# Twinkle — Java Best-Practices Refactoring Proposal

**Author:** Prepared for engineering review
**Scope:** `twinkle-app/**` (all Maven modules)
**Goal:** Bring the codebase up to modern Java best practices and the engineering
bar expected in a regulated fintech environment (Revolut-style: security-first,
high test coverage, strict code review, structured logging, no dead/duplicated
code, static analysis gates in CI).

This is a **small, incremental refactoring plan**, not a rewrite. Every item below
references real code in the repo today, is scoped to be shippable as its own
PR under the existing `{dev|devops|test}/Ticket-0000` branch convention, and is
ordered by risk so security fixes land first.

---

## 1. Methodology

The codebase was scanned module-by-module (`twinkle.main`, `twinkle.auth.services`,
`twinkle.bll.services`, `persistence/twinkle.dal.*`). Findings are grouped into:

- **P0 — Security** (fix before anything else touches these files)
- **P1 — Architecture & correctness** (structural debt, dead code, layering violations)
- **P2 — Java idiom / style** (Effective-Java-level cleanups)
- **P3 — Process / tooling** (what "Revolut-style" engineering rigor adds on top)

Current state for scale: **150 main source files vs. 7 test files** across the
whole repo — under 5% file-level test coverage, concentrated almost entirely in
`twinkle.auth.services` and the Hibernate repository layer. `twinkle.main` (the
largest module, ~60 classes including all UI/business logic in
[`AboutCarPanel.java`](../twinkle-app/twinkle.main/src/main/java/com/github/donkeyrit/twinkle/panels/Content/AboutCarPanel.java))
has **zero tests**.

---

## 2. P0 — Security issues (fix first)

### 2.1 SQL injection via string concatenation

`AboutCarPanel.java` builds SQL by concatenating raw user/session values
directly into query strings and executes them through a hand-rolled
`DataBase` helper — bypassing the Hibernate/jOOQ persistence layer that
already exists in the `persistence/` modules:

```java
// twinkle-app/twinkle.main/.../panels/Content/AboutCarPanel.java:369-370
String checkQuery = "SELECT id_client FROM clients INNER JOINusersON client.id_user = user.id_user WHERE login = "
        + "'" + UserInformation.getLogin() + "'";
ResultSet checkClientSet = database.select(checkQuery);
```

```java
// twinkle-app/twinkle.main/.../panels/Content/AboutCarPanel.java:405-410
String insertRenta = "INSERT INTO rent(id_client,id_car,start_date,plan_date) VALUES ("
        + idClient + "," + imagesNum;
String startDataIn = "'" + yList.get(0) + "-" + yList.get(1) + "-" + yList.get(2) + "'";
insertRenta += "," + startDataIn + "," + planDataIn + ")";
database.insert(insertRenta);
```

**Why it matters:** any value that ever flows into `UserInformation.getLogin()`
or the date/id fields from an untrusted source becomes a SQL injection vector.
There's also a broken JOIN (`INNER JOINusersON` — missing spaces), meaning this
path is likely already non-functional, which is further evidence it's dead
legacy code left over from before the Hibernate/jOOQ layer was introduced.

**Fix:**
- Delete `DataBase.java` and the raw-SQL branches of `AboutCarPanel.java`
  entirely; route rental creation through `CarService` / a new `RentService`
  backed by `RentRepositoryImpl` (already exists, uses Hibernate parameter
  binding).
- If any raw SQL is ever genuinely required, use `PreparedStatement` with
  bound parameters — never string concatenation.

### 2.2 Hardcoded database credentials in source

```java
// twinkle-app/twinkle.main/.../DataBase.java:11-13
private String DB_CONNECTION = "jdbc:postgresql://127.0.0.1:5432/carrental";
private String DB_USER = "twinkle_user";
private String DB_PASSWORD = "Sr412Tqew!";
```

A real password is committed to git history. **Fix:**
- Remove this class (see 2.1 — it's superseded by the DAL modules, which
  already read `database.url` / `database.user` / `database.password` from
  Maven/system properties per `README.md`).
- Rotate the leaked password immediately — it is compromised the moment it's
  in version control, regardless of whether the class is later deleted.
- Standardize secret injection through environment variables /
  a secrets manager (Vault, AWS Secrets Manager, etc.) rather than
  Maven `-D` flags long-term; never a literal in `.java` source.

### 2.3 Weak password hashing (unsalted SHA-1), duplicated three times

```java
// identical file in twinkle.bll.services, twinkle.auth.services, and twinkle.main:
MessageDigest mDigest = MessageDigest.getInstance("SHA1");
byte[] result = mDigest.digest(input.getBytes());
```

Files:
- [`twinkle.bll.services/.../bll/security/HashManager.java`](../twinkle-app/twinkle.bll.services/src/main/java/com/github/donkeyrit/twinkle/bll/security/HashManager.java)
- [`twinkle.auth.services/.../auth/security/HashManager.java`](../twinkle-app/twinkle.auth.services/src/main/java/com/github/donkeyrit/twinkle/auth/security/HashManager.java)
- [`twinkle.main/.../security/HashManager.java`](../twinkle-app/twinkle.main/src/main/java/com/github/donkeyrit/twinkle/security/HashManager.java)

SHA-1 is cryptographically broken and, more importantly, **not a password
hash** — it's a fast general-purpose digest with no salt or work factor,
so it's trivially brute-forced/rainbow-tabled offline.

**Fix:**
- Collapse to a single `PasswordHasher` in one shared module (e.g.
  `twinkle.auth.services`, which the other two already depend on
  transitively) and delete the other two copies.
- Replace SHA-1 with `BCrypt` (`spring-security-crypto` or `jBCrypt`) or
  `Argon2` (`argon2-jvm`) — both salt automatically and have a tunable cost
  factor.
- This is a breaking data-format change: plan a migration (re-hash on next
  successful login, or a one-off migration script) rather than a silent swap.

### 2.4 Swallowed exceptions via `printStackTrace`

20+ occurrences across `DataBase.java`, `HashManager.java` (×3),
`MyTableModel.java`, `AboutCarPanel.java`, and `BaseCrudRepository.java`, e.g.:

```java
// twinkle-app/twinkle.bll.services/.../security/HashManager.java:25-28
catch(Exception ex)
{
    ex.printStackTrace();
}
```

```java
// twinkle-app/persistence/.../BaseCrudRepository.java:33-37
catch (Exception ex)
{
    //TODO: Implement logger
    System.out.printf("Exception occured in %s during inserting. Exception - %s", this, getClass().getName(), ex);
}
```

`log4j`/`slf4j` are already project dependencies (see root `pom.xml`) but are
essentially unused — `printStackTrace()`/`System.out` are used instead
everywhere an exception is handled. This means: no log levels, no log
aggregation, no way to alert on failures in production, and — worse —
`insert`/`update`/`delete` in `BaseCrudRepository` **swallow the failure and
return `false`**, so callers that don't check the boolean silently lose data.

**Fix:**
- Inject an SLF4J `Logger` into every class that currently prints exceptions;
  log at `ERROR` with the exception object (`log.error("...", ex)`), never
  string-interpolate the stack trace away.
- Stop swallowing `Exception` at persistence boundaries — either let a
  domain-specific unchecked exception (`RepositoryException`) propagate so
  the UI layer can show a real error, or make the `boolean` return type
  explicit-and-checked everywhere it's used (currently several call sites
  don't check it at all — see `UserInfoServiceImpl.updatePassword`, which
  calls `userRepository.update(...)` and ignores the result).

### 2.5 Session/credentials cached in `java.util.prefs.Preferences`

```java
// twinkle-app/twinkle.bll.services/.../bll/models/UserInformation.java
public static void setUser(User user) {
    preferences.putLong(PREF_ID, user.getId());
    preferences.put(PREF_LOGIN, user.getLogin());
    preferences.put(PREF_PASS, user.getPassword());   // password hash written to disk
    preferences.putBoolean(PREF_ROLE, user.isRole());
}
```

`Preferences` persists to the OS user registry/`~/.java` on disk, unencrypted,
outliving the process. Storing the password hash there means it sits on disk
indefinitely after logout, and any other local process/user can read it.

**Fix:** hold the authenticated session (id, login, role, and *never* the
password/hash) in an in-memory `SessionContext` object with application
lifetime, injected via Guice, not a static class backed by persistent OS
storage. See §3.1 for why the `static` design itself also needs to go.

---

## 3. P1 — Architecture & correctness

### 3.1 Static mutable global state instead of DI-managed session

`UserInformation` (§2.5) is a fully `static` class — every field and method
is static, initialized once in a `static {}` block. The project already uses
Guice for dependency injection everywhere else (`ServicesModules`,
`AuthenticationModules`, `MainModules`), so this is an inconsistent,
untestable escape hatch: any class can silently read/mutate global session
state, there's no way to run two sessions in tests, and it makes
`UserInfoServiceImpl` (§2.4) impossible to unit test without touching real OS
preferences.

**Fix:** introduce a `SessionContext` (or `CurrentUser`) interface bound as a
Guice singleton, injected into `LoginService`, `UserInfoService`, and the
panels that currently call `UserInformation.getX()` directly.

### 3.2 `AboutCarPanel` is a ~700-line God class mixing UI, SQL, and business rules

Beyond the SQL injection in §2.1, `AboutCarPanel.java` directly instantiates
`DataBase`, builds date-diff/pricing logic inline inside anonymous
`ActionListener`s nested 4–5 levels deep, and mixes Swing layout code with
data access in the same method. This duplicates responsibility that already
exists cleanly in `CarService` / `DefaultCarService` and the `dal.hibernate`
repositories.

**Fix:** extract a `RentalService` (BLL layer, following the existing
`CarService`/`UserInfoService` pattern) that owns "compute rental cost",
"validate single active rental per client", and "create rental" — then have
`AboutCarPanel` only call the service and render results. This alone removes
the need for `DataBase.java` entirely.

### 3.3 `Optional` used as a field type and method parameter

```java
// twinkle-app/persistence/.../dal/specifications/CarQuerySpecification.java
private Optional<MarkOfCar> selectedMark;
private Optional<String> selectedModel;
```

```java
// twinkle-app/twinkle.bll.services/.../services/UserInfoServiceImpl.java:56
public Optional<String> updateUserProfile(
    Optional<Client> client, String firstNameText, ... )
```

*Effective Java* (Item 55) is explicit: `Optional` is meant as a **return
type** to signal "this may be absent" to a caller — it should not be used as
a field type (adds serialization/allocation overhead for no benefit over
`null` + clear ownership) or as a parameter type (forces every caller to wrap
values, and IDEs/annotations already have `@Nullable` for this).

**Fix:**
- Replace `Optional<T>` fields with plain nullable fields (or, better,
  restructure `CarQuerySpecification` as an immutable builder-constructed
  value object — see §3.5).
- Change `updateUserProfile(Optional<Client> client, ...)` to
  `updateUserProfile(@Nullable Client client, ...)` or, cleaner, split into
  `createClientProfile(...)` / `updateClientProfile(Client existing, ...)`
  so the caller's `if (client.isPresent())` branch (already present at the
  call site) maps to two distinct, testable methods instead of one method
  with a boolean-by-Optional branch inside it.

### 3.4 Ambiguous `boolean role` on `User`

```java
// twinkle-app/persistence/twinkle.dal.abstractions/.../dal/models/User.java
private boolean role;
public boolean isRole() { return role; }
```

A `boolean` named `role` doesn't say what `true` means at any call site (grep
shows it's used as an admin flag). This is exactly the kind of ambiguous
boolean *Effective Java* Item 34 recommends replacing with an enum.

**Fix:** `enum Role { ADMIN, CUSTOMER }` (or similar), stored as an ordinal/
string column. Immediately makes call sites like
`new User(username, passwordHash, false)` in `DefaultLoginService.java:54`
self-documenting instead of a magic boolean literal.

### 3.5 Package/folder naming violates Java conventions

```
twinkle-app/twinkle.main/src/main/java/.../panels/Content/   ← capitalized package
```

Java convention (and most static-analysis/Checkstyle default rulesets) require
all-lowercase package names. `Content` (and its sibling, correctly-cased
`content`-style siblings like `authentication`, `common`, `settings`) stands
out as the one inconsistency.

**Fix:** rename to `content`; low-risk, IDE-assisted rename, but touches many
imports — do as its own mechanical PR.

### 3.6 Effectively dead/duplicate persistence path

`DataBase.java` + the raw-SQL branches of `AboutCarPanel.java` are a second,
parallel, broken (§2.1's malformed JOIN) way of talking to Postgres that
coexists with the maintained `dal.hibernate` / `dal.jooq` modules. Keeping
two data-access strategies in the same app is itself a best-practice
violation independent of the security issues — it doubles the surface area
reviewers and new engineers have to reason about.

**Fix:** covered by 2.1/3.2 — delete once `RentalService` replaces the direct
calls.

---

## 4. P2 — Java idiom / style cleanups

| Issue | Where | Fix |
|---|---|---|
| `catch(Exception ex)` too broad | `HashManager` (×3), `AboutCarPanel`, `BaseCrudRepository` | Catch the specific checked exception (`NoSuchAlgorithmException`, `SQLException`, `PersistenceException`) |
| `StringBuffer` in single-threaded code | `HashManager.generateHash` | Use `StringBuilder` (no synchronization needed) |
| Manual hex encoding loop | `HashManager.generateHash` | `HexFormat.of().formatHex(bytes)` (JDK 17+, already the project's minimum) |
| Missing `@Override` consistency / raw exception messages | `UserInfoServiceImpl` typo `"Password does't match"` | Fix copy; consider an `ErrorMessages` constants class or a validation library (Bean Validation / `jakarta.validation`) instead of hand-written `if` chains returning `Optional<String>` |
| Manual date-diff math on `java.util.Date` | `AboutCarPanel.java:355-360` (`planDateReturnCar.getTime() - startDateGetCar.getTime()`, then dividing by `24*60*60*1000`) | Use `java.time.LocalDate` + `ChronoUnit.DAYS.between(...)` — the project targets Java 19, `java.time` has been standard since Java 8 |
| Wildcard imports | `DataBase.java` (`import java.sql.*; import java.util.*;`) | Explicit imports (also a default Checkstyle/PMD violation) |
| Magic numbers for UI layout scattered through `AboutCarPanel` | throughout | Not urgent, but worth named constants once the class is broken up per §3.2 |
| `Client` mutated via setters after construction in a loop-like update method | `UserInfoServiceImpl.updateUserProfile` | Once `Optional` param is removed (§3.3), consider a `Client.Builder` or a proper constructor to avoid the current "5 setter calls in a row" pattern for both the create and update branch |

---

## 5. P3 — Process & tooling (the "Revolut-style" bar)

Fixing individual files is necessary but not sufficient — the practices below
are what actually *prevents regressions* like the ones in §2 from landing
again, and are standard in fintech engineering orgs like Revolut:

1. **Static analysis in CI, blocking merge.** Add Checkstyle + PMD + SpotBugs
   (or Error Prone) to the Maven build (`mvn verify`) with a ratcheted
   baseline, so the SQL-concatenation and swallowed-exception patterns above
   fail the build automatically going forward. SpotBugs' `FindSqlInjection`
   detector and PMD's `AvoidCatchingGenericException` would have caught §2.1
   and §2.4 respectively.
2. **Secret scanning.** Add a pre-commit/CI secret scanner (gitleaks or
   truffleHog) — would have caught §2.2 before it reached `main`.
3. **Test coverage gate.** Introduce a JaCoCo threshold (start low, e.g. 40%,
   ratchet up per module) so new code can't ship untested; today's <5%
   file-level coverage in `twinkle.main` is the highest-risk gap given it's
   where the SQL injection and God-class issues live.
4. **Structured logging.** Standardize on the SLF4J API that's already a
   dependency (log4j2 backend) everywhere `printStackTrace`/`System.out` is
   used today (§2.4), with consistent log levels and no PII (passwords,
   hashes) in log lines.
5. **Small, single-purpose PRs.** The `[Ticket-0000][DEV|DEVOPS|TEST]`
   commit convention in `AGENTS.md` is already in place — pair it with a PR
   template requiring "what changed / why / how tested" and CODEOWNERS-based
   mandatory review, matching the ticket-scoped-change discipline typical of
   Revolut engineering workflows.
6. **Dependency & secret hygiene.** Rotate the credential from §2.2, add
   Dependabot/Renovate for the pinned versions in the root `pom.xml`
   (`hibernate 6.1.7`, `jooq 3.19.6`, etc. are already reasonably current but
   will drift), and document required env vars (`DATABASE_URL`,
   `DATABASE_USER`, `DATABASE_PASSWORD`) in one place instead of only in
   `README.md` prose.

---

## 6. Suggested execution order

| Phase | Items | Why first/last |
|---|---|---|
| **1. Security hotfix** | §2.1, §2.2 (rotate credential), §2.5 | Live exploitable issues; smallest possible diff (delete `DataBase.java`, stop persisting password hash) |
| **2. Password hashing** | §2.3 | Needs a migration plan, so scope separately from Phase 1's quick deletions |
| **3. Logging** | §2.4 | Mechanical, high-value, unblocks debugging everything after it |
| **4. Session/DI cleanup** | §3.1 | Unblocks unit-testing the BLL services touched in Phase 2 |
| **5. RentalService extraction** | §3.2, §3.6 | Depends on Phase 4 (needs `SessionContext`, not `UserInformation`) |
| **6. API cleanups** | §3.3, §3.4 | Independent, can run in parallel with Phase 5 |
| **7. Style/naming** | §3.5, §4 | Lowest risk, do last / opportunistically |
| **8. Tooling** | §5 (all) | Set up early in parallel, but keep the *gate* (failing CI) enabled only after Phases 1–3 land, or the initial PR won't pass it |

Each phase is intentionally small enough to be one or two PRs, keeping with
the existing ticket-per-branch convention.

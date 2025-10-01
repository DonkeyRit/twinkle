# AGENTS.md

## Build

The project is built with Maven from the `twinkle-app` directory:

```bash
cd twinkle-app
mvn clean install
```

## Commits & Branches

- Commit messages must start with the pattern:
  `[Ticket-0000][DEV|DEVOPS|TEST] <description>`
  Example: `[Ticket-1234][DEV] Fix car rental calculation`

- Branch names must follow the pattern:
  `{dev|devops|test}/Ticket-0000`
  Example: `dev/Ticket-1234`

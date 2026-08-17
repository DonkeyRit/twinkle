-- Login is used as the sole lookup key for authentication
-- (see UserRepositoryImpl#getByLoginAndPassword / #isUserExist, both of which
-- assume at most one matching row), but nothing in the schema enforced that.
-- Without this constraint two accounts could be created with the same login,
-- which would make those lookups throw NonUniqueResultException at runtime
-- instead of failing fast at INSERT time. The constraint also gives Postgres
-- a unique index to satisfy those lookups instead of a sequential scan.
ALTER TABLE "users" ADD CONSTRAINT users_login_key UNIQUE (login);

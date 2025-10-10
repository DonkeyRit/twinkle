-- Seeds a default admin account for local/dev use.
-- Password hash is BCrypt("ChangeMe123!"), matching the hashing scheme
-- DefaultLoginService now uses (see PasswordHasher). This is a well-known
-- placeholder, not a secret: change it immediately in any shared environment.
INSERT INTO "users"(login, password, role)
VALUES ('admin', '$2a$12$oKDab6P1dUUfRn.pdFM/qOQD7/P7rz.IyYXe7CLluZc.dAj.0y0o.', true);

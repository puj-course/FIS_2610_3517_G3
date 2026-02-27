ALTER TABLE users
  ADD COLUMN is_active TINYINT(1) NOT NULL DEFAULT 1,
  ADD COLUMN last_login TIMESTAMP NULL,
  ADD UNIQUE INDEX ux_users_email (email),
  ADD UNIQUE INDEX ux_users_username (username);


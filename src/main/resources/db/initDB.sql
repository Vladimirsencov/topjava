DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS user_Meals;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START 100000;

CREATE TABLE users
(
  id         INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name       VARCHAR NOT NULL,
  email      VARCHAR NOT NULL,
  password   VARCHAR NOT NULL,
  registered TIMESTAMP DEFAULT now(),
  enabled    BOOL DEFAULT TRUE,
  calories_per_day INTEGER DEFAULT 2000 NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_roles
(
  id      INTEGER PRIMARY KEY  DEFAULT nextval('global_seq'),
  user_id INTEGER NOT NULL,
  role    VARCHAR,
  CONSTRAINT user_roles_idx UNIQUE (user_id, role),
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE user_meals (
  id          INTEGER PRIMARY KEY  DEFAULT nextval('global_seq'),
  description VARCHAR(50) NOT NULL,
  dateTime    TIMESTAMP            DEFAULT now(),
  calories    INTEGER     NOT NULL DEFAULT 0,
  user_id     INTEGER     NOT NULL,
  CONSTRAINT user_meal_idx UNIQUE (user_id, description),
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
)


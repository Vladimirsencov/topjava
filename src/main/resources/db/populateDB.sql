DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM user_meals;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO user_meals (description, calories, user_id) VALUES
  ('Завтрак', 1500, 100000),
  ('Обед', 1500, 100000),
  ('Ужин', 1500, 100000),
  ('Завтрак', 1500, 100001),
  ('Обед', 1500, 100001),
  ('Ужин', 1500, 100001);

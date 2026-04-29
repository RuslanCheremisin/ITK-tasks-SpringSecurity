CREATE TABLE IF NOT EXISTS roles (
                       id BIGSERIAL PRIMARY KEY,
                       name VARCHAR(50) UNIQUE NOT NULL
);

INSERT INTO roles (name) VALUES
                             ('SUPER-ADMIN'),
                             ('MODERATOR'),
                             ('USER');
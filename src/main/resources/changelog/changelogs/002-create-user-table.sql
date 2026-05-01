-- Create users table
CREATE TABLE users
(
    id                BIGSERIAL PRIMARY KEY,
    first_name        VARCHAR(255),
    last_name         VARCHAR(255),
    phone             VARCHAR(50),
    bio               TEXT,
    email             VARCHAR(255),
    username          VARCHAR(255) UNIQUE NOT NULL,
    password          VARCHAR(255)        NOT NULL,
    created_at        TIMESTAMP,
    updated_at        TIMESTAMP,
    is_account_locked BOOL
);

-- Create join table (needed for roles relation)
CREATE TABLE user_roles
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

-- Insert default user
INSERT INTO users (first_name, last_name, phone, bio, email, username, password, created_at, updated_at,
                   is_account_locked)
VALUES (NULL,
        NULL,
        NULL,
        NULL,
        NULL,
        'johndoe123',
        '$2a$10$yhrcGSvlrWRbka4Q9.Nkeu2nJZzqmxQT6s/SF95kWoeZ0xlSYY4Nu',
        NOW(),
        NOW(),
        false);

-- Link user to SUPER-ADMIN role (requires role to exist already)
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u
         JOIN roles r ON r.name = 'SUPER-ADMIN'
WHERE u.username = '!1Vasya';
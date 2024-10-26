CREATE TABLE users_application.users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);


CREATE TABLE users_application.roles (
    id BIGSERIAL PRIMARY KEY,
    role_name VARCHAR(255) NOT NULL,
    description TEXT
);

 CREATE TABLE users_application.user_roles (
     id BIGSERIAL PRIMARY KEY,
     user_id BIGINT REFERENCES users_application.users(id) ON DELETE CASCADE,
     role_id BIGINT REFERENCES users_application.roles(id) ON DELETE CASCADE,
     assigned_at TIMESTAMP NOT NULL DEFAULT NOW()
 );

 CREATE TABLE users_application.password_reset_tokens (
     id BIGSERIAL PRIMARY KEY,
     user_id BIGINT REFERENCES users_application.users(id) ON DELETE CASCADE,
     token VARCHAR(255) NOT NULL,
     created_at TIMESTAMP NOT NULL DEFAULT NOW(),
     expires_at TIMESTAMP NOT NULL
 );





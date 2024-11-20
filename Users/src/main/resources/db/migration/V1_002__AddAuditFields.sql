-- Users tablosuna alanlar ekleme
ALTER TABLE users_application.users
ADD COLUMN created_by VARCHAR(255),
ADD COLUMN updated_by VARCHAR(255),
ADD COLUMN deleted_by VARCHAR(255),
ADD COLUMN deleted_at TIMESTAMP;

-- Roles tablosuna alanlar ekleme
ALTER TABLE users_application.roles
ADD COLUMN created_by VARCHAR(255),
ADD COLUMN created_at TIMESTAMP NOT NULL DEFAULT NOW(),
ADD COLUMN updated_by VARCHAR(255),
ADD COLUMN updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
ADD COLUMN deleted_by VARCHAR(255),
ADD COLUMN deleted_at TIMESTAMP;

-- UserRoles tablosuna alanlar ekleme
ALTER TABLE users_application.user_roles
ADD COLUMN created_by VARCHAR(255),
ADD COLUMN created_at TIMESTAMP NOT NULL DEFAULT NOW(),
ADD COLUMN updated_by VARCHAR(255),
ADD COLUMN updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
ADD COLUMN deleted_by VARCHAR(255),
ADD COLUMN deleted_at TIMESTAMP;

-- PasswordResetTokens tablosuna alanlar ekleme
ALTER TABLE users_application.password_reset_tokens
ADD COLUMN created_by VARCHAR(255),
ADD COLUMN updated_by VARCHAR(255),
ADD COLUMN updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
ADD COLUMN deleted_by VARCHAR(255),
ADD COLUMN deleted_at TIMESTAMP;

-- Settings tablosuna alanlar ekleme
ALTER TABLE users_application.settings
ADD COLUMN created_by VARCHAR(255),
ADD COLUMN created_at TIMESTAMP NOT NULL DEFAULT NOW(),
ADD COLUMN updated_by VARCHAR(255),
ADD COLUMN updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
ADD COLUMN deleted_by VARCHAR(255),
ADD COLUMN deleted_at TIMESTAMP;

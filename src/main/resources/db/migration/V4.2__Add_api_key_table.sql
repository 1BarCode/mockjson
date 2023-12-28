DROP TABLE IF EXISTS `api_key`;

CREATE TABLE `api_key` (
    `id` binary(16) NOT NULL DEFAULT (UUID_TO_BIN(UUID())),
    `key_value` VARCHAR(255) NOT NULL,
    `expires_at` TIMESTAMP NOT NULL,
    `is_revoked` BOOLEAN NOT NULL,
    `usage_count` INT DEFAULT 0,
    `last_used_at` TIMESTAMP,
    `description` TEXT,
    `key_type` ENUM ('read-only', 'read-write'),
    `client_name` VARCHAR(255) NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `user_id` binary(16) NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `api_key_key_value_unique` UNIQUE (`key_value`),
    CONSTRAINT `api_key_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    INDEX `idx_key_value` (`key_value`)
);

-- remove all token from token table
DELETE FROM `token`;

-- modify token table to add created_at and updated_at columns
ALTER TABLE `token`
    ADD COLUMN `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
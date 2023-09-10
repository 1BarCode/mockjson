DROP TABLE IF EXISTS `role`;
DROP TABLE IF EXISTS `authority`;

CREATE TABLE `role` (
    `id` binary(16) NOT NULL DEFAULT (UUID_TO_BIN(UUID())),
    `name` ENUM('ROLE_USER', 'ROLE_MODERATOR', 'ROLE_ADMIN') NOT NULL,
    `description` varchar(255) NOT NULL DEFAULT '',
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    CONSTRAINT `role_name_unique` UNIQUE (`name`)
) ENGINE=InnoDB;

CREATE TABLE `authority` (
    `user_id` binary(16) NOT NULL,
    `role_id` binary(16) NOT NULL,
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`user_id`, `role_id`),
    CONSTRAINT `authority_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    CONSTRAINT `authority_role_id_fk` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB;

ALTER TABLE `user`
    ADD COLUMN `password` varchar(255) NOT NULL AFTER `email`,
    ADD COLUMN `enabled` boolean NOT NULL DEFAULT 1,
    ADD COLUMN `locked` boolean NOT NULL DEFAULT 0;
    
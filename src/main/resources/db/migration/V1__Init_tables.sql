-- MYSQL

-- drop tables
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `post`;
DROP TABLE IF EXISTS `comment`;
DROP TABLE IF EXISTS `task`;
DROP TABLE IF EXISTS `post_like`;

-- create tables
CREATE TABLE `user` (
    `id` binary(16) NOT NULL DEFAULT (UUID_TO_BIN(UUID())),
    `username` varchar(50) NOT NULL,
    `email` varchar(50) NOT NULL,
    `first_name` varchar(50) NOT NULL,
    `last_name` varchar(50) NOT NULL,
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    CONSTRAINT `user_username_unique` UNIQUE (`username`),
    CONSTRAINT `user_email_unique` UNIQUE (`email`)
 ) ENGINE=InnoDB;

CREATE TABLE `post` (
    `id` binary(16) NOT NULL DEFAULT (UUID_TO_BIN(UUID())),
    `title` varchar(50) NOT NULL,
    `slug` varchar(255) NOT NULL,
    `content` text NOT NULL,
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `user_id` binary(16) NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `post_slug_unique` UNIQUE (`slug`),
    CONSTRAINT `post_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
 ) ENGINE=InnoDB;

CREATE TABLE `comment` (
    `id` binary(16) NOT NULL DEFAULT (UUID_TO_BIN(UUID())),
    `slug` varchar(255) NOT NULL,
    `content` text NOT NULL,
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `user_id` binary(16) NOT NULL,
    `post_id` binary(16) NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `comment_slug_unique` UNIQUE (`slug`),
    CONSTRAINT `comment_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
    CONSTRAINT `comment_post_id_fk` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`) ON DELETE CASCADE
 ) ENGINE=InnoDB;

CREATE TABLE `task` (
    `id` binary(16) NOT NULL DEFAULT (UUID_TO_BIN(UUID())),
    `title` varchar(50) NOT NULL,
    `slug` varchar(255) NOT NULL,
    `description` text NOT NULL,
    `status` ENUM('TODO', 'PENDING', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED') NOT NULL,
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `user_id` binary(16) NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `task_slug_unique` UNIQUE (`slug`),
    CONSTRAINT `task_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE -- delete all tasks when user is deleted
 ) ENGINE=InnoDB;

CREATE TABLE `post_like` (
    `post_id` binary(16) NOT NULL,
    `user_id` binary(16) NOT NULL,
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`post_id`, `user_id`),
    CONSTRAINT `post_like_post_id_fk` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`) ON DELETE CASCADE,
    CONSTRAINT `post_like_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
 ) ENGINE=InnoDB;



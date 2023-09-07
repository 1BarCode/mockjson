-- MYSQL

-- drop tables
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `post`;
DROP TABLE IF EXISTS `comment`;
DROP TABLE IF EXISTS `task`;
DROP TABLE IF EXISTS `post_like`;

-- create tables
CREATE TABLE `user` {
    `id` varchar(36) NOT NULL,
    `username` varchar(50) NOT NULL,
    `email` varchar(50) NOT NULL,
    `first_name` varchar(50) NOT NULL,
    `last_name` varchar(50) NOT NULL,
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
    CONSTRAINT `user_id_unique` UNIQUE (`id`)
    CONSTRAINT `user_username_unique` UNIQUE (`username`)
    CONSTRAINT `user_email_unique` UNIQUE (`email`)
} ENGINE=InnoDB

CREATE TABLE `post` {
    `id` int NOT NULL AUTO_INCREMENT,
    `title` varchar(50) NOT NULL,
    `content` varchar(255) NOT NULL,
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `user_id` varchar(36) NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `post_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE -- delete all posts when user is deleted
} ENGINE=InnoDB

CREATE TABLE `comment` {
    `id` int NOT NULL AUTO_INCREMENT,
    `content` varchar(255) NOT NULL,
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `user_id` varchar(36) NOT NULL,
    `post_id` int NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `comment_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE -- delete all comments when user is deleted
    CONSTRAINT `comment_post_id_fk` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`) ON DELETE CASCADE -- delete all comments when post is deleted
} ENGINE=InnoDB

CREATE TABLE `task` {
    `id` int NOT NULL AUTO_INCREMENT,
    `title` varchar(50) NOT NULL,
    `description` varchar(255) NOT NULL,
    `status` varchar(20) NOT NULL,
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `user_id` varchar(36) NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `task_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE -- delete all tasks when user is deleted
} ENGINE=InnoDB

CREATE TABLE `post_like` {
    `post_id` int NOT NULL,
    `user_id` varchar(36) NOT NULL,
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`post_id`, `user_id`), -- composite key
    CONSTRAINT UNIQUE (`post_id`, `user_id`), -- unique constraint
    CONSTRAINT `post_like_post_id_fk` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`) ON DELETE CASCADE -- delete all post likes when post is deleted
    CONSTRAINT `post_like_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE -- delete all post likes when user is deleted
} ENGINE=InnoDB



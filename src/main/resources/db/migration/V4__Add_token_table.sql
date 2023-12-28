DROP TABLE IF EXISTS `token`;

CREATE TABLE `token` (
    `id` binary(16) NOT NULL DEFAULT (UUID_TO_BIN(UUID())),
    `value` varchar(400) NOT NULL,
    `token_type` ENUM('BEARER') NOT NULL,
    `user_id` binary(16) NOT NULL,
    `revoked` tinyint(1) NOT NULL DEFAULT 0,
    `expired` tinyint(1) NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    CONSTRAINT `token_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB;

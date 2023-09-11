RENAME TABLE `role` TO `permission`;

ALTER TABLE `permission`
    -- update column name and type
    MODIFY COLUMN `name` varchar(50) NOT NULL,
    -- update contraint name
    DROP CONSTRAINT `role_name_unique`,
    ADD CONSTRAINT `permission_name_unique` UNIQUE (`name`);

ALTER TABLE `authority`
    RENAME TO `user_permission`;

ALTER TABLE `user_permission`
    DROP FOREIGN KEY `authority_user_id_fk`,
    DROP FOREIGN KEY `authority_role_id_fk`,
    DROP PRIMARY KEY,
    -- rename column role_id to permission_id
    CHANGE COLUMN `role_id` `permission_id` binary(16) NOT NULL,
    -- add composite primary key
    ADD PRIMARY KEY (`user_id`, `permission_id`),
    -- add foreign key constraint
    ADD CONSTRAINT `user_permission_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    ADD CONSTRAINT `user_permission_permission_id_fk` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`);


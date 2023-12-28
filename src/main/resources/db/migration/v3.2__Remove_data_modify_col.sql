-- remove constraint `user_permission_permission_id_fk` from table `user_permission`
ALTER TABLE `user_permission`
    DROP FOREIGN KEY `user_permission_permission_id_fk`;

DELETE FROM `permission`;

ALTER TABLE `permission` 
    MODIFY COLUMN `name` ENUM('general_user:', 'general_user:read', 'general_user:write', 'general_user:delete', 'general_user:update') NOT NULL;
    -- add back constraint
    ADD CONSTRAINT `user_permission_permission_id_fk` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`);

INSERT INTO `permission` (`name`, `description`)
VALUES 
    ('general_user:', 'this is used for searching'), 
    ('general_user:read', 'user can read general informations'), 
    ('general_user:write', 'user can write general informations'), 
    ('general_user:delete', 'user can delete general informations'), 
    ('general_user:update', 'user can update general informations');
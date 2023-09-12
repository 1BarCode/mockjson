DELETE FROM `permission`;

ALTER TABLE `permission` 
    MODIFY COLUMN `name` ENUM('general_user:', 'general_user:read', 'general_user:write', 'general_user:delete', 'general_user:update') NOT NULL;

INSERT INTO `permission` (`name`, `description`)
VALUES 
    ('general_user:', 'this is used for searching'), 
    ('general_user:read', 'user can read general informations'), 
    ('general_user:write', 'user can write general informations'), 
    ('general_user:delete', 'user can delete general informations'), 
    ('general_user:update', 'user can update general informations');
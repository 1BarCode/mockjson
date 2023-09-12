INSERT INTO `permission` (`name`, `description`)
VALUES 
    ('general_user', 'this is used for searching'), 
    ('general_user:read', 'user can read general informations'), 
    ('general_user:write', 'user can write general informations'), 
    ('general_user:delete', 'user can delete general informations'), 
    ('general_user:update', 'user can update general informations'),

    ('general_moderator:read', 'moderator can read general informations'), 
    ('general_moderator:write', 'moderator can write general informations'), 
    ('general_moderator:delete', 'moderator can delete general informations'),
    ('general_moderator:update', 'moderator can update general informations'),

    ('general_admin:read', 'admin can read general informations'),
    ('general_admin:write', 'admin can write general informations'),
    ('general_admin:delete', 'admin can delete general informations'),
    ('general_admin:update', 'admin can update general informations');
    

   
    



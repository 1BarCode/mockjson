package com.mycompany.mockjson.auth.permission;

/**
 * Resouce-based and role-based authorization
 */
public enum PermissionName {
    // general role-based permissions
    GENERAL_USER_READ("general_user:read"),
    GENERAL_USER_WRITE("general_user:write"),
    GENERAL_USER_DELETE("general_user:delete"),
    GENERAL_USER_UPDATE("general_user:update"),

    GENERAL_MODERATOR_READ("general_moderator:read"),
    GENERAL_MODERATOR_WRITE("general_moderator:write"),
    GENERAL_MODERATOR_DELETE("general_moderator:delete"),
    GENERAL_MODERATOR_UPDATE("general_moderator:update"),

    GENERAL_ADMIN_READ("general_admin:read"),
    GENERAL_ADMIN_WRITE("general_admin:write"),
    GENERAL_ADMIN_DELETE("general_admin:delete"),
    GENERAL_ADMIN_UPDATE("general_admin:update"),

    // resource-based permissions
    USER_READ("user:read"),
    USER_WRITE("user:write"),
    USER_DELETE("user:delete"),
    USER_UPDATE("user:update"),

    TEST_READ("test:read"),
    TEST_WRITE("test:write"),
    TEST_DELETE("test:delete"),
    TEST_UPDATE("test:update"),

    ;

    private final String permission;

    PermissionName(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}

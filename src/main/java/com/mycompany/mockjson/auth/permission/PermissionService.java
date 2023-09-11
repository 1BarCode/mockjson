package com.mycompany.mockjson.auth.permission;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.mockjson.auth.userpermission.UserPermission;
import com.mycompany.mockjson.user.User;

@Service
public class PermissionService {
    @Autowired
    private PermissionRepo permissionRepo;

    public Permission findByName(PermissionName name) throws Exception {
        return permissionRepo.findByName(name).orElseThrow(() -> new Exception("Something went wrong"));
    }

    // grant user permissions methods
    /**
     * Add general user permissions to user
     * 
     * @param user
     * @return the user with the authority added, but not persisted to DB yet
     * @throws Exception
     */
    public User grantGeneralUserPermissions(User user) throws Exception {
        // Permission generalUserRead =
        // permissionRepo.findByName(PermissionName.GENERAL_USER_READ)
        // .orElseThrow(() -> new Exception("Something went wrong"));

        // Permission generalUserWrite =
        // permissionRepo.findByName(PermissionName.GENERAL_USER_WRITE)
        // .orElseThrow(() -> new Exception("Something went wrong"));

        // Permission generalUserDelete =
        // permissionRepo.findByName(PermissionName.GENERAL_USER_DELETE)
        // .orElseThrow(() -> new Exception("Something went wrong"));

        // Permission generalUserUpdate =
        // permissionRepo.findByName(PermissionName.GENERAL_USER_UPDATE)
        // .orElseThrow(() -> new Exception("Something went wrong"));

        // List<Permission> permissions = List.of(generalUserRead, generalUserWrite,
        // generalUserDelete, generalUserUpdate);

        List<Permission> permissions = permissionRepo.findByNameStartsWith("general_user")
                .orElseThrow(() -> new Exception("Something went wrong"));

        if (permissions.size() == 0) {
            throw new Exception("Cannot grant general user permissions");
        }

        // create a new authority for each permission and add to user
        permissions.forEach(permission -> {
            UserPermission userPermission = new UserPermission();
            userPermission.setUser(user);
            userPermission.setPermission(permission);

            user.addUserPermission(userPermission);
        });

        return user;
    }
}

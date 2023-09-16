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
                Permission userRole = permissionRepo.findByName(PermissionName.ROLE_USER)
                                .orElseThrow(() -> new Exception("Cannot grant user role"));

                List<Permission> generalUserPermissions = permissionRepo
                                .findByNameStartsWith(PermissionName.GENERAL_USER);

                generalUserPermissions.add(userRole); // include the general ROLE_USER

                if (generalUserPermissions.size() == 0) {
                        throw new Exception("Cannot grant general user permissions");
                }

                // create a new authority for each permission and add to user
                generalUserPermissions.forEach(permission -> {
                        UserPermission userPermission = new UserPermission();
                        userPermission.setUser(user);
                        userPermission.setPermission(permission);

                        user.addUserPermission(userPermission);
                });

                return user;
        }
}

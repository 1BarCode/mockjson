package com.mycompany.mockjson.auth.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepo roleRepo;

    public Role findByName(RoleName name) throws Exception {
        return roleRepo.findByName(name).orElseThrow(() -> new Exception("Something went wrong"));
    }
}

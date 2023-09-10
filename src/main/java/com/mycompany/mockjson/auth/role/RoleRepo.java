package com.mycompany.mockjson.auth.role;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepo extends JpaRepository<Role, UUID> {
    @Query("SELECT r FROM Role r WHERE r.name = ?1")
    Role findByName(RoleName name);
}

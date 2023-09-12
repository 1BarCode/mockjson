package com.mycompany.mockjson.auth.permission;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PermissionRepo extends JpaRepository<Permission, UUID> {
    @Query("SELECT p FROM Permission p WHERE p.name = ?1")
    Optional<Permission> findByName(PermissionName name);

    @Query("SELECT p FROM Permission p WHERE p.name LIKE ?1")
    List<Permission> findByNameStartsWith(PermissionName name);
}

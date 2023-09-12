package com.mycompany.mockjson.user;

import java.net.URI;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.mockjson.exception.DuplicateResourceException;
import com.mycompany.mockjson.exception.IdMismatchException;
import com.mycompany.mockjson.exception.ResourceNotFoundException;
import com.mycompany.mockjson.util.validation.Update;
import com.mycompany.mockjson.util.validation.Validation;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

@RestController
@RequestMapping("/v1/users")
@PreAuthorize("hasAnyAuthority('general_admin:read', 'general_admin:write')")
@Validated
// @Secured({"ROLE_GENERAL_ADMIN", "ROLE_GENERAL_USER"})
// also equiv to @PreAuthorize("hasRole('GENERAL_ADMIN') or
// hasRole('GENERAL_USER')")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody User user) throws DuplicateResourceException {

        User createdUser = userService.createUser(user);
        URI uri = URI.create("/v1/users/" + createdUser.getId());

        return ResponseEntity.created(uri).body(createdUser);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('general_admin:read')")
    public ResponseEntity<?> listUsers() {
        return ResponseEntity.ok(userService.listUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(
            @PathVariable("id") @Size(min = 36, max = 36, message = "Id must be 36 characters") String id) {

        try {
            UUID uuid = Validation.getUUIDFromString(id);
            User user = userService.getUserById(uuid);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserById(
            @PathVariable("id") @Size(min = 36, max = 36, message = "Id must be 36 characters") String id,
            @Validated(Update.class) @RequestBody User user) throws IdMismatchException {

        UUID uuid = null;

        try {
            uuid = Validation.getUUIDFromString(id);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }

        if (!uuid.equals(user.getId())) {
            throw new IdMismatchException("Id in path does not match id in request body");
        }

        try {
            User updatedUser = userService.updateUserById(uuid, user);
            return ResponseEntity.ok(updatedUser);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(
            @PathVariable("id") @Size(min = 36, max = 36, message = "Id must be 36 characters") String id) {

        try {
            UUID uuid = Validation.getUUIDFromString(id);
            userService.deleteUserById(uuid);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

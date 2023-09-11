package com.mycompany.mockjson.auth.userpermission;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;

import com.mycompany.mockjson.auth.permission.Permission;
import com.mycompany.mockjson.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;

@Entity
@Table(name = "user_permission")
public class UserPermission {
    @EmbeddedId
    private UserPermissionId id = new UserPermissionId();

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Instant createdAt;

    public UserPermissionId getId() {
        return id;
    }

    public void setId(UserPermissionId id) {
        this.id = id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Transient
    public User getUser() {
        return id.getUser();
    }

    public void setUser(User user) {
        id.setUser(user);
    }

    @Transient
    public Permission getPermission() {
        return id.getpermission();
    }

    public void setPermission(Permission permission) {
        id.setpermission(permission);
    }

    @Override
    public String toString() {
        return "Authority [" + id.getUser().getUsername() + ", " + id.getpermission().getName() + "]";
    }

}

package com.mycompany.mockjson.auth.authority;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;

import com.mycompany.mockjson.auth.role.Role;
import com.mycompany.mockjson.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;

@Entity
@Table(name = "authority")
public class Authority {
    @EmbeddedId
    private AuthorityId id = new AuthorityId();

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Instant createdAt;

    public AuthorityId getId() {
        return id;
    }

    public void setId(AuthorityId id) {
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
    public Role getRole() {
        return id.getRole();
    }

    public void setRole(Role role) {
        id.setRole(role);
    }

    @Override
    public String toString() {
        return "Authority [" + id.getUser().getUsername() + ", " + id.getRole().getName() + "]";
    }

}

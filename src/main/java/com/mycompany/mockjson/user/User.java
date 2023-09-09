package com.mycompany.mockjson.user;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mycompany.mockjson.comment.Comment;
import com.mycompany.mockjson.post.Post;
import com.mycompany.mockjson.post.PostLike;
import com.mycompany.mockjson.task.Task;
import com.mycompany.mockjson.util.validation.Update;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "user")
public class User {
    // fields
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2") // uuid2 means uuid stored as binary(16)
    @Column(name = "id", unique = true, nullable = false, updatable = false, columnDefinition = "BINARY(16)")
    @NotNull(message = "Id cannot be null", groups = { Update.class }) // validation
    private UUID id;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    @NotNull(message = "Username cannot be null") // validation
    @Length(min = 4, max = 50, message = "Username must be between 3 and 50 characters") // validation
    private String username;

    @Column(name = "email", nullable = false, length = 50, unique = true)
    @NotNull(message = "Email cannot be null") // validation
    @Length(min = 4, max = 50, message = "Email must be between 3 and 50 characters") // validation
    @Email(message = "Email must be valid", regexp = "^[A-Za-z0-9+_.-]+@(.+)$") // validation
    private String email;

    @Column(name = "first_name", nullable = false, length = 50)
    @NotNull(message = "First name cannot be null") // validation
    @Length(min = 2, max = 50, message = "First name must be between 3 and 50 characters") // validation
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    @NotNull(message = "Last name cannot be null") // validation
    @Length(min = 2, max = 50, message = "Last name must be between 3 and 50 characters") // validation
    private String lastName;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    // relationships
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "id.user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<PostLike> postLikes = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Task> tasks = new ArrayList<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", email=" + email + ", firstName=" + firstName
                + ", lastName=" + lastName + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public void addPost(Post post) {
        posts.add(post);
        post.setUser(this);
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setUser(this);
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Set<PostLike> getPostLikes() {
        return postLikes;
    }

    public void addPostLike(PostLike postLike) {
        postLikes.add(postLike);
        postLike.getId().setUser(this);
    }

    public void setPostLikes(Set<PostLike> postLikes) {
        this.postLikes = postLikes;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

}

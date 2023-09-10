package com.mycompany.mockjson.post;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import com.mycompany.mockjson.comment.Comment;
import com.mycompany.mockjson.user.User;
import com.mycompany.mockjson.util.validation.Update;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", unique = true, nullable = false, updatable = false, columnDefinition = "BINARY(16) DEFAULT (UUID_TO_BIN(UUID()))")
    @NotNull(message = "Id cannot be null", groups = { Update.class })
    private UUID id;

    @Column(name = "slug", nullable = false, unique = true, length = 255)
    @NotNull(message = "Slug cannot be null", groups = { Update.class })
    @Length(min = 17, max = 255, message = "Slug must be between 17 and 255 characters", groups = { Update.class })
    private String slug;

    @Column(name = "title", nullable = false, length = 50)
    @NotNull(message = "Title cannot be null")
    @Length(min = 5, max = 50, message = "Title must be between 5 and 50 characters")
    private String title;

    @Column(name = "content", nullable = false)
    @NotNull(message = "Content cannot be null")
    @Length(min = 25, message = "Content must be at least 25 characters")
    private String content;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Instant createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Instant updatedAt;

    // relationships
    @ManyToOne(fetch = FetchType.LAZY) // when post is deleted no need to delete user (no cascade by default)
    @JoinColumn(name = "user_id")
    private User user;

    // when post is deleted also delete all comments
    @OneToMany(mappedBy = "post", cascade = { CascadeType.ALL }, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    // post likes mapped by postlikeid's post
    @OneToMany(mappedBy = "id.post", cascade = { CascadeType.ALL }, orphanRemoval = true)
    private List<PostLike> postLikes;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<PostLike> getPostLikes() {
        return postLikes;
    }

    public void setPostLikes(List<PostLike> postLikes) {
        this.postLikes = postLikes;
    }

    @Override
    public String toString() {
        return "Post [id=" + id + ", title=" + title + ", content=" + content + ", createdAt=" + createdAt
                + ", updatedAt=" + updatedAt + ", user=" + user.getId() + "]";
    }

}

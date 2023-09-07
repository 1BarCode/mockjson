package com.mycompany.mockjson.post;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;

import com.mycompany.mockjson.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "post_like")
public class PostLike {
    @EmbeddedId
    @Column(name = "id", nullable = false, unique = true)
    private PostLikeId id;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    // relationships

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToMany
    @JoinColumn(name = "user_id")
    private User user;

    public PostLikeId getId() {
        return id;
    }

    public void setId(PostLikeId id) {
        this.id = id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "PostLike [id=" + id + ", createdAt=" + createdAt + ", post=" + post.getId() + ", user=" + user.getId()
                + "]";
    }

}

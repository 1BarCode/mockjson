package com.mycompany.mockjson.post;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class PostLikeId implements Serializable {
    private int postId;
    private int userId;

    public PostLikeId() {
    }

    public PostLikeId(int postId, int userId) {
        this.postId = postId;
        this.userId = userId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}

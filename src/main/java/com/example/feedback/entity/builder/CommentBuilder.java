package com.example.feedback.entity.builder;

import com.example.feedback.entity.Comment;
import com.example.feedback.entity.Policy;
import com.example.feedback.entity.User;

import java.util.ArrayList;
import java.util.List;

public class CommentBuilder {

    private String commentId;
    private String category;
    private String commentBody;
    private boolean anonymous;
    private User user;
    private Policy policy;

    public CommentBuilder setCommentId(String commentId) {
        this.commentId = commentId;
        return this;
    }

    public CommentBuilder setPolicy(Policy policy) {
        this.policy = policy;
        return this;
    }

    public CommentBuilder setCategory(String category) {
        this.category = category;
        return this;
    }

    public CommentBuilder setCommentBody(String commentBody) {
        this.commentBody = commentBody;
        return this;
    }

    public CommentBuilder setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
        return this;
    }

    public CommentBuilder setUser(User user) {
        this.user = user;
        return this;
    }

    public Comment build(){
        return new Comment(commentId, category,commentBody,anonymous,user, policy);
    }
}

package com.example.feedback.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


//@Document(collection = "putComment")
//@Setter
@ToString
@NoArgsConstructor
@Document
public class Comment {

    @Id
    private String commentId;
    private String category;
    private String commentBody;
    private boolean anonymous;
    private User user;
    private Policy policy;

    public Comment(String commentId, String category, String commentBody, boolean anonymous, User user, Policy policy) {
        super();
        this.commentId = commentId;
        this.category = category;
        this.commentBody = commentBody;
        this.anonymous = anonymous;
        this.user = user;
        this.policy = policy;
    }

    public String getCommentId() {
        return commentId;
    }

    public String getCategory() {
        return category;
    }

    public String getCommentBody() {
        return commentBody;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public User getUser() {
        return user;
    }

    public Policy getPolicy() {
        return policy;
    }
}

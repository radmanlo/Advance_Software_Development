package com.example.feedback.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


//@Document(collection = "comment")
//@Setter
@ToString
@NoArgsConstructor
public class Comment {

    private String category;
    private String commentBody;
    private boolean anonymous;
    private User user;

    public Comment(String category, String commentBody, boolean anonymous, User user) {
        super();
        this.category = category;
        this.commentBody = commentBody;
        this.anonymous = anonymous;
        this.user = user;
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

}

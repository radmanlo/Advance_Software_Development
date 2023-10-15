package com.example.feedback.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Document(collection = "comment")
public class Comment {

    @Id
    private String commentId;
    private String category;
    private String commentBody;
    private boolean anonymous;
    private User user;
}

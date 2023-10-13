package com.example.feedback.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "policy")
public class Policy {

    private String name;
    private String category;
    private String duration;
    private String description;
    private int likes;
    private List<Comment> PolicyComment;
}

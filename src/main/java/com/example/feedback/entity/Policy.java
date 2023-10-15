package com.example.feedback.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@NonNull
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "policy")
public class Policy {

    @Id
    private String policyId;
    private String name;
    private String description;
    private String category;
    private String duration;
    private int likes;
    private List<Comment> PolicyComment = new ArrayList<Comment>();
}

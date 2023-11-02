package com.example.feedback.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@ToString
@Document(collection = "policy")
public class Policy {

    @Id
    private String policyId;
    private String name;
    private String description;
    private String category;
    private String duration;
    private int likes;
    private List<Comment> policyComments = new ArrayList<Comment>();

    public Policy(String policyId,
                  String name,
                  String description,
                  String category,
                  String duration,
                  int likes,
                  List<Comment> policyComments) {
        super();
        this.policyId = policyId;
        this.name = name;
        this.description = description;
        this.category = category;
        this.duration = duration;
        this.likes = likes;
        this.policyComments = policyComments;
    }

    public String getPolicyId() {
        return policyId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getDuration() {
        return duration;
    }

    public int getLikes() {
        return likes;
    }

    public List<Comment> getPolicyComments() {
        return policyComments;
    }

}

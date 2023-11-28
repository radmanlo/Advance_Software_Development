package com.example.feedback.entity;

import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@ToString
@Document(collection = "rating")
public class Rating {

    @Id
    private String ratingId;
    private int satisfaction;
    private int clarity;
    private int coverage;
    private User user;
    private Policy policy;

    public Rating(String ratingId, int satisfaction, int clarity, int coverage, User user, Policy policy) {
        this.ratingId = ratingId;
        this.satisfaction = satisfaction;
        this.clarity = clarity;
        this.coverage = coverage;
        this.user = user;
        this.policy = policy;
    }

    public String getRatingId() {
        return ratingId;
    }

    public int getSatisfaction() {
        return satisfaction;
    }
    public int getClarity() {
        return clarity;
    }

    public int getCoverage() {
        return coverage;
    }

    public User getUser() {
        return user;
    }

    public Policy getPolicy() {
        return policy;
    }
}

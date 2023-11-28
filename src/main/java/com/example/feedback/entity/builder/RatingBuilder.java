package com.example.feedback.entity.builder;

import com.example.feedback.entity.Policy;
import com.example.feedback.entity.Rating;
import com.example.feedback.entity.User;

public class RatingBuilder {

    private String ratingId;
    private int satisfaction;
    private int clarity;
    private int coverage;
    private User user;
    private Policy policy;

    public RatingBuilder setRatingId(String ratingId) {
        this.ratingId = ratingId;
        return this;
    }

    public RatingBuilder setSatisfaction(int satisfaction) {
        this.satisfaction = satisfaction;
        return this;
    }

    public RatingBuilder setClarity(int clarity) {
        this.clarity = clarity;
        return this;
    }

    public RatingBuilder setCoverage(int coverage) {
        this.coverage = coverage;
        return this;
    }

    public RatingBuilder setUser(User user) {
        this.user = user;
        return this;
    }

    public RatingBuilder setPolicy(Policy policy) {
        this.policy = policy;
        return this;
    }

    public Rating build(){
        return new Rating(ratingId, satisfaction, clarity, coverage, user, policy);
    }
}

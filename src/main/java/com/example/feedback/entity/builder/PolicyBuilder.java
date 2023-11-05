package com.example.feedback.entity.builder;

import com.example.feedback.entity.Comment;
import com.example.feedback.entity.Policy;

import java.util.ArrayList;
import java.util.List;

public class PolicyBuilder {

    private String policyId;
    private String name;
    private String description;
    private String category;
    private String duration;
    private int likes;
//    private List<Comment> policyComments = new ArrayList<Comment>();

    public PolicyBuilder setPolicyId(String policyId) {
        this.policyId = policyId;
        return this;
    }

    public PolicyBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public PolicyBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public PolicyBuilder setCategory(String category) {
        this.category = category;
        return this;
    }

    public PolicyBuilder setDuration(String duration) {
        this.duration = duration;
        return this;
    }

    public PolicyBuilder setLikes(int likes) {
        this.likes = likes;
        return this;
    }

//    public PolicyBuilder setPolicyComments(List<Comment> policyComment) {
//        policyComments = policyComment;
//        return this;
//    }

    public Policy build(){
        return new Policy(policyId,name,description,category,duration,likes);
    }
}

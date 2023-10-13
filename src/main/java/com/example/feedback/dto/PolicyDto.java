package com.example.feedback.dto;


import com.example.feedback.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PolicyDto {

    ObjectId policyId;
    private String name;
    private String category;
    private String duration;
    private String description;
    private int likes;
    private List<Comment> PolicyComment;
}

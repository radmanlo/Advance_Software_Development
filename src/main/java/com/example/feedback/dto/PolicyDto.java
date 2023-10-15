package com.example.feedback.dto;


import com.example.feedback.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PolicyDto {

    private String policyId;
    private String name;
    private String description;
    private String category;
    private String duration;
    private int likes;
    private List<Comment> PolicyComment = new ArrayList<Comment>();
}

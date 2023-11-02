package com.example.feedback.dto;


import com.example.feedback.entity.Comment;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PolicyDto {

    private String policyId;
    private String name;
    private String description;
    private String category;
    private String duration;
    private int likes;
    private List<Comment> policyComments = new ArrayList<Comment>();
}

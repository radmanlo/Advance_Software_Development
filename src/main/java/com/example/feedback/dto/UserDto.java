package com.example.feedback.dto;

import com.example.feedback.entity.Comment;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.List;

//@Data

@Getter
@Builder
@ToString
public class UserDto {

    //private ObjectId _id;

    private String email;
    private String firstName;
    private String lastName;
    private int points;

    //private List<Comment> userComments;
}

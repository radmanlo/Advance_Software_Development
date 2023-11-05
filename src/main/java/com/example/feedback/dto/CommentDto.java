package com.example.feedback.dto;


import com.example.feedback.entity.Comment;
import com.example.feedback.entity.User;
import lombok.*;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class CommentDto {

    private String commentId;
    private String category;
    private String commentBody;
    private boolean anonymous;
    private UserDto userDto;
    private PolicyDto policyDto;
//    private List<Comment> comments = new ArrayList<Comment>();

}

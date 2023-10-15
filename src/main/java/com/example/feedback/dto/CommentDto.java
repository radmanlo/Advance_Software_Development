package com.example.feedback.dto;


import com.example.feedback.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    //ObjectId commentObjectId;
    private String category;
    private String commentBody;
    private boolean anonymous;
    private User user;

}

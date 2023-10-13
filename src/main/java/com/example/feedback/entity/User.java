package com.example.feedback.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user")
public class User {

//    @Id
    private ObjectId _id;
    private String firstName;
    private String lastName;
    @Indexed(unique = true)
    private String email;
    private int points;
    //private List<Comment> userComments;
}

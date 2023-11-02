package com.example.feedback.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Document(collection = "user")
@ToString
public class User {

    @Id
    private String email;
    private String firstName;
    private String lastName;
    private int points;

    public User(String email, String firstName, String lastName, int points) {
        super();
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.points = points;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getPoints() {
        return points;
    }
}

package com.example.feedback.entity.builder;

import com.example.feedback.entity.User;

public class UserBuilder {

    private String email;
    private String firstName;
    private String lastName;
    private int points;

    public UserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserBuilder setPoints(int points) {
        this.points = points;
        return this;
    }

    public User build(){
        return new User(email,firstName,lastName,points);
    }
}

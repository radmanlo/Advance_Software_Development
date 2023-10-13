package com.example.feedback.service;

import com.example.feedback.dto.UserDto;

public interface UserService {

    /**
     * Create new User
     * @param userDto
     * @return UserDto if it saves in MongoDB
     * otherwise
     * @return null
     */
    UserDto createUser (UserDto userDto);

    /**
     * Find User by email
     * @param email
     * @return UserDto if find the user by provided email
     * otherwise
     * @return null
     */
    UserDto findUserByEmail (String email);

    /**
     * Update a User
     * @param userDto
     * @return UserDto an Updated User
     * otherwise
     * @return null
     */
    UserDto updateUser (UserDto userDto);

}

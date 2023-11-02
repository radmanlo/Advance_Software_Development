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
     * Update a user
     * @param userDto
     * @return UserDto if it updates successfully
     * otherwise
     * @return null
     */
    UserDto updateUser (UserDto userDto);

    /**
     * Find User by email
     * @param email
     * @return UserDto if function finds the user by provided email
     * otherwise
     * @return null
     */
    UserDto getUserByEmail (String email);

    /**
     * Delete a user
     * @param email
     * @return UserDto when it is deleted successfully
     * otherwise
     * @return null
     */
    UserDto deleteUser (String email);

    /**
     * Update a User
     * @param email
     * @return UserDto an Updated User
     * otherwise
     * @return null
     */
    UserDto updateUserPoint (String email);

}

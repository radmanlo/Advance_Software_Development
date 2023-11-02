package com.example.feedback.user;

import com.example.feedback.dto.UserDto;
import com.example.feedback.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;

    public static String email;

    @Test
    @DisplayName("Create User Test")
    @Order(1)
    public void createUser(){

        // Create a Test UserDto
//        UserDto testUser = new UserDto();
//        testUser.setEmail("test@test.com");
//        testUser.setFirstName("Test First Name");
//        testUser.setLastName("Test Last Name");
//        testUser.setPoints(0);

        // Retrieve the created User
//        UserDto createTestUser = userService.createUser(testUser);
//
//        // Test
//        assertNotNull(createTestUser, "Created user is null");
//        assertEquals(testUser.getEmail(), createTestUser.getEmail(), "Email Attribute");
//        assertEquals(testUser.getFirstName(), createTestUser.getFirstName(), "First Name Attribute");
//        assertEquals(testUser.getLastName(), createTestUser.getLastName(), "Lastname Attribute");
//        assertEquals(testUser.getPoints(), createTestUser.getPoints(), "Point Attribute");
//
//        email = createTestUser.getEmail();
    }

    @Test
    @DisplayName("Update User test")
    @Order(2)
    public void updateUser(){

        // Create an Update User
//        UserDto updateTest = new UserDto(
//                email,
//                "Updated First Name",
//                "Updated Last Name",
//                10
//        );
//
//        // Retrieve Updated User
//        UserDto updatedUserTest = userService.updateUser(updateTest);
//
//        // Test
//        assertNotNull(updatedUserTest, "Updated User is null");
//        assertEquals(updateTest.getEmail(), updatedUserTest.getEmail(), "Email Attribute");
//        assertEquals(updateTest.getFirstName(), updatedUserTest.getFirstName(), "First Name Attribute");
//        assertEquals(updateTest.getLastName(), updatedUserTest.getLastName(), "Last Name Attribute");
//        assertEquals(updateTest.getPoints(), updatedUserTest.getPoints(), "Point Attribute");

    }

    @Test
    @DisplayName("Get User Test")
    @Order(3)
    public void getUser (){

        // Retrieve a User
        UserDto getUserTest = userService.getUserByEmail(email);

        // Test
        assertNotNull(getUserTest, "Get User is null");
        assertEquals(email, getUserTest.getEmail(), "Email Attribute");
    }

    @Test
    @DisplayName("Delete User Test")
    @Order(4)
    public void deleteUser (){

        // Retrieve deleted User
        UserDto deletedUser = userService.deleteUser(email);

        assertNotNull(deletedUser, "Deleted User is null");
    }
}

package com.example.feedback;

import com.example.feedback.dto.CommentDto;
import com.example.feedback.dto.PolicyDto;
import com.example.feedback.dto.RatingDto;
import com.example.feedback.dto.UserDto;
import com.example.feedback.service.UserService;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.*;
import com.example.feedback.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class ApplicationTest {

    @Autowired
    PolicyService policyService;
    @Autowired
    UserService userService;
    static private String commentId;
    private static String policyId;
    private static String userEmail;
    private static String ratingId;

    @Test
    @DisplayName("Create User Test")
    @Order(1)
    public void createUser(){

        // Create a Test UserDto
        UserDto testUser = UserDto.builder()
                .email("test@test.com")
                .firstName("Test First Name")
                .lastName("Test Last Name")
                .build();

        // Retrieve the created User
        UserDto createTestUser = userService.createUser(testUser);

        // Test
        assertNotNull(createTestUser, "Created user is null");
        assertEquals(testUser.getEmail(), createTestUser.getEmail(), "Email Attribute");
        assertEquals(testUser.getFirstName(), createTestUser.getFirstName(), "First Name Attribute");
        assertEquals(testUser.getLastName(), createTestUser.getLastName(), "Lastname Attribute");
        assertEquals(testUser.getPoints(), createTestUser.getPoints(), "Point Attribute");

        userEmail = createTestUser.getEmail();
    }

    @Test
    @DisplayName("Update User test")
    @Order(2)
    public void updateUser(){

        // Create an Update User
        UserDto updateTest = UserDto.builder()
                .email(userEmail)
                .firstName("Updated First Name")
                .lastName("Updated Last Name")
                .points(10)
                .build();

        // Retrieve Updated User
        UserDto updatedUserTest = userService.updateUser(updateTest);

        // Test
        assertNotNull(updatedUserTest, "Updated User is null");
        assertEquals(updateTest.getFirstName(), updatedUserTest.getFirstName(), "First Name Attribute");
        assertEquals(updateTest.getLastName(), updatedUserTest.getLastName(), "Last Name Attribute");
        assertEquals(updateTest.getPoints(), updatedUserTest.getPoints(), "Point Attribute");

    }

    @Test
    @DisplayName("Get User Test")
    @Order(3)
    public void getUser (){

        // Retrieve a User
        UserDto getUserTest = userService.getUserByEmail(userEmail);

        // Test
        assertNotNull(getUserTest, "Get User is null");
        assertEquals(userEmail, getUserTest.getEmail(), "Email Attribute");
    }

    @Test
    @DisplayName("Create Policy Test")
    @Order(4)
    public void createPolicyTest(){
        // Create a Dummy user
        PolicyDto policyTest = PolicyDto.builder()
                .name("Test Name")
                .description("Test Description")
                .category("Test Category")
                .duration("Test Duration")
                .build();

        // Retrieve user from createPolicy function
        PolicyDto retrievedPolicy = policyService.createPolicy(policyTest);

        // test different attribute of Policy
        assertNotNull(retrievedPolicy, "Policy Object is null");
        assertNotNull(retrievedPolicy.getPolicyId(), "Policy Id is null");
        assertNotNull(retrievedPolicy.getName(), "Policy Name is null");
        assertNotNull(retrievedPolicy.getDescription(), "Policy Description is null");
        assertNotNull(retrievedPolicy.getCategory(), "Policy Category is null");
        assertNotNull(retrievedPolicy.getDuration(), "Policy Duration is null");

        // store the policyId for further tests
        policyId = retrievedPolicy.getPolicyId();

    }

    @Test
    @DisplayName("Update Policy Test")
    @Order(5)
    public void updatePolicyTest(){

        // Update the Dummy user
        PolicyDto policyTest = PolicyDto.builder()
                .policyId(policyId)
                .name("Updated Test Name")
                .duration("Updated Test Duration")
                .description("Updated Test Description")
                .category("Updated Category")
                .build();

        // Retrieve user from updatePolicy function
        PolicyDto retrievedPolicy = policyService.updatePolicy(policyTest);

        // Test Update Policy
        assertNotNull(retrievedPolicy, "Policy is null");
        assertEquals(policyTest.getName(), retrievedPolicy.getName(), "Name is not updated");
        assertEquals(policyTest.getDescription(), retrievedPolicy.getDescription(), "Description is not updated");
        assertEquals(policyTest.getCategory(), retrievedPolicy.getCategory(), "Category is not updated");
        assertEquals(policyTest.getDuration(), retrievedPolicy.getDuration(), "Duration is not Updated");
    }

    @Test
    @DisplayName ("Get Policy By Id")
    @Order(6)
    public void getPolicy(){

        // Retrieve Policy from getPolicyById
        PolicyDto policyDto = policyService.getPolicyById(policyId);

        // Test it is not null
        assertNotNull(policyDto, "Policy is null");

    }

    @Test
    @DisplayName("Get All Policies")
    @Order(7)
    public void getAllPolicies(){

        // Retrieve all Policy from getAllPolicy
        List<PolicyDto> listPolicy = policyService.getAllPolicies();

        // Test
        assertNotNull(listPolicy, "List user is null");
        assertNotEquals(0, listPolicy.size(), "There is not any user in database");
    }

    @Test
    @DisplayName("Create a Comment for Policy API")
    @Order(8)
    public void CreateCommentAPI() {

        UserDto testUser = UserDto.builder()
                .email(userEmail)
                .build();

        PolicyDto testPolicy = PolicyDto.builder()
                .policyId(policyId)
                .build();

        CommentDto testComment = CommentDto.builder()
                .userDto(testUser)
                .policyDto(testPolicy)
                .commentBody("Test Comment Body")
                .category("Test Comment Category")
                .build();

        ValidatableResponse response = given()
                .contentType(ContentType.JSON)
                .body(testComment)
                .when()
                .post("http://localhost:5050/comment/create")
                .then()
                .statusCode(201)
                .body("userDto.email", equalTo(testComment.getUserDto().getEmail()))
                .body("policyDto.policyId", equalTo(policyId))
                .body("commentBody", equalTo(testComment.getCommentBody()))
                .body("category", equalTo(testComment.getCategory()))
                .contentType(ContentType.JSON);

        CommentDto res = response.extract().as(CommentDto.class);
        commentId = res.getCommentId();
    }

    @Test
    @DisplayName("Get a Comment By Comment Id API")
    @Order(9)
    public void getCommentByCommentId(){
        given()
                .get("http://localhost:5050/comment/getById?commentId="+commentId)
                .then()
                .statusCode(302)
                .body("commentId", equalTo(commentId))
                .contentType(ContentType.JSON);
    }

    @Test
    @DisplayName("Get a Comment By PolicyId API")
    @Order(10)
    public void getCommentByPolicyId(){

        Response response = given().get("http://localhost:5050/comment/getByPolicyId?policyId=" + policyId);
        List<String> commentIds = response.jsonPath().getList("commentId");

        // Test
        assertEquals(302, response.getStatusCode(), "Status Code Attribute");
        assertFalse(commentIds.isEmpty(), "No comments found for the given policyId");
        String actualCommentId = commentIds.get(0);

        // Perform the assertion
        assertEquals(commentId, actualCommentId, "CommentId Attribute");
        response.then().contentType(ContentType.JSON);
    }

    @Test
    @DisplayName("Get a Comment By userEmail API")
    @Order(11)
    public void getCommentByUserEmail(){
        Response response = given().get("http://localhost:5050/comment/getByUserEmail?userEmail="+userEmail);
        List<String> commentIds = response.jsonPath().getList("commentId");

        // Test
        assertEquals(302, response.getStatusCode(), "Status Code Attribute");
        assertFalse(commentIds.isEmpty(), "No comments found for the given policyId");
        String actualCommentId = commentIds.get(0);

        // Perform the assertion
        assertEquals(commentId, actualCommentId, "CommentId Attribute");
        response.then().contentType(ContentType.JSON);
    }

    @Test
    @DisplayName("Update a Comment By commentId API")
    @Order(12)
    public void updateCommentByUserEmail(){
        UserDto testUser = UserDto.builder()
                .email(userEmail)
                .build();

        PolicyDto testPolicy = PolicyDto.builder()
                .policyId(policyId)
                .build();

        CommentDto updatedComment = CommentDto.builder()
                .commentId(commentId)
                .userDto(testUser)
                .policyDto(testPolicy)
                .commentBody("Updated Test Comment Body")
                .category("Updated Test Comment Category")
                .build();

        given()
                .contentType(ContentType.JSON)
                .body(updatedComment)
                .when()
                .put("http://localhost:5050/comment/update")
                .then()
                .statusCode(200)
                .body("commentId", equalTo(commentId))
                .body("commentBody", equalTo("Updated Test Comment Body"))
                .body("category", equalTo("Updated Test Comment Category"))
                .contentType(ContentType.JSON);

    }

    @Test
    @DisplayName("Create a Rating API")
    @Order(13)
    public void createRating(){
        UserDto testUser = UserDto.builder()
                .email(userEmail)
                .build();

        PolicyDto testPolicy = PolicyDto.builder()
                .policyId(policyId)
                .build();

        RatingDto testRating = RatingDto.builder()
                .satisfaction(5)
                .clarity(5)
                .coverage(5)
                .policyDto(testPolicy)
                .userDto(testUser)
                .build();

        ValidatableResponse response = given()
                .contentType(ContentType.JSON)
                .body(testRating)
                .when()
                .post("http://localhost:5050/rating/create")
                .then()
                .statusCode(201)
                .body("userDto.email", equalTo(testRating.getUserDto().getEmail()))
                .body("policyDto.policyId", equalTo(policyId))
                .body("satisfaction", equalTo(testRating.getSatisfaction()))
                .body("clarity", equalTo(testRating.getClarity()))
                .body("coverage", equalTo(testRating.getCoverage()))
                .contentType(ContentType.JSON);

        RatingDto res = response.extract().as(RatingDto.class);
        ratingId = res.getRatingId();
    }



    @Test
    @DisplayName("Delete a Rating API")
    @Order(15)
    public void deleteRating(){
        given()
                .delete("http://localhost:5050/rating/delete?ratingId="+ratingId)
                .then()
                .statusCode(200)
                .body("ratingId", equalTo(ratingId))
                .contentType(ContentType.JSON);
    }

    @Test
    @DisplayName("Delete a Comment By CommentId API")
    @Order(15)
    public void deleteCommentByCommentId(){
        given()
                .delete("http://localhost:5050/comment/delete?commentId="+commentId)
                .then()
                .statusCode(200)
                .body("commentId", equalTo(commentId))
                .contentType(ContentType.JSON);
    }

    @Test
    @DisplayName("Delete Policy Service")
    @Order(16)
    public void deletePolicy (){

        PolicyDto policyTest = PolicyDto.builder()
                .policyId(policyId)
                .name("Updated Test Name")
                .description("Updated Test Description")
                .category("Updated Category")
                .duration("Updated Test Duration")
                .build();

        // Retrieve deleted Policy
        PolicyDto deletedPolicy = policyService.deletePolicy(policyId);

        // Test
        assertNotNull(deletedPolicy, "Deleted user is null");
    }



    @Test
    @DisplayName("Delete User Test")
    @Order(17)
    public void deleteUser (){

        // Retrieve deleted User
        UserDto deletedUser = userService.deleteUser(userEmail);

        assertNotNull(deletedUser, "Deleted User is null");
    }
}

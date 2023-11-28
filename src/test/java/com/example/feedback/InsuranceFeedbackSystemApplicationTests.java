package com.example.feedback;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
class InsuranceFeedbackSystemApplicationTests {

    @Test
    void contextLoads() {
    }

//    @Test
//    @DisplayName("Get a Rating by userEmail API")
//    @Order(14)
//    public void getRatingByUserEmail (){
//        given()
//                .get("http://localhost:5050/rating/byUserEmail?userEmail="+userEmail)
//                .then()
//                .statusCode(302)
//                .body("ratingId", equalTo(ratingId))
//                .contentType(ContentType.JSON);
//    }
//    @Test
//    @DisplayName("Delete a Rating API")
//    @Order(15)
}

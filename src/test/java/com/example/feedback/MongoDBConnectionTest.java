package com.example.feedback;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.net.ConnectException;

import static org.junit.jupiter.api.Assertions.*;


public class MongoDBConnectionTest {

    private static final Logger logger = LoggerFactory.getLogger(MongoDBConnectionTest.class);


    @Test
    @DisplayName("Test MongoDB Connection")
    public void testMongoDBConnection() {

        logger.info("Starting the MongoDB connection test...");

        String connectionString = "mongodb://localhost:27017";
        MongoClient mongoClient;
        MongoDatabase mongoDatabase;
        mongoClient = MongoClients.create(MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .build());
        try{
            assertEquals("Insurance", mongoClient.getDatabase("Insurance").getName(), "Test is failed");
            mongoClient.close();
        } catch (Exception e){
            fail("Test is failed because => " + e.getMessage());
        }
    }
}

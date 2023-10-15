package com.example.feedback.repository;

import com.example.feedback.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> deleteByEmail (String email);

    //Optional<User>
    //@Query("{email: ?0}")
   // Optional<User> findUsersByEmail (String email);
}

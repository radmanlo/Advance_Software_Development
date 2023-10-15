package com.example.feedback.repository;

import com.example.feedback.entity.Policy;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PolicyRepository extends MongoRepository<Policy, String> {


    Optional<Policy> deleteByPolicyId(String id);
}

package com.example.feedback.repository;

import com.example.feedback.entity.Policy;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PolicyRepository extends MongoRepository<Policy, ObjectId> {
}

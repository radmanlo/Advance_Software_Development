package com.example.feedback.repository;

import com.example.feedback.entity.Policy;
import com.example.feedback.entity.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends MongoRepository<Rating, String> {

    Optional<Rating> findByUserEmailAndPolicy_PolicyId(String userEmail, String policyId);

    Optional<List<Rating>> findByUserEmail(String userEmail);

    Optional<List<Rating>> findByPolicy_PolicyId(String policyId);

    Optional<Rating> deleteByRatingId(String ratingId);
}

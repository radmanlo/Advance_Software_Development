package com.example.feedback.repository;

import com.example.feedback.entity.Comment;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends MongoRepository<Comment, String> {

    Optional<List<Comment>> findByUserEmail(String userEmail);

    Optional<List<Comment>> findByPolicyPolicyId(String policyId);

    Comment deleteCommentByCommentId(String CommentId);
}

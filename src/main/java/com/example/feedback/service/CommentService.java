package com.example.feedback.service;

import com.example.feedback.dto.CommentDto;
import com.example.feedback.dto.PolicyDto;
import org.bson.types.ObjectId;

public interface CommentService {

    /**
     * Create a new comment
     * @param commentDto
     * @return
     */
    CommentDto createComment (CommentDto commentDto);

    /**
     * Create a comment
     * @param commentDto
     * @param policyId
     * @param email
     * @return comment if it saves to mongoDb
     * otherwise
     * @return null
     */
    CommentDto addCommentToPolicy (CommentDto commentDto, ObjectId policyId, String email);
}

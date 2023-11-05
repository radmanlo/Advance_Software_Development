package com.example.feedback.service;

import com.example.feedback.dto.CommentDto;

import java.util.List;

public interface CommentService {

    /**
     * Create a new putComment
     * @param commentDto
     * @return
     */
    CommentDto createComment (CommentDto commentDto);

    /**
     * Get Comment by its Id
     * @param commentId
     * @return CommentDto if it was successful
     * otherwise
     * @return null
     */
    CommentDto getById (String commentId);

    /**
     * Update a putComment by id
     * @param commentDto
     * @return updated CommentDto if it was successful
     * otherwise
     * @return null
     */
    CommentDto updateComment (CommentDto commentDto);

    /**
     * Get all Comments of a user by its email
     * @param userEmail
     * @return list of CommentDto if it finds
     * otherwise
     * @return null
     */
    List<CommentDto> getUserComments (String userEmail);

    List<CommentDto> getPolicyComments (String policyId);

    /**
     * Delete a document by its id
     * @param commentId
     * @return CommentDto if it is deleted successfully
     * otherwise
     * @return null
     */
    CommentDto deleteComment (String commentId);


}

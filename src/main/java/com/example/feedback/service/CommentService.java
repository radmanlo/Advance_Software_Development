package com.example.feedback.service;

import com.example.feedback.dto.CommentDto;
import com.example.feedback.dto.PolicyDto;
import org.bson.types.ObjectId;

import java.util.List;

public interface CommentService {

    /**
     * Create a new comment
     * @param commentDto
     * @return
     */
    CommentDto createComment (CommentDto commentDto);

    /**
     * Update a comment by id
     * @param commentId
     * @return updated CommentDto if it was successful
     * otherwise
     * @return null
     */
    CommentDto updateComment (String commentId);

    /**
     * Get all Comments of a user by its email
     * @param userEmail
     * @return list of CommentDto if it finds
     * otherwise
     * @return null
     */
    List<CommentDto> getUserComments (String userEmail);

    /**
     * Delete a document by its id
     * @param commentId
     * @return CommentDto if it is deleted successfully
     * otherwise
     * @return null
     */
    CommentDto deleteComment (String commentId);

    /**
     * Add a comment to another comment
     * @param commentId
     * @param commentDto
     * @return updated CommentDto if it was successful
     * otherwise
     * @return null
     */
    CommentDto addCommentToComment (String commentId, CommentDto commentDto);

}

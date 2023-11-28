package com.example.feedback.service;

import com.example.feedback.dto.CommentDto;
import com.example.feedback.dto.PolicyDto;
import com.example.feedback.dto.UserDto;
import com.example.feedback.entity.Comment;
import com.example.feedback.entity.Policy;
import com.example.feedback.entity.User;
import org.bson.types.ObjectId;

import java.util.List;

public interface PolicyService {

    /**
     * Create a new user
     * @param policyDto
     * @return policyDto if it saves in MongoDB
     * otherwise
     * @return null
     */
    PolicyDto createPolicy (PolicyDto policyDto);

    /**
     * Update a Policy
     * @param policyDto
     * @return PolicyDto it is updated successfully
     * otherwise
     * @return null
     *
     */
    PolicyDto updatePolicy (PolicyDto policyDto);

    /**
     * Get a Policy By its id
     * @param policyId
     * @return PolicyDto if it exists
     * otherwise
     * @return null
     */
    PolicyDto getPolicyById (String policyId);

    /**
     * Return all policies
     * @return List<PolicyDto>
     * otherwise
     * @return null list
     */
    List<PolicyDto> getAllPolicies ();

    /**
     * Delete a user by its id
     * @param policyId
     * @return PolicyDto if it deleted
     * otherwise
     * @return null
     */
    PolicyDto deletePolicy (String policyId);


    /**
     * Add a putComment into the
     * @param policyId
     * @return PolicyDto with added putComment
     * otherwise
     * @return null
     */
    PolicyDto addComment (String policyId, Comment comment);



//    List<PolicyDto> findCommentByUserEmail (String userEmail);

//    PolicyDto addCommentByChain (String policyId, CommentDto commentDto);
}

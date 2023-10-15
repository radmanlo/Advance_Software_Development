package com.example.feedback.service;

import com.example.feedback.dto.PolicyDto;
import com.example.feedback.entity.Comment;
import com.example.feedback.entity.Policy;
import org.bson.types.ObjectId;

import java.util.List;

public interface PolicyService {

    /**
     * Create a new policy
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
     * Delete a policy by its id
     * @param policyId
     * @return PolicyDto if it deleted
     * otherwise
     * @return null
     */
    PolicyDto deletePolicy (String policyId);


    /**
     * Add a comment into the
     * @param policyId
     * @return PolicyDto with added comment
     * otherwise
     * @return null
     */
    PolicyDto addComment (String policyId, Comment comment);


}

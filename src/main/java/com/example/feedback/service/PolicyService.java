package com.example.feedback.service;

import com.example.feedback.dto.CommentDto;
import com.example.feedback.dto.PolicyDto;
import org.bson.types.ObjectId;

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
     * Add a comment into the
     * @param policyId
     * @return
     */
    PolicyDto addCommentToPolicy (ObjectId policyId);


}

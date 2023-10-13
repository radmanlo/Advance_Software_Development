package com.example.feedback.service;

import com.example.feedback.dto.PolicyDto;
import com.example.feedback.entity.Policy;
import com.example.feedback.repository.PolicyRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PolicyServiceImp implements PolicyService{

    @Autowired
    private PolicyRepository policyRepository;

    @Override
    public PolicyDto createPolicy(PolicyDto policyDto) {
        try{
            Policy policy = new Policy();
            policy.setPolicyComment(policyDto.getPolicyComment());
            policy.setDescription(policyDto.getDescription());
            policy.setName(policyDto.getName());
            policy.setLikes(policyDto.getLikes());
            policy.setCategory(policyDto.getCategory());
            policy.setDuration(policyDto.getDuration());
            policyRepository.save(policy);
            return policyDto;
        }catch (Exception e) {
            return null;
        }
    }

    @Override
    public PolicyDto addCommentToPolicy(ObjectId policyId) {
        return null;
    }
}

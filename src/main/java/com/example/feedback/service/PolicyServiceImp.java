package com.example.feedback.service;


import com.example.feedback.dto.PolicyDto;
import com.example.feedback.dto.UserDto;
import com.example.feedback.entity.Comment;
import com.example.feedback.entity.Policy;
import com.example.feedback.repository.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PolicyServiceImp implements PolicyService{

    @Autowired
    private PolicyRepository policyRepository;

    @Override
    public PolicyDto createPolicy(PolicyDto policyDto) {
        try{
            Policy policy = new Policy();
            //policy.setPolicyId(policyDto.getPolicyId());
            policy.setName(policyDto.getName());
            policy.setDescription(policyDto.getDescription());
            policy.setCategory(policyDto.getCategory());
            policy.setDuration(policyDto.getDuration());
            policy.setLikes(0);
            Policy createdPolicy = policyRepository.save(policy);
            System.out.println("-----------------------------------");
            System.out.println("Policy is Created");
            System.out.println("-----------------------------------");
            PolicyDto createdPolicyDto = new PolicyDto();
            createdPolicyDto.setPolicyId(createdPolicy.getPolicyId());
            createdPolicyDto.setName(createdPolicy.getName());
            createdPolicyDto.setDescription(createdPolicy.getDescription());
            createdPolicyDto.setCategory(createdPolicy.getCategory());
            createdPolicyDto.setDuration(createdPolicy.getDuration());
            createdPolicyDto.setLikes(0);
            return createdPolicyDto;
        }catch (Exception e) {
            System.out.println("Exception createPolicy in policyService ==> " + e.getMessage());
            return null;
        }
    }

    @Override
    public PolicyDto updatePolicy(PolicyDto policyDto) {
        try {
            System.out.println(policyDto);
            Optional<Policy> policy = policyRepository.findById(policyDto.getPolicyId());
            if (policy.isPresent()){
                Policy updatedPolicy = new Policy();
                updatedPolicy.setPolicyId(policyDto.getPolicyId());
                if (!policyDto.getName().isEmpty()){
                    updatedPolicy.setName(policyDto.getName());
                }
                else
                    updatedPolicy.setName(policy.get().getName());
                if (!policyDto.getDescription().isEmpty()){
                    updatedPolicy.setCategory(policy.get().getCategory());
                }
                else
                    updatedPolicy.setDuration(policy.get().getDuration());
                if (policyDto.getLikes() != 0)
                    updatedPolicy.setLikes(policyDto.getLikes());
                else
                    updatedPolicy.setLikes(policy.get().getLikes());
                updatedPolicy.setPolicyComment(policy.get().getPolicyComment());

                Policy respond = policyRepository.save(updatedPolicy);
                System.out.println("-----------------------------------");
                System.out.println("Policy is Updated");
                System.out.println("-----------------------------------");
                policyDto.setPolicyId(respond.getPolicyId());
                policyDto.setName(respond.getName());
                policyDto.setDescription(respond.getDescription());
                policyDto.setCategory(respond.getCategory());
                policyDto.setDuration(respond.getDuration());
                policyDto.setLikes(respond.getLikes());
                return policyDto;
            }
            return null;
        }catch (Exception e){
            System.out.println("Exception updatePolicy in policyService " + e.getMessage());
            return null;
        }
    }

    @Override
    public PolicyDto getPolicyById(String policyId) {
        try{
            Optional<Policy> foundPolicy = policyRepository.findById(policyId);
            if (foundPolicy.isPresent()) {
                PolicyDto policyDto = new PolicyDto();
                policyDto.setPolicyId(foundPolicy.get().getPolicyId());
                policyDto.setName(foundPolicy.get().getName());
                policyDto.setDescription(foundPolicy.get().getDescription());
                policyDto.setCategory(foundPolicy.get().getCategory());
                policyDto.setDuration(foundPolicy.get().getDuration());
                policyDto.setLikes(foundPolicy.get().getLikes());
                policyDto.setPolicyComment(foundPolicy.get().getPolicyComment());
                return policyDto;
            }
            return null;
        } catch (Exception e) {
            System.out.println("Exception getPolicyById in PolicyService" + e.getMessage());
            return null;
        }
    }

    @Override
    public List<PolicyDto> getAllPolicies() {
        try{
            List<Policy> listPolicy = policyRepository.findAll();
            List<PolicyDto> lisPolicyDto = new ArrayList<PolicyDto>();
            for (Policy item : listPolicy){
                PolicyDto policyDto = new PolicyDto(
                        item.getPolicyId(),
                        item.getName(),
                        item.getDescription(),
                        item.getCategory(),
                        item.getDuration(),
                        item.getLikes(),
                        item.getPolicyComment()
                );
                lisPolicyDto.add(policyDto);
            }
            return lisPolicyDto;
        } catch (Exception e){
            System.out.println("Exception getAllPolicies in PolicyService " + e.getMessage());
            return null;
        }
    }

    @Override
    public PolicyDto deletePolicy(String policyId) {

        try {
            Optional<Policy> deletedPolicy = policyRepository.deleteByPolicyId(policyId);
            if (deletedPolicy.isPresent()){
                PolicyDto policyDto = new PolicyDto(
                        deletedPolicy.get().getPolicyId(),
                        deletedPolicy.get().getName(),
                        deletedPolicy.get().getDescription(),
                        deletedPolicy.get().getCategory(),
                        deletedPolicy.get().getDuration(),
                        deletedPolicy.get().getLikes(),
                        deletedPolicy.get().getPolicyComment()
                );
                return policyDto;
            }
            return null;
        } catch (Exception e){
            System.out.println("Exception deletePolicy in PolicyService");
            return null;
        }

    }

    @Override
    public PolicyDto addComment(String policyId, Comment comment) {
        try{
            Optional<Policy> foundPolicy = policyRepository.findById(policyId);
            if (foundPolicy.isPresent()){
                String url = "http://localhost:5050/user/update/point?email=" + comment.getUser().getEmail();
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<UserDto> user = restTemplate.exchange(
                        url,
                        HttpMethod.PUT,
                        null,
                        UserDto.class
                );
                if (user.hasBody()){
                    Policy policy = foundPolicy.get();
                    comment.getUser().setFirstName(user.getBody().getFirstName());
                    comment.getUser().setLastName(user.getBody().getLastName());
                    policy.getPolicyComment().add(comment);
                    Policy updatedPolicy = policyRepository.save(policy);
                    System.out.println("-----------------------------------");
                    System.out.println("Comment is added to Policy");
                    System.out.println("-----------------------------------");
                    PolicyDto policyDto = new PolicyDto(
                            updatedPolicy.getPolicyId(),
                            updatedPolicy.getName(),
                            updatedPolicy.getDescription(),
                            updatedPolicy.getCategory(),
                            updatedPolicy.getDuration(),
                            updatedPolicy.getLikes(),
                            updatedPolicy.getPolicyComment());
                    return policyDto;
                }
                System.out.println("User is not found in addComment PolicyService");
                return null;
            }
            System.out.println("Policy is not found in addComment PolicyService");
            return null;
        } catch (Exception e) {
            System.out.println("Exception addComment in PolicyService ==> " + e.getMessage());
            return null;
        }
    }
}

package com.example.feedback.service;


import com.example.feedback.dto.CommentDto;
import com.example.feedback.dto.PolicyDto;
import com.example.feedback.dto.UserDto;
import com.example.feedback.entity.Comment;
import com.example.feedback.entity.Policy;
import com.example.feedback.entity.User;
import com.example.feedback.entity.builder.CommentBuilder;
import com.example.feedback.entity.builder.PolicyBuilder;
import com.example.feedback.entity.builder.UserBuilder;
import com.example.feedback.repository.PolicyRepository;
import com.example.feedback.service.chainOfResponsibility.AddCommentHandler;
import com.example.feedback.service.chainOfResponsibility.FindUserHandler;
import com.example.feedback.service.chainOfResponsibility.AddPointHandler;
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
            Policy policy = new PolicyBuilder()
                    .setName(policyDto.getName())
                    .setDescription(policyDto.getDescription())
                    .setCategory(policyDto.getCategory())
                    .setDuration(policyDto.getDuration())
                    .setLikes(policyDto.getLikes())
//                    .setPolicyComments(policyDto.getPolicyComments())
                    .build();
            Policy createdPolicy = policyRepository.save(policy);
            System.out.println("-----------------------------------");
            System.out.println("Policy is Created");
            System.out.println("-----------------------------------");
            PolicyDto createdPolicyDto = PolicyDto.builder()
                    .policyId(createdPolicy.getPolicyId())
                    .name(createdPolicy.getName())
                    .description(createdPolicy.getDescription())
                    .category(createdPolicy.getCategory())
                    .duration(createdPolicy.getDuration())
                    .likes(createdPolicy.getLikes())
//                    .policyComments(createdPolicy.getPolicyComments())
                    .build();
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
                PolicyBuilder updatedPolicyBuilder = new PolicyBuilder();
                updatedPolicyBuilder.setPolicyId(policyDto.getPolicyId());
                if (!policyDto.getName().isEmpty()){
                    updatedPolicyBuilder.setName(policyDto.getName());
                }
                else
                    updatedPolicyBuilder.setName(policy.get().getName());
                if (!policyDto.getDescription().isEmpty()){
                    updatedPolicyBuilder.setCategory(policy.get().getCategory());
                }
                else
                    updatedPolicyBuilder.setDuration(policy.get().getDuration());
                if (policyDto.getLikes() != 0)
                    updatedPolicyBuilder.setLikes(policyDto.getLikes());
                else
                    updatedPolicyBuilder.setLikes(policy.get().getLikes());
//                updatedPolicyBuilder.setPolicyComments(policy.get().getPolicyComments());

                Policy respond = policyRepository.save(updatedPolicyBuilder.build());
                System.out.println("-----------------------------------");
                System.out.println("Policy is Updated");
                System.out.println("-----------------------------------");
                PolicyDto result = PolicyDto.builder()
                        .policyId(respond.getPolicyId())
                        .name(respond.getName())
                        .description(respond.getDescription())
                        .category(respond.getCategory())
                        .duration(respond.getDuration())
                        .likes(respond.getLikes())
                        .build();
                return result;
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
                PolicyDto policyDto = PolicyDto.builder()
                        .policyId(foundPolicy.get().getPolicyId())
                        .name(foundPolicy.get().getName())
                        .description(foundPolicy.get().getDescription())
                        .category(foundPolicy.get().getCategory())
                        .duration(foundPolicy.get().getDuration())
                        .likes(foundPolicy.get().getLikes())
//                        .policyComments(foundPolicy.get().getPolicyComments())
                        .build();
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
                PolicyDto policyDto = PolicyDto.builder()
                        .policyId(item.getPolicyId())
                        .name(item.getName())
                        .description(item.getDescription())
                        .category(item.getCategory())
                        .duration(item.getDuration())
                        .likes(item.getLikes())
//                        .policyComments(item.getPolicyComments())
                        .build();
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
                PolicyDto policyDto = PolicyDto.builder()
                        .policyId(deletedPolicy.get().getPolicyId())
                        .name(deletedPolicy.get().getName())
                        .description(deletedPolicy.get().getDescription())
                        .category(deletedPolicy.get().getCategory())
                        .duration(deletedPolicy.get().getDuration())
                        .likes(deletedPolicy.get().getLikes())
//                        .policyComments(deletedPolicy.get().getPolicyComments())
                        .build();
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
        Optional<Policy> foundPolicy = policyRepository.findById(policyId);
        if (foundPolicy.isPresent()){
//            foundPolicy.get().getPolicyComments().add(comment);
            Policy updatedPolicy = policyRepository.save(foundPolicy.get());
            return PolicyDto.builder()
                    .name(updatedPolicy.getName())
                    .policyId(updatedPolicy.getPolicyId())
                    .description(updatedPolicy.getDescription())
                    .duration(updatedPolicy.getDuration())
                    .likes(updatedPolicy.getLikes())
                    .category(updatedPolicy.getCategory())
                    .build();
        }
        return null;
    }

//    @Override
//    public List<PolicyDto> findCommentByUserEmail(String userEmail) {
//        Optional<List<Policy>> policies = policyRepository.findByPolicyCommentsUserEmail(userEmail);
//        if (policies.isPresent()){
//            List<PolicyDto> policyDtos = new ArrayList<PolicyDto>();
//            for (Policy p : policies.get()){
//                policyDtos.add(PolicyDto.builder()
//                        .policyId(p.getPolicyId())
//                        .name(p.getName())
//                        .description(p.getDescription())
//                        .duration(p.getDuration())
//                        .build());
//            }
//            return po;
//        }
//        return null;
//    }

//    public PolicyDto addComment(String policyId, Comment putComment) {
//        try{
//            Optional<Policy> foundPolicy = policyRepository.findById(policyId);
//            if (foundPolicy.isPresent()){
//                String url = "http://localhost:5050/user/update/point?email=" + putComment.getUser().getEmail();
//                RestTemplate restTemplate = new RestTemplate();
//                ResponseEntity<UserDto> user = restTemplate.exchange(
//                        url,
//                        HttpMethod.PUT,
//                        null,
//                        UserDto.class
//                );
//                if (user.hasBody()){
//                    Policy user = foundPolicy.get();
//                    User commentUser = new UserBuilder()
//                            .setEmail(user.getBody().getEmail())
//                            .setFirstName(user.getBody().getFirstName())
//                            .setLastName(user.getBody().getLastName())
//                            .setPoints(user.getBody().getPoints())
//                            .build();
//                    Comment newComment = new CommentBuilder()
//                            .setCommentBody(putComment.getCommentBody())
//                            .setComments(putComment.getComments())
//                            .setAnonymous(putComment.isAnonymous())
//                            .setUser(commentUser)
//                            .setCategory(putComment.getCategory())
//                            .build();
//                    user.getPolicyComments().add(newComment);
//                    Policy updatedPolicy = policyRepository.save(user);
//                    System.out.println("-----------------------------------");
//                    System.out.println("Comment is added to Policy");
//                    System.out.println("-----------------------------------");
//                    PolicyDto policyDto = PolicyDto.builder()
//                            .policyId(updatedPolicy.getPolicyId())
//                            .name(updatedPolicy.getName())
//                            .description(updatedPolicy.getDescription())
//                            .category(updatedPolicy.getCategory())
//                            .duration(updatedPolicy.getDuration())
//                            .likes(updatedPolicy.getLikes())
//                            .policyComments(updatedPolicy.getPolicyComments())
//                            .build();
//                    return policyDto;
//                }
//                System.out.println("User is not found in addComment PolicyService");
//                return null;
//            }
//            System.out.println("Policy is not found in addComment PolicyService");
//            return null;
//        } catch (Exception e) {
//            System.out.println("Exception addComment in PolicyService ==> " + e.getMessage());
//            return null;
//        }
//    }

//    @Override
//    public PolicyDto addCommentByChain(String policyId, CommentDto commentDto){
//        Optional<Policy> foundPolicy = policyRepository.findById(policyId);
//        if (foundPolicy.isPresent()){
//            AddCommentHandler findUserHandler = new FindUserHandler(userService);
//            AddCommentHandler addPointHandler = new AddPointHandler(userService);
//            AddCommentHandler saveCommentHandler = new SaveCommentHandler(commentService);
//            findUserHandler.setNextHandler(addPointHandler);
//            addPointHandler.setNextHandler(saveCommentHandler);
//            CommentDto processedComment = findUserHandler.processComment(commentDto);
//            Comment newComment = new CommentBuilder()
//                    .setComments(processedComment.getComments())
//                    .setCommentBody(processedComment.getCommentBody())
//                    .setCommentId(processedComment.getCommentId())
//                    .setAnonymous(processedComment.isAnonymous())
//                    .setCategory(processedComment.getCategory())
//                    .setUser(processedComment.getUser())
//                    .build();
//            foundPolicy.get().getPolicyComments().add(newComment);
//            Policy updatedPolicy = policyRepository.save(foundPolicy.get());
//            return PolicyDto.builder()
//                    .name(updatedPolicy.getName())
//                    .policyId(updatedPolicy.getPolicyId())
//                    .description(updatedPolicy.getDescription())
//                    .duration(updatedPolicy.getDuration())
//                    .likes(updatedPolicy.getLikes())
//                    .category(updatedPolicy.getCategory())
//                    .build();
//        }
//        return null;
//    }

}

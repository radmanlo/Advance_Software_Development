package com.example.feedback.service;

import com.example.feedback.dto.CommentDto;
import com.example.feedback.dto.PolicyDto;
import com.example.feedback.dto.UserDto;
import com.example.feedback.entity.Comment;
import com.example.feedback.entity.builder.CommentBuilder;
import com.example.feedback.service.chainOfResponsibility.AddCommentHandler;
import com.example.feedback.service.chainOfResponsibility.AddPointHandler;
import com.example.feedback.service.chainOfResponsibility.FindPolicyHandler;
import com.example.feedback.service.chainOfResponsibility.FindUserHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FacadeService {

    private final PolicyService policyService;
    private final UserService userService;
    private final CommentService commentService;


    public PolicyDto createPolicy(PolicyDto policyDto){
        return policyService.createPolicy(policyDto);
    }

    public PolicyDto updatePolicy(PolicyDto policyDto){
        return policyService.updatePolicy(policyDto);
    }

    public PolicyDto getPolicyById(String policyId){
        return policyService.getPolicyById(policyId);
    }

    public List<PolicyDto> getAllPolicies(){
        return policyService.getAllPolicies();
    }

    public PolicyDto deletePolicy(String policyId){
        return policyService.deletePolicy(policyId);
    }

//    public PolicyDto addComment(String policyId, Comment comment){
//            AddCommentHandler findUserHandler = new FindUserHandler(userService);
//            AddCommentHandler findPolicyHandler = new FindPolicyHandler(policyService);
//            AddCommentHandler addPointHandler = new AddPointHandler(userService);
//            findUserHandler.setNextHandler(findPolicyHandler);
//            findPolicyHandler.setNextHandler(addPointHandler);
//            Comment processedComment = findUserHandler.processComment(comment);
//            return policyService.addComment(policyId, processedComment);
//    }

    public UserDto createUser(UserDto userDto){
        return userService.createUser(userDto);
    }

    public UserDto updateUser(UserDto userDto){
        return userService.updateUser(userDto);
    }

    public UserDto getUserByEmail(String email){
        return userService.getUserByEmail(email);
    }

    public UserDto deleteUser(String email){
        return userService.deleteUser(email);
    }

    public UserDto updateUserPoint (String email){
        return userService.updateUserPoint(email);
    }

    public CommentDto addComment (CommentDto commentDto){
        AddCommentHandler findUserHandler = new FindUserHandler(userService);
        AddCommentHandler findPolicyHandler = new FindPolicyHandler(policyService);
        AddCommentHandler addPointHandler = new AddPointHandler(userService);
        findUserHandler.setNextHandler(findPolicyHandler);
        findPolicyHandler.setNextHandler(addPointHandler);
        CommentDto processedComment = findUserHandler.processComment(commentDto);
//        System.out.println("CommentDto in Facade => " + processedComment.toString());
        return commentService.createComment(processedComment);
    }

    public CommentDto getCommentById (String commentId){
        return commentService.getById(commentId);
    }

    public List<CommentDto> getUserComments (String userEmail){
        return commentService.getUserComments(userEmail);
    }

    public List<CommentDto> getPolicyComments (String policyId){
        return commentService.getPolicyComments(policyId);
    }

    public CommentDto updateComment (CommentDto commentDto){
        return commentService.updateComment(commentDto);
    }

    public CommentDto deleteComment (String commentId){
        return commentService.deleteComment(commentId);
    }
}

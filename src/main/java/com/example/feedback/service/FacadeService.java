package com.example.feedback.service;

import com.example.feedback.dto.CommentDto;
import com.example.feedback.dto.PolicyDto;
import com.example.feedback.dto.RatingDto;
import com.example.feedback.dto.UserDto;
import com.example.feedback.service.chainOfResponsibility.commentChain.AddCommentHandler;
import com.example.feedback.service.chainOfResponsibility.commentChain.AddPointHandler;
import com.example.feedback.service.chainOfResponsibility.commentChain.FindPolicyHandler;
import com.example.feedback.service.chainOfResponsibility.commentChain.FindUserHandler;
import com.example.feedback.service.chainOfResponsibility.ratingChain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FacadeService {

    private final PolicyService policyService;
    private final UserService userService;
    private final CommentService commentService;
    private final RatingService ratingService;


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
        CommentDto response = commentService.deleteComment(commentId);
        if (response != null) {
            userService.DeleteUserPoint(response.getUserDto().getEmail());
            return response;
        }
        return null;
    }

    public RatingDto createRating (RatingDto ratingDto) {
        AddRatingHandler findUserHandler = new FindUserHandlerR(userService);
        AddRatingHandler findPolicyHandler = new FindPolicyHandlerR(policyService);
        AddRatingHandler findRatingHandler = new FindRatingHandler(ratingService);
        AddRatingHandler addPointingHandler = new AddPointHandlerR(userService);
        findUserHandler.setNextHandler(findPolicyHandler);
        findPolicyHandler.setNextHandler(findRatingHandler);
        findRatingHandler.setNextHandler(addPointingHandler);
        RatingDto processedRating = findUserHandler.processRating(ratingDto);
        if (processedRating != null)
            return ratingService.createRating(processedRating);
        return null;
    }

    public List<RatingDto> getRatingByUserEmail(String userEmail){
        return ratingService.findByUserEmail(userEmail);
    }

    public int[] policyAverageRating(String policyId){
        return ratingService.policyRatingAverage(policyId);
    }

    public RatingDto updateRating(RatingDto ratingDto){
        return ratingService.updateRating(ratingDto);
    }

    public RatingDto deleteRating(String ratingId){
        RatingDto response = ratingService.deleteRating(ratingId);
        if (response != null){
            userService.DeleteUserPoint(response.getUserDto().getEmail());
            return response;
        }
        return null;
    }

}

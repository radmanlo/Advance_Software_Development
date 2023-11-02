package com.example.feedback.service.chainOfResponsibility;

import com.example.feedback.dto.CommentDto;
import com.example.feedback.dto.PolicyDto;
import com.example.feedback.dto.UserDto;
import com.example.feedback.entity.Comment;
import com.example.feedback.entity.Policy;
import com.example.feedback.entity.User;
import com.example.feedback.entity.builder.CommentBuilder;
import com.example.feedback.entity.builder.UserBuilder;
import com.example.feedback.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

//@RequiredArgsConstructor
@Component
public class FindUserHandler implements AddCommentHandler{

    private AddCommentHandler nextHandler;

    private final UserService userService;

    @Autowired
    public FindUserHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void setNextHandler(AddCommentHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public Comment processComment(Comment comment) {
        UserDto foundUser = userService.getUserByEmail(comment.getUser().getEmail());
        if(foundUser != null ){
            if (nextHandler != null) {
                User commentUser = new UserBuilder()
                        .setEmail(foundUser.getEmail())
                        .setFirstName(foundUser.getFirstName())
                        .setLastName(foundUser.getLastName())
                        .setPoints(foundUser.getPoints())
                        .build();
                Comment updatedComment = new CommentBuilder()
                        .setCommentBody(comment.getCommentBody())
                        .setUser(commentUser)
                        .setCategory(comment.getCategory())
                        .setAnonymous(comment.isAnonymous())
                        .build();
                return nextHandler.processComment(updatedComment);
            }
            System.out.println("User not found");
            return null;
        }
        else
            return null;
    }
}

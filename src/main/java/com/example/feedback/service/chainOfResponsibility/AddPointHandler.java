package com.example.feedback.service.chainOfResponsibility;

import com.example.feedback.dto.CommentDto;
import com.example.feedback.dto.UserDto;
import com.example.feedback.entity.Comment;
import com.example.feedback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddPointHandler implements AddCommentHandler{

    private AddCommentHandler nextHandler;

    private final UserService userService;

    @Autowired
    public AddPointHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void setNextHandler(AddCommentHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public Comment processComment(Comment comment) {
        UserDto userDto = userService.updateUserPoint(comment.getUser().getEmail());
        if (userDto != null){
            if (nextHandler != null)
                return nextHandler.processComment(comment);
            return comment;
        }
        return null;
    }
}

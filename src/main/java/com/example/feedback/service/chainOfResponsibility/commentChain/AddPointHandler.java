package com.example.feedback.service.chainOfResponsibility.commentChain;

import com.example.feedback.dto.CommentDto;
import com.example.feedback.dto.UserDto;
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
    public CommentDto processComment(CommentDto commentDto) {
        UserDto userDto = userService.updateUserPoint(commentDto.getUserDto().getEmail());
        if (userDto != null){
            if (nextHandler != null)
                return nextHandler.processComment(commentDto);
            return commentDto;
        }
        return null;
    }
}

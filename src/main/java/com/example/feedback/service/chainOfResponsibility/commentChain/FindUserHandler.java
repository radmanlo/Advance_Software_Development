package com.example.feedback.service.chainOfResponsibility.commentChain;

import com.example.feedback.dto.CommentDto;
import com.example.feedback.dto.UserDto;
import com.example.feedback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    public CommentDto processComment(CommentDto commentDto) {
        UserDto foundUser = userService.getUserByEmail(commentDto.getUserDto().getEmail());
        if(foundUser != null ){
            CommentDto updatedComment = CommentDto.builder()
                    .commentId(commentDto.getCommentId())
                    .policyDto(commentDto.getPolicyDto())
                    .userDto(foundUser)
                    .commentBody(commentDto.getCommentBody())
                    .category(commentDto.getCategory())
                    .anonymous(commentDto.isAnonymous())
                    .build();
            if (nextHandler != null) {
                return nextHandler.processComment(updatedComment);
            }
            else
                return updatedComment;
        }
        System.out.println("User not found");
        return null;
    }
}

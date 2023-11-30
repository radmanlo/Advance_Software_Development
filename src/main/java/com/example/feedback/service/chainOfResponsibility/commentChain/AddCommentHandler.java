package com.example.feedback.service.chainOfResponsibility.commentChain;

import com.example.feedback.dto.CommentDto;


public interface AddCommentHandler {

    void setNextHandler (AddCommentHandler nextHandler);
    CommentDto processComment(CommentDto commentDto);
}

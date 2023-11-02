package com.example.feedback.service.chainOfResponsibility;

import com.example.feedback.dto.CommentDto;
import com.example.feedback.entity.Comment;


public interface AddCommentHandler {

    void setNextHandler (AddCommentHandler nextHandler);
    Comment processComment(Comment commentDto);
}

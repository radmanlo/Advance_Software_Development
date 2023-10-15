package com.example.feedback.service;

import com.example.feedback.dto.CommentDto;
import com.example.feedback.entity.Comment;
import com.example.feedback.repository.CommentRepository;
import com.example.feedback.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImp implements CommentService{

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public CommentDto createComment(CommentDto commentDto) {

        try {
            Comment comment = new Comment();
            comment.setCommentBody(commentDto.getCommentBody());
            comment.setCategory(commentDto.getCategory());
            comment.setAnonymous(commentDto.isAnonymous());
            comment.setUser(commentDto.getUser());
            commentRepository.save(comment);
            return commentDto;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }

    }

    @Override
    public CommentDto addCommentToPolicy(CommentDto commentDto, ObjectId policyId, String email) {
        return null;
    }
}

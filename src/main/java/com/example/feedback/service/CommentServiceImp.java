package com.example.feedback.service;

import com.example.feedback.dto.CommentDto;
import com.example.feedback.entity.Comment;
import com.example.feedback.entity.builder.CommentBuilder;
import com.example.feedback.repository.CommentRepository;
import com.example.feedback.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImp implements CommentService{

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public CommentDto createComment(CommentDto commentDto) {
        try {
            Comment comment = new CommentBuilder()
                    .setCommentBody(commentDto.getCommentBody())
                    .setCategory(commentDto.getCategory())
                    .setAnonymous(commentDto.isAnonymous())
                    .setUser(commentDto.getUser())
                    .build();
            Comment savedComment = commentRepository.save(comment);
            return CommentDto.builder()
                    .commentBody(savedComment.getCommentBody())
                    .anonymous(savedComment.isAnonymous())
                    .user(savedComment.getUser())
                    .category(savedComment.getCategory())
                    .build();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public CommentDto updateComment(String commentId) {
        return null;
    }

    @Override
    public List<CommentDto> getUserComments(String userEmail) {
        return null;
    }

    @Override
    public CommentDto deleteComment(String commentId) {
        return null;
    }

    @Override
    public CommentDto addCommentToComment(String commentId, CommentDto commentDto) {
        return null;
    }

}

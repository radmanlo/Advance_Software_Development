package com.example.feedback.service;

import com.example.feedback.dto.CommentDto;
import com.example.feedback.dto.PolicyDto;
import com.example.feedback.dto.UserDto;
import com.example.feedback.entity.Comment;
import com.example.feedback.entity.Policy;
import com.example.feedback.entity.builder.CommentBuilder;
import com.example.feedback.entity.builder.PolicyBuilder;
import com.example.feedback.entity.builder.UserBuilder;
import com.example.feedback.repository.CommentRepository;
import com.example.feedback.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImp implements CommentService{

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public CommentDto createComment(CommentDto commentDto) {
        Comment newComment = new CommentBuilder()
                .setCommentBody(commentDto.getCommentBody())
                .setAnonymous(commentDto.isAnonymous())
                .setCategory(commentDto.getCategory())
                .setUser(new UserBuilder()
                        .setEmail(commentDto.getUserDto().getEmail())
                        .setFirstName(commentDto.getUserDto().getFirstName())
                        .setLastName(commentDto.getUserDto().getLastName())
                        .build())
                .setPolicy(new PolicyBuilder()
                        .setPolicyId(commentDto.getPolicyDto().getPolicyId())
                        .setName(commentDto.getPolicyDto().getName())
                        .setDuration(commentDto.getPolicyDto().getDuration())
                        .setDescription(commentDto.getPolicyDto().getDescription())
                        .build())
                .build();
        System.out.println("CommentDto inside commentServiceImp => " + newComment.toString());
        Comment savedComment = commentRepository.save(newComment);
        return CommentDto.builder()
                .commentBody(savedComment.getCommentBody())
                .category(savedComment.getCategory())
                .commentId(savedComment.getCommentId())
                .anonymous(savedComment.isAnonymous())
                .userDto(UserDto.builder()
                        .email(savedComment.getUser().getEmail())
                        .firstName(savedComment.getUser().getFirstName())
                        .lastName(savedComment.getUser().getLastName())
                        .build())
                .policyDto(PolicyDto.builder()
                        .policyId(savedComment.getPolicy().getPolicyId())
                        .description(savedComment.getPolicy().getDescription())
                        .name(savedComment.getPolicy().getName())
                        .duration(savedComment.getPolicy().getDuration())
                        .category(savedComment.getPolicy().getCategory())
                        .build())
                .build();
    }

    @Override
    public CommentDto getById(String commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isPresent()){
            return CommentDto.builder()
                    .commentId(comment.get().getCommentId())
                    .commentBody(comment.get().getCommentBody())
                    .anonymous(comment.get().isAnonymous())
                    .category(comment.get().getCategory())
                    .policyDto(PolicyDto.builder()
                            .name(comment.get().getPolicy().getName())
                            .likes(comment.get().getPolicy().getLikes())
                            .policyId(comment.get().getPolicy().getPolicyId())
                            .duration(comment.get().getPolicy().getDuration())
                            .description(comment.get().getPolicy().getDescription())
                            .build())
                    .userDto(UserDto.builder()
                            .email(comment.get().getUser().getEmail())
                            .firstName(comment.get().getUser().getFirstName())
                            .lastName(comment.get().getUser().getLastName())
                            .points(comment.get().getUser().getPoints())
                            .build())
                    .build();
        }
        return null;
    }


    @Override
    public CommentDto updateComment(CommentDto commentDto) {
        Optional<Comment> foundComment = commentRepository.findById(commentDto.getCommentId());
        if(foundComment.isPresent()){
            Comment updatedComment = new CommentBuilder()
                    .setCommentId(foundComment.get().getCommentId())
                    .setCategory(commentDto.getCategory().isEmpty()?
                            foundComment.get().getCategory() : commentDto.getCategory())
                    .setCommentBody(commentDto.getCommentBody().isEmpty()?
                            foundComment.get().getCommentBody() : commentDto.getCommentBody())
                    .setAnonymous(commentDto.isAnonymous())
                    .setCommentId(foundComment.get().getCommentId())
                    .setUser(foundComment.get().getUser())
                    .setPolicy(foundComment.get().getPolicy())
                    .build();
            Comment savedComment = commentRepository.save(updatedComment);
            return CommentDto.builder()
                    .anonymous(savedComment.isAnonymous())
                    .commentBody(savedComment.getCommentBody())
                    .commentId(savedComment.getCommentId())
                    .category(savedComment.getCategory())
                    .userDto(UserDto.builder()
                            .email(savedComment.getUser().getEmail())
                            .firstName(savedComment.getUser().getFirstName())
                            .lastName(savedComment.getUser().getLastName())
                            .points(savedComment.getUser().getPoints())
                            .build())
                    .build();
        }
        return null;
    }

    @Override
    public List<CommentDto> getUserComments(String userEmail) {
        Optional<List<Comment>> comments = commentRepository.findByUserEmail(userEmail);
        if(comments.isPresent()){
            List<CommentDto> commentDtos = new ArrayList<CommentDto>();
            for (Comment c : comments.get()){
                commentDtos.add(CommentDto.builder()
                        .commentBody(c.getCommentBody())
                        .category(c.getCategory())
                        .anonymous(c.isAnonymous())
                        .userDto(UserDto.builder()
                                .email(c.getUser().getEmail())
                                .firstName(c.getUser().getFirstName())
                                .lastName(c.getUser().getLastName())
                                .build())
                        .policyDto(PolicyDto.builder()
                                .description(c.getPolicy().getDescription())
                                .policyId(c.getPolicy().getPolicyId())
                                .category(c.getPolicy().getCategory())
                                .duration(c.getPolicy().getDuration())
                                .name(c.getPolicy().getName())
                                .build())
                        .commentId(c.getCommentId())
                        .build());
            }
            return commentDtos;
        }
        return null;
    }

    @Override
    public List<CommentDto> getPolicyComments(String policyId) {
        Optional<List<Comment>> comments = commentRepository.findByPolicyPolicyId(policyId);
        if (comments.isPresent()){
            List<CommentDto> commentDtos = new ArrayList<CommentDto>();
            for (Comment c : comments.get()){
                commentDtos.add(CommentDto.builder()
                                .commentId(c.getCommentId())
                                .commentBody(c.getCommentBody())
                                .anonymous(c.isAnonymous())
                                .category(c.getCategory())
                                .policyDto(PolicyDto.builder()
                                        .name(c.getPolicy().getName())
                                        .policyId(c.getPolicy().getPolicyId())
                                        .description(c.getPolicy().getDescription())
                                        .duration(c.getPolicy().getDuration())
                                        .category(c.getPolicy().getCategory())
                                        .build())
                                .userDto(UserDto.builder()
                                        .email(c.getUser().getEmail())
                                        .firstName(c.getUser().getFirstName())
                                        .lastName(c.getUser().getLastName())
                                        .build())
                                .build());
            }
            return commentDtos;
        }
        return null;
    }


    @Override
    public CommentDto deleteComment(String commentId) {
        Comment deletedComment = commentRepository.deleteCommentByCommentId(commentId);
        if (deletedComment != null){
            System.out.println("I am here");
            return CommentDto.builder()
                    .userDto(UserDto.builder()
                            .email(deletedComment.getUser().getEmail())
                            .lastName(deletedComment.getUser().getLastName())
                            .firstName(deletedComment.getUser().getFirstName())
                            .build())
                    .policyDto(PolicyDto.builder()
                            .category(deletedComment.getPolicy().getCategory())
                            .policyId(deletedComment.getPolicy().getPolicyId())
                            .duration(deletedComment.getPolicy().getDuration())
                            .name(deletedComment.getPolicy().getName())
                            .likes(deletedComment.getPolicy().getLikes())
                            .build())
                    .commentBody(deletedComment.getCommentBody())
                    .category(deletedComment.getCategory())
                    .anonymous(deletedComment.isAnonymous())
                    .commentId(deletedComment.getCommentId())
                    .build();
        }
        return null;
    }

}

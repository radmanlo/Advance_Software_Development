package com.example.feedback.controller;

import com.example.feedback.dto.CommentDto;
import com.example.feedback.dto.UserDto;
import com.example.feedback.entity.User;
import com.example.feedback.repository.CommentRepository;
import com.example.feedback.service.CommentService;
import com.example.feedback.service.FacadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private FacadeService service;

    @PostMapping("/create")
    public ResponseEntity<CommentDto> createComment (@RequestBody CommentDto commentDto){
        try {
//            System.out.println("CommentDto in cpmmentController => " + commentDto.toString());
            CommentDto newComment = service.addComment(commentDto);
            if (newComment != null)
                return ResponseEntity.status(HttpStatus.CREATED).body(newComment);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }catch (Exception e){
            System.out.println("Exception addComment in CommentController" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/getById")
    public ResponseEntity<CommentDto> getCommentById (@RequestParam String commentId){
        try {
            CommentDto commentDto = service.getCommentById(commentId);
            if (commentDto != null){
                return ResponseEntity.status(HttpStatus.FOUND).body(commentDto);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }catch (Exception e){
            System.out.println("Exception getCommentById in CommentController" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/getByUserEmail")
    public ResponseEntity<List<CommentDto>> getUserComments (@RequestParam String userEmail){
        try {
            List<CommentDto> commentDto = service.getUserComments(userEmail);
            if (commentDto != null)
                return ResponseEntity.status(HttpStatus.FOUND).body(commentDto);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }catch (Exception e){
            System.out.println("Exception getUserComments in CommentController" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/getByPolicyId")
    public ResponseEntity<List<CommentDto>> getPolicyComments (@RequestParam String policyId) {
        try {
            List<CommentDto> commentDtos = service.getPolicyComments(policyId);
            if (commentDtos != null)
                return ResponseEntity.status(HttpStatus.FOUND).body(commentDtos);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }catch (Exception e){
            System.out.println("Exception getPolicyComments in CommentController" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<CommentDto> updateComment (@RequestBody CommentDto commentDto){
        try {
            CommentDto updatedComment = service.updateComment(commentDto);
            if (commentDto != null)
                return ResponseEntity.status(HttpStatus.OK).body(updatedComment);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }catch (Exception e){
            System.out.println("Exception updateComment in CommentController" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<CommentDto> deleteComment (@RequestParam String commentId) {
        try {
            CommentDto commentDto = service.deleteComment(commentId);
            if(commentDto != null)
                return ResponseEntity.status(HttpStatus.OK).body(commentDto);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            System.out.println("Exception deleteComment in CommentController" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

package com.example.feedback.controller;

import com.example.feedback.dto.CommentDto;
import com.example.feedback.dto.UserDto;
import com.example.feedback.entity.User;
import com.example.feedback.repository.CommentRepository;
import com.example.feedback.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<CommentDto> createComment (@RequestBody CommentDto commentDto){

        try {
            String url = "http://localhost:5050/user/findByEmail?email=" + commentDto.getUser().getEmail();
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<UserDto> user = restTemplate.getForEntity(url, UserDto.class);
            if (user.hasBody()){
                System.out.println(user.getBody().get_id().toString());
                CommentDto newComment = commentService.createComment(commentDto);
                return ResponseEntity.status(HttpStatus.CREATED).body(newComment);
            }
            else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

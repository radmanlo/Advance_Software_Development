package com.example.feedback.controller;

import com.example.feedback.dto.PolicyDto;
import com.example.feedback.dto.RatingDto;
import com.example.feedback.service.FacadeService;
import com.example.feedback.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rating")
public class RatingController {
    @Autowired
    private FacadeService service;

    @PostMapping("/create")
    public ResponseEntity<RatingDto> createRating (@RequestBody RatingDto ratingDto){
        try{
            RatingDto rating = service.createRating(ratingDto);
            if (rating != null)
                return ResponseEntity.status(HttpStatus.CREATED).body(rating);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e){
            System.out.println("Exception CreateRating" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/byUserEmail")
    public ResponseEntity<List<RatingDto>> getRatingByUserEmail (@RequestParam String userEmail){
        try {
            List<RatingDto> response = service.getRatingByUserEmail(userEmail);
            if (response != null){
                return ResponseEntity.status(HttpStatus.FOUND).body(response);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/policyAverage")
    public ResponseEntity<int[]> getPolicyAverage (@RequestParam String policyId){
        try {
            int[] response = service.policyAverageRating(policyId);
            if (response != null){
                return ResponseEntity.status(HttpStatus.FOUND).body(response);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<RatingDto> updateRating(@RequestBody RatingDto ratingDto){
        try {
            RatingDto response = service.updateRating(ratingDto);
            if (response != null){
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<RatingDto> deleteRating(@RequestParam String ratingId){
        try {
            RatingDto response = service.deleteRating(ratingId);
            if (response != null) {
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

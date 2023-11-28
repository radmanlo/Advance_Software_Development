package com.example.feedback.service.chainOfResponsibility.ratingChain;


import com.example.feedback.dto.RatingDto;

public interface AddRatingHandler {
    void setNextHandler (AddRatingHandler nextHandler);
    RatingDto processRating(RatingDto ratingDto);
}

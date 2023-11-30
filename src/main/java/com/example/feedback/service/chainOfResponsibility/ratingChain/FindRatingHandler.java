package com.example.feedback.service.chainOfResponsibility.ratingChain;

import com.example.feedback.dto.RatingDto;
import com.example.feedback.service.RatingService;
import org.springframework.stereotype.Component;

@Component
public class FindRatingHandler implements AddRatingHandler{
    private AddRatingHandler nextHandler;
    private final RatingService ratingService;

    public FindRatingHandler(RatingService ratingService) {
        this.ratingService = ratingService;
    }
    @Override
    public void setNextHandler(AddRatingHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public RatingDto processRating(RatingDto ratingDto) {
        RatingDto foundRating = ratingService.findByUserEmailAndPolicyId(
                ratingDto.getUserDto().getEmail(),
                ratingDto.getPolicyDto().getPolicyId());
        if (foundRating == null){
            if (nextHandler != null) {
                return nextHandler.processRating(ratingDto);
            }
            else
                return ratingDto;
        }
        System.out.println("Policy found");
        return null;
    }
}

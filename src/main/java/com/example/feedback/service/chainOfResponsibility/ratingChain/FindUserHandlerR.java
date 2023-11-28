package com.example.feedback.service.chainOfResponsibility.ratingChain;

import com.example.feedback.dto.RatingDto;
import com.example.feedback.dto.UserDto;
import com.example.feedback.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class FindUserHandlerR implements AddRatingHandler{

    private AddRatingHandler nextHandler;
    private final UserService userService;

    public FindUserHandlerR(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void setNextHandler(AddRatingHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public RatingDto processRating(RatingDto ratingDto) {
        UserDto foundUser = userService.getUserByEmail(ratingDto.getUserDto().getEmail());
        if(foundUser != null ){
            RatingDto updatedRating = RatingDto.builder()
                    .ratingId(ratingDto.getRatingId())
                    .userDto(foundUser)
                    .policyDto(ratingDto.getPolicyDto())
                    .satisfaction(ratingDto.getSatisfaction())
                    .coverage(ratingDto.getCoverage())
                    .clarity(ratingDto.getClarity())
                    .build();
            if (nextHandler != null) {
                return nextHandler.processRating(updatedRating);
            }
            else
                return updatedRating;
        }
        System.out.println("User not found");
        return null;
    }
}

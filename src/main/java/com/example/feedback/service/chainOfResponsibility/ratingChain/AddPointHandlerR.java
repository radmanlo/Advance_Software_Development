package com.example.feedback.service.chainOfResponsibility.ratingChain;

import com.example.feedback.dto.RatingDto;
import com.example.feedback.dto.UserDto;
import com.example.feedback.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class AddPointHandlerR implements AddRatingHandler{

    private AddRatingHandler nextHandler;

    private final UserService userService;

    public AddPointHandlerR(UserService userService) {
        this.userService = userService;
    }


    @Override
    public void setNextHandler(AddRatingHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public RatingDto processRating(RatingDto ratingDto) {
        UserDto userDto = userService.updateUserPoint(ratingDto.getUserDto().getEmail());
        if (userDto != null){
            if (nextHandler != null)
                return nextHandler.processRating(ratingDto);
            return ratingDto;
        }
        return null;
    }
}

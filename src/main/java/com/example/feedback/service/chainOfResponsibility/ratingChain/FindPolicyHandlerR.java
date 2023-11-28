package com.example.feedback.service.chainOfResponsibility.ratingChain;

import com.example.feedback.dto.PolicyDto;
import com.example.feedback.dto.RatingDto;
import com.example.feedback.service.PolicyService;
import org.springframework.stereotype.Component;

@Component
public class FindPolicyHandlerR implements AddRatingHandler{

    private AddRatingHandler nextHandler;

    private final PolicyService policyService;

    public FindPolicyHandlerR(PolicyService policyService) {
        this.policyService = policyService;
    }

    @Override
    public void setNextHandler(AddRatingHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public RatingDto processRating(RatingDto ratingDto) {
        PolicyDto policyDto = policyService.getPolicyById(ratingDto.getPolicyDto().getPolicyId());
        if (policyDto != null){
            RatingDto updatedRating = RatingDto.builder()
                    .clarity(ratingDto.getClarity())
                    .coverage(ratingDto.getCoverage())
                    .satisfaction(ratingDto.getSatisfaction())
                    .ratingId(ratingDto.getRatingId())
                    .policyDto(policyDto)
                    .userDto(ratingDto.getUserDto())
                    .build();
            if(nextHandler != null){
                return nextHandler.processRating(updatedRating);
            }
            else
                return updatedRating;
        }
        System.out.println("Policy is not found");
        return null;
    }
}

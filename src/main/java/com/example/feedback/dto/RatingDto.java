package com.example.feedback.dto;

import com.example.feedback.entity.Policy;
import com.example.feedback.entity.User;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class RatingDto {

    private String ratingId;
    private int satisfaction;
    private int clarity;
    private int coverage;
    private UserDto userDto;
    private PolicyDto policyDto;
}

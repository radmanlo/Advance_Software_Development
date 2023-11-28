package com.example.feedback.service;

import com.example.feedback.dto.RatingDto;

import java.util.List;

public interface RatingService {

    /**
     * Create a Rating
     * @param ratingDto
     * @return RatingDto if it was successful
     * otherwise
     * @return null
     */
    public RatingDto createRating (RatingDto ratingDto);

    /**
     * Find User by user email and policyId
     * @param email
     * @param policyId
     * @return RatingDto
     * otherwise
     * @return null
     */
    public RatingDto findByUserEmailAndPolicyId (String email, String policyId);

    /**
     * Find Rating by UserEmail
     * @param userEmail
     * @return RatingDto if finds
     * otherwise
     * @return null
     */
    public List<RatingDto> findByUserEmail (String userEmail);

    /**
     * Find Rating by policyId
     * @param policyId
     * @return Ratin
     */
    public int[] policyRatingAverage (String policyId);

    /**
     * Update a rating
     * @param ratingDto
     * @return RatingDto if it was successful
     * otherwise
     * @return null
     */
    public RatingDto updateRating (RatingDto ratingDto);

    /**
     * Delete a rating
     * @param ratingId
     * @return RatingDto if it was successful
     * otherwise
     * @return null
     */
    public RatingDto deleteRating (String ratingId);
}

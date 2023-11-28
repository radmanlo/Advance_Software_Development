package com.example.feedback.service;

import com.example.feedback.dto.PolicyDto;
import com.example.feedback.dto.RatingDto;
import com.example.feedback.dto.UserDto;
import com.example.feedback.entity.Rating;
import com.example.feedback.entity.builder.PolicyBuilder;
import com.example.feedback.entity.builder.RatingBuilder;
import com.example.feedback.entity.builder.UserBuilder;
import com.example.feedback.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RatingServiceImp implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public RatingDto createRating(RatingDto ratingDto) {
        try{
            Rating newRating = new RatingBuilder()
                    .setClarity(ratingDto.getClarity())
                    .setCoverage(ratingDto.getCoverage())
                    .setUser(new UserBuilder()
                            .setEmail(ratingDto.getUserDto().getEmail())
                            .setFirstName(ratingDto.getUserDto().getFirstName())
                            .setLastName(ratingDto.getUserDto().getLastName())
                            .setPoints(ratingDto.getUserDto().getPoints())
                            .build())
                    .setPolicy(new PolicyBuilder()
                            .setPolicyId(ratingDto.getPolicyDto().getPolicyId())
                            .setName(ratingDto.getPolicyDto().getName())
                            .setCategory(ratingDto.getPolicyDto().getCategory())
                            .setDescription(ratingDto.getPolicyDto().getDescription())
                            .setDuration(ratingDto.getPolicyDto().getDuration())
                            .build())
                    .setSatisfaction(ratingDto.getSatisfaction())
                    .build();
            Rating savedRating = ratingRepository.save(newRating);
            return RatingDto.builder()
                    .userDto(UserDto.builder()
                            .email(savedRating.getUser().getEmail())
                            .firstName(savedRating.getUser().getFirstName())
                            .lastName(savedRating.getUser().getLastName())
                            .points(savedRating.getUser().getPoints())
                            .build())
                    .policyDto(PolicyDto.builder()
                            .policyId(savedRating.getPolicy().getPolicyId())
                            .description(savedRating.getPolicy().getDescription())
                            .category(savedRating.getPolicy().getCategory())
                            .name(savedRating.getPolicy().getName())
                            .duration(savedRating.getPolicy().getDuration())
                            .build())
                    .clarity(savedRating.getClarity())
                    .ratingId(savedRating.getRatingId())
                    .coverage(savedRating.getCoverage())
                    .satisfaction(savedRating.getSatisfaction())
                    .build();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public RatingDto findByUserEmailAndPolicyId(String userEmail, String policyId) {
        Optional<Rating> foundRating =  ratingRepository.findByUserEmailAndPolicy_PolicyId(userEmail, policyId);
        if (foundRating.isPresent()){
            return RatingDto.builder()
                    .userDto(UserDto.builder()
                            .email(foundRating.get().getUser().getEmail())
                            .firstName(foundRating.get().getUser().getFirstName())
                            .lastName(foundRating.get().getUser().getLastName())
                            .points(foundRating.get().getUser().getPoints())
                            .build())
                    .policyDto(PolicyDto.builder()
                            .duration(foundRating.get().getPolicy().getDuration())
                            .policyId(foundRating.get().getPolicy().getPolicyId())
                            .name(foundRating.get().getPolicy().getName())
                            .category(foundRating.get().getPolicy().getCategory())
                            .description(foundRating.get().getPolicy().getDescription())
                            .build())
                    .ratingId(foundRating.get().getRatingId())
                    .satisfaction(foundRating.get().getSatisfaction())
                    .coverage(foundRating.get().getCoverage())
                    .clarity(foundRating.get().getClarity())
                    .build();
        }
        return null;
    }

    @Override
    public List<RatingDto> findByUserEmail(String userEmail) {
        Optional<List<Rating>> foundRating = ratingRepository.findByUserEmail(userEmail);
        if (foundRating.isPresent()){
            List<RatingDto> listRating = new ArrayList<RatingDto>();
            for(Rating rate : foundRating.get()){
                listRating.add(RatingDto.builder()
                                .clarity(rate.getClarity())
                                .coverage(rate.getCoverage())
                                .satisfaction(rate.getSatisfaction())
                                .policyDto(PolicyDto.builder()
                                        .category(rate.getPolicy().getCategory())
                                        .policyId(rate.getPolicy().getPolicyId())
                                        .description(rate.getPolicy().getDescription())
                                        .name(rate.getPolicy().getName())
                                        .duration(rate.getPolicy().getDuration())
                                        .build())
                                .ratingId(rate.getRatingId())
                        .build());
            }
            return listRating;
        }
        return null;
    }

    @Override
    public int[] policyRatingAverage(String policyId) {
        Optional<List<Rating>> foundRatings = ratingRepository.findByPolicy_PolicyId(policyId);
        if (foundRatings.isPresent()){
            int[] res = {0, 0, 0};
            if (foundRatings.get().size() != 0){
                int satisfaction = 0;
                int clarity = 0;
                int coverage = 0;
                for (Rating rate : foundRatings.get()){
                    satisfaction += rate.getSatisfaction();
                    clarity += rate.getClarity();
                    coverage +=rate.getCoverage();
                }
                res[0] = satisfaction/foundRatings.get().size();
                res[1] = clarity/foundRatings.get().size();
                res[2] = coverage/foundRatings.get().size();
            }
            return res;
        }
        return null;
    }

    @Override
    public RatingDto updateRating(RatingDto ratingDto) {
        Optional<Rating> foundRating = ratingRepository.findById(ratingDto.getRatingId());
        if (foundRating.isPresent()){
            Rating newRating = new RatingBuilder()
                    .setRatingId(foundRating.get().getRatingId())
                    .setSatisfaction(ratingDto.getSatisfaction())
                    .setClarity(ratingDto.getClarity())
                    .setCoverage(ratingDto.getCoverage())
                    .setPolicy(new PolicyBuilder()
                            .setPolicyId(foundRating.get().getPolicy().getPolicyId())
                            .setName(foundRating.get().getPolicy().getName())
                            .setDuration(foundRating.get().getPolicy().getDuration())
                            .setDescription(foundRating.get().getPolicy().getDescription())
                            .setCategory(foundRating.get().getPolicy().getCategory())
                            .build())
                    .setUser(new UserBuilder()
                            .setEmail(foundRating.get().getUser().getEmail())
                            .setFirstName(foundRating.get().getUser().getFirstName())
                            .setLastName(foundRating.get().getUser().getLastName())
                            .setPoints(foundRating.get().getUser().getPoints())
                            .build())
                    .build();
            Rating updateRating = ratingRepository.save(newRating);
            return RatingDto.builder()
                    .ratingId(updateRating.getRatingId())
                    .satisfaction(updateRating.getSatisfaction())
                    .clarity(updateRating.getClarity())
                    .coverage(updateRating.getCoverage())
                    .policyDto(PolicyDto.builder()
                            .policyId(updateRating.getPolicy().getPolicyId())
                            .name(updateRating.getPolicy().getName())
                            .category(updateRating.getPolicy().getCategory())
                            .description(updateRating.getPolicy().getDescription())
                            .duration(updateRating.getPolicy().getDuration())
                            .build())
                    .userDto(UserDto.builder()
                            .email(updateRating.getUser().getEmail())
                            .firstName(updateRating.getUser().getFirstName())
                            .lastName(updateRating.getUser().getLastName())
                            .points(updateRating.getUser().getPoints())
                            .build())
                    .build();
        }
        return null;
    }

    @Override
    public RatingDto deleteRating(String ratingId) {
        Optional<Rating> deletedRating = ratingRepository.deleteByRatingId(ratingId);
        if (deletedRating.isPresent()){
            return RatingDto.builder()
                    .ratingId(deletedRating.get().getRatingId())
                    .satisfaction(deletedRating.get().getSatisfaction())
                    .clarity(deletedRating.get().getClarity())
                    .coverage(deletedRating.get().getCoverage())
                    .userDto(UserDto.builder()
                            .email(deletedRating.get().getUser().getEmail())
                            .firstName(deletedRating.get().getUser().getFirstName())
                            .lastName(deletedRating.get().getUser().getLastName())
                            .points(deletedRating.get().getUser().getPoints())
                            .build())
                    .policyDto(PolicyDto.builder()
                            .policyId(deletedRating.get().getPolicy().getPolicyId())
                            .name(deletedRating.get().getPolicy().getName())
                            .category(deletedRating.get().getPolicy().getCategory())
                            .description(deletedRating.get().getPolicy().getDescription())
                            .duration(deletedRating.get().getPolicy().getDuration())
                            .build())
                    .build();
        }
        return null;
    }
}

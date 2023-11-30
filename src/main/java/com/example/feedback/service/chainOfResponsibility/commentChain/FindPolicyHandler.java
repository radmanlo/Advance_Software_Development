package com.example.feedback.service.chainOfResponsibility.commentChain;

import com.example.feedback.dto.CommentDto;
import com.example.feedback.dto.PolicyDto;
import com.example.feedback.service.PolicyService;
import org.springframework.stereotype.Component;

@Component
public class FindPolicyHandler implements AddCommentHandler {

    private AddCommentHandler nextHandler;

    private final PolicyService policyService;

    public FindPolicyHandler (PolicyService policyService){
        this.policyService = policyService;
    }
    @Override
    public void setNextHandler(AddCommentHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public CommentDto processComment(CommentDto commentDto) {
        PolicyDto policyDto = policyService.getPolicyById(commentDto.getPolicyDto().getPolicyId());
        if (policyDto != null){
            CommentDto updatedComment = CommentDto.builder()
                    .commentId(commentDto.getCommentId())
                    .commentBody(commentDto.getCommentBody())
                    .policyDto(policyDto)
                    .userDto(commentDto.getUserDto())
                    .anonymous(commentDto.isAnonymous())
                    .category(commentDto.getCategory())
                    .build();
            if(nextHandler != null){
                return nextHandler.processComment(updatedComment);
            }
            else
                return updatedComment;
        }
        System.out.println("Policy is not found");
        return null;
    }
}

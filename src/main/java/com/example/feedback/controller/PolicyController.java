package com.example.feedback.controller;

import com.example.feedback.dto.PolicyDto;
import com.example.feedback.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/policy")
public class PolicyController {

    @Autowired
    private PolicyService policyService;

    @PostMapping("/create")
    public ResponseEntity<PolicyDto> createPolicy (@RequestBody PolicyDto policyDto){
        try{
            PolicyDto newPolicy = policyService.createPolicy(policyDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(newPolicy);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

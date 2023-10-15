package com.example.feedback.controller;

import com.example.feedback.dto.PolicyDto;
import com.example.feedback.entity.Comment;
import com.example.feedback.entity.Policy;
import com.example.feedback.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            System.out.println("Exception getPolicyById in PolicyController" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/update")
    public  ResponseEntity<PolicyDto> updatePolicy (@RequestBody PolicyDto policyDto){
        try{
            PolicyDto updatePolicy = policyService.updatePolicy(policyDto);
            if (updatePolicy != null){
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatePolicy);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e){
            System.out.println("Exception updatePolicy in PolicyController" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/getById")
    public ResponseEntity<PolicyDto> getPolicyById(@RequestParam String policyId){
        try {
            PolicyDto policyDto = policyService.getPolicyById(policyId);
            if (policyDto != null)
                return ResponseEntity.status(HttpStatus.FOUND).body(policyDto);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e){
            System.out.println("Exception getPolicyById in PolicyController" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/getAll")
    public  ResponseEntity<List<PolicyDto>> getAllPolicies(){
        try {
            List<PolicyDto> listPolicy = policyService.getAllPolicies();
            return ResponseEntity.status(HttpStatus.FOUND).body(listPolicy);
        } catch (Exception e){
            System.out.println("Exception getAllPolicies in PolicyController" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<PolicyDto> deletePolicyById(@RequestParam String policyId){
        try {
            PolicyDto policyDto = policyService.deletePolicy(policyId);
            if (policyDto != null)
                return ResponseEntity.status(HttpStatus.OK).body(policyDto);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e){
            System.out.println("Exception deletePolicyById in PolicyController" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/addComment")
    public ResponseEntity<PolicyDto> addComment (@RequestParam String policyId,
                                                 @RequestBody Comment comment){
        try{
            PolicyDto updatePolicy = policyService.addComment(policyId, comment);
            if (updatePolicy != null){
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatePolicy);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }
}

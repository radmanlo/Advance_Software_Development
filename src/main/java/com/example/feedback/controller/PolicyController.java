package com.example.feedback.controller;

import com.example.feedback.dto.CommentDto;
import com.example.feedback.dto.PolicyDto;
import com.example.feedback.entity.Comment;
import com.example.feedback.entity.Policy;
import com.example.feedback.entity.User;
import com.example.feedback.service.FacadeService;
import com.example.feedback.service.PolicyService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/policy")
public class PolicyController {

    @Autowired
    private FacadeService service;

    @PostMapping("/create")
    public ResponseEntity<PolicyDto> createPolicy (@RequestBody PolicyDto policyDto){
        try{
            PolicyDto newPolicy = service.createPolicy(policyDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(newPolicy);
        } catch (Exception e){
            System.out.println("Exception getPolicyById in PolicyController" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/update")
    public  ResponseEntity<PolicyDto> updatePolicy (@RequestBody PolicyDto policyDto){
        try{
            PolicyDto updatePolicy = service.updatePolicy(policyDto);
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
            PolicyDto policyDto = service.getPolicyById(policyId);
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
            List<PolicyDto> listPolicy = service.getAllPolicies();
            return ResponseEntity.status(HttpStatus.FOUND).body(listPolicy);
        } catch (Exception e){
            System.out.println("Exception getAllPolicies in PolicyController" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<PolicyDto> deletePolicyById(@RequestParam String policyId){
        try {
            PolicyDto policyDto = service.deletePolicy(policyId);
            if (policyDto != null)
                return ResponseEntity.status(HttpStatus.OK).body(policyDto);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e){
            System.out.println("Exception deletePolicyById in PolicyController" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

//    @GetMapping("like")
//    public ResponseEntity<Boolean> likePolicy(@RequestParam String policyId){
//        try {
//            boolean response = service.likePolicy(policyId);
//            if (response)
//                return ResponseEntity.status(HttpStatus.OK).body(response);
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//        } catch (Exception e){
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
//        }
//    }

//    @PostMapping("/addComment")
//    public ResponseEntity<PolicyDto> addComment (@RequestBody CommentDto commentDto){
//        try{
//            PolicyDto updatePolicy = service.addComment(commentDto);
//            if (updatePolicy != null){
//                return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatePolicy);
//            }
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        } catch (Exception e){
//            System.out.println("Exception addComment in PolicyController" + e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//
//    }

//    @PostMapping("/addByChain")
//    public ResponseEntity<PolicyDto> addCommentByChain(@RequestParam String policyId,
//                                                       @RequestBody CommentDto commentDto){
//        try{
//            PolicyDto policyDto = policyService.addCommentByChain(policyId, commentDto);
//            if (policyDto != null){
//                return ResponseEntity.status(HttpStatus.ACCEPTED).body(policyDto);
//            }
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }catch (Exception e){
//            System.out.println("Exception in addCommentByChain" + e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }
}

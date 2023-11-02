package com.example.feedback.policy;

import com.example.feedback.dto.PolicyDto;
import org.junit.jupiter.api.*;
import com.example.feedback.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class PolicyServiceTest {

    @Autowired
    PolicyService policyService;

    private static String policyId;

    @Test
    @DisplayName("Create Policy Test")
    @Order(1)
    public void createPolicyTest(){

        // Create a Dummy policy
//        PolicyDto policyTest = new PolicyDto();
//        policyTest.setName("Test Name");
//        policyTest.setDescription("Test Description");
//        policyTest.setCategory("Test Category");
//        policyTest.setDuration("Test Duration");
//        policyTest.setLikes(0);
//
//        // Retrieve policy from createPolicy function
//        PolicyDto retrievedPolicy = policyService.createPolicy(policyTest);
//
//        // test different attribute of Policy
//        assertNotNull(retrievedPolicy, "Policy Object is null");
//        assertNotNull(retrievedPolicy.getPolicyId(), "Policy Id is null");
//        assertNotNull(retrievedPolicy.getName(), "Policy Name is null");
//        assertNotNull(retrievedPolicy.getDescription(), "Policy Description is null");
//        assertNotNull(retrievedPolicy.getCategory(), "Policy Category is null");
//        assertNotNull(retrievedPolicy.getDuration(), "Policy Duration is null");
//
//        // store the policyId for further tests
//        policyId = retrievedPolicy.getPolicyId();

    }

    @Test
    @DisplayName("Update Policy Test")
    @Order(2)
    public void updatePolicyTest(){

        // Update the Dummy policy
//        PolicyDto policyTest = new PolicyDto();
//        policyTest.setPolicyId(policyId);
//        policyTest.setName("Updated Test Name");
//        policyTest.setDescription("Updated Test Description");
//        policyTest.setCategory("Updated Category");
//        policyTest.setDuration("Updated Test Duration");
//
//        // Retrieve policy from updatePolicy function
//        PolicyDto retrievedPolicy = policyService.updatePolicy(policyTest);
//
//        // Test Update Policy
//        assertNotNull(retrievedPolicy, "Policy is null");
//        assertEquals(policyTest.getName(), retrievedPolicy.getName(), "Name is not updated");
//        assertEquals(policyTest.getDescription(), retrievedPolicy.getDescription(), "Description is not updated");
//        assertEquals(policyTest.getCategory(), retrievedPolicy.getCategory(), "Category is not updated");
//        assertEquals(policyTest.getDuration(), retrievedPolicy.getDuration(), "Duration is not Updated");
    }

    @Test
    @DisplayName ("Get Policy By Id")
    @Order(3)
    public void getPolicy(){

        // Retrieve Policy from getPolicyById
        PolicyDto policyDto = policyService.getPolicyById(policyId);

        // Test it is not null
        assertNotNull(policyDto, "Policy is null");

    }

    @Test
    @DisplayName("Get All Policies")
    @Order(3)
    public void getAllPolicies(){

        // Retrieve all Policy from getAllPolicy
        List<PolicyDto> listPolicy = policyService.getAllPolicies();

        // Test
        assertNotNull(listPolicy, "List policy is null");
        assertNotEquals(0, listPolicy.size(), "There is not any policy in database");
    }

    @Test
    @DisplayName("Delete Policy")
    @Order(4)
    public void deletePolicy (){

//        PolicyDto policyTest = new PolicyDto();
//        policyTest.setPolicyId(policyId);
//        policyTest.setName("Updated Test Name");
//        policyTest.setDescription("Updated Test Description");
//        policyTest.setCategory("Updated Category");
//        policyTest.setDuration("Updated Test Duration");
//
//        // Retrieve deleted Policy
//        PolicyDto deletedPolicy = policyService.deletePolicy(policyId);
//
//        // Test
//        assertNotNull(deletedPolicy, "Deleted policy is null");
    }
}

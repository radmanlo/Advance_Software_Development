package com.example.feedback.controller;

import com.example.feedback.dto.UserDto;
import com.example.feedback.entity.User;
import com.example.feedback.service.UserService;
import com.example.feedback.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser (@RequestBody UserDto userDto){
        try{
            UserDto newUSer = userService.createUser(userDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUSer);
        } catch (Exception e){
            System.out.println("Exception createUser in UserController ==> " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<UserDto> updateUser (@RequestBody UserDto userDto){
        try{
            UserDto updatedUser = userService.updateUser(userDto);
            if (updatedUser != null){
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedUser);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e){
            System.out.println("Exception updatedUser in UserController" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/findByEmail")
    public ResponseEntity<UserDto> findByEmail (@RequestParam String email){
        try {
            UserDto userDto = userService.getUserByEmail(email);
            if (userDto != null)
                return ResponseEntity.status(HttpStatus.FOUND).body(userDto);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            System.out.println("findByEmail in UserController ==> " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<UserDto> deleteUser (String email){
        try{
            UserDto deletedUser = userService.deleteUser(email);
            if (deletedUser != null){
                return ResponseEntity.status(HttpStatus.OK).body(deletedUser);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e){
            System.out.println("Exception updatedUser in UserController" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/update/point")
    public ResponseEntity<UserDto> updateUserPoint (@RequestParam String email){
        try{
            UserDto updatedUser = userService.updateUserPoint(email);
            if (updatedUser != null){
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedUser);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e){
            System.out.println("Exception updateUser in UserController ==> " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}

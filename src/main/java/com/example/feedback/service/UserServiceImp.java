package com.example.feedback.service;

import com.example.feedback.dto.UserDto;
import com.example.feedback.entity.User;
import com.example.feedback.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {

        try{
            User user = new User();
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setPoints(userDto.getPoints());
            user.setEmail(userDto.getEmail());
            //user.setUserComments(userDto.getUserComments());
            userRepository.save(user);
            return userDto;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public UserDto findUserByEmail(String email) {
        try{
            Optional<User> foundUser = userRepository.findUserByEmail(email);
            if (foundUser.isPresent()){
                UserDto foundUserDto = new UserDto();
                foundUserDto.setFirstName(foundUser.get().getFirstName());
                foundUserDto.setLastName(foundUser.get().getLastName());
                foundUserDto.setPoints(foundUser.get().getPoints());
                foundUserDto.setEmail(foundUser.get().getEmail());
                //foundUserDto.setUserId(foundUser.get().getUserId());
                return foundUserDto;
            }
            return null;
        } catch (Exception e){
            System.out.println( "findUserByEmail in UserServiceImp ==> " + e.getMessage());
            return null;
        }
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        try{
            User user = new User();
            //user.setUserId(userDto.getUserId());
            user.setFirstName(userDto.getFirstName());
            user.setLastName(user.getLastName());
            user.setEmail(userDto.getEmail());
            user.setPoints(user.getPoints());
            userRepository.save(user);
            return userDto;
        } catch (Exception e){
            System.out.println("Exception UpdateUser in UserServiceImp ==> " + e.getMessage());
            return null;
        }
    }
}

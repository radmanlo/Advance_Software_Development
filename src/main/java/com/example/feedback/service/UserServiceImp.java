package com.example.feedback.service;

import com.example.feedback.dto.UserDto;
import com.example.feedback.entity.User;
import com.example.feedback.repository.UserRepository;
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
            user.setEmail(userDto.getEmail());
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setPoints(userDto.getPoints());
            User newUser = userRepository.save(user);
            UserDto newUserDto = new UserDto(newUser.getEmail(), newUser.getFirstName(),
                    newUser.getLastName(), newUser.getPoints());
            System.out.println("-----------------------------------");
            System.out.println("User is created");
            System.out.println("-----------------------------------");
            return newUserDto;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        try{
            Optional<User> foundUser = userRepository.findById(userDto.getEmail());
            if (foundUser.isPresent()){
                User updateUser = new User();
                if (userDto.getFirstName() != null)
                    updateUser.setFirstName(userDto.getFirstName());
                else
                    updateUser.setFirstName(foundUser.get().getFirstName());
                if (userDto.getLastName() != null)
                    updateUser.setLastName(userDto.getLastName());
                else
                    updateUser.setLastName(foundUser.get().getLastName());
                if (userDto.getPoints() != 0)
                    updateUser.setPoints(userDto.getPoints());
                else
                    updateUser.setPoints(foundUser.get().getPoints());
                User user = userRepository.save(updateUser);
                UserDto updatedUserDto = new UserDto(
                        userDto.getEmail(),
                        updateUser.getFirstName(),
                        userDto.getLastName(),
                        userDto.getPoints());
                return updatedUserDto;
            }
            return null;
        } catch (Exception e){
            System.out.println("Exception updateUser in UserService" + e.getMessage());
            return null;
        }
    }

    @Override
    public UserDto getUserByEmail(String email) {
        try{
            Optional<User> foundUser = userRepository.findById(email);
            if (foundUser.isPresent()){
                UserDto foundUserDto = new UserDto();
                foundUserDto.setEmail(foundUser.get().getEmail());
                foundUserDto.setFirstName(foundUser.get().getFirstName());
                foundUserDto.setLastName(foundUser.get().getLastName());
                foundUserDto.setPoints(foundUser.get().getPoints());
                return foundUserDto;
            }
            System.out.println("updateUser in UserServiceImp ==> " + "user with this email not found");
            return null;
        } catch (Exception e){
            System.out.println( "Exception findUserByEmail in UserServiceImp ==> " + e.getMessage());
            return null;
        }
    }

    @Override
    public UserDto deleteUser(String email) {
        try{
            Optional<User> deletedUser = userRepository.deleteByEmail(email);
            if (deletedUser.isPresent()){
                UserDto userDto = new UserDto(
                        deletedUser.get().getEmail(),
                        deletedUser.get().getFirstName(),
                        deletedUser.get().getLastName(),
                        deletedUser.get().getPoints());
                return userDto;
            }
            return null;
        } catch (Exception e){
            System.out.println("Exception deleteUser in UserServiceImp ==> " + e.getMessage());
            return null;
        }
    }

    @Override
    public UserDto updateUserPoint (String email) {
        try{
            Optional<User> foundUser = userRepository.findById(email);
            if (foundUser.isPresent()){
                foundUser.get().setPoints(foundUser.get().getPoints() + 1);
                UserDto updatedUser = new UserDto();
                updatedUser.setEmail(foundUser.get().getEmail());
                updatedUser.setFirstName(foundUser.get().getFirstName());
                updatedUser.setLastName(foundUser.get().getLastName());
                updatedUser.setPoints(foundUser.get().getPoints());
                userRepository.save(foundUser.get());
                System.out.println("-----------------------------------");
                System.out.println("User point is updated");
                System.out.println("-----------------------------------");
                return updatedUser;
            }
            System.out.println("updateUser in UserServiceImp ==> " + "user with this email not found");
            return null;
        } catch (Exception e){
            System.out.println("Exception UpdateUser in UserServiceImp ==> " + e.getMessage());
            return null;
        }
    }
}

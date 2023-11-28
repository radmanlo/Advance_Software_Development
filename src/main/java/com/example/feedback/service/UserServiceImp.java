package com.example.feedback.service;

import com.example.feedback.dto.UserDto;
import com.example.feedback.entity.User;
import com.example.feedback.entity.builder.UserBuilder;
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
            User user = new UserBuilder()
                    .setEmail(userDto.getEmail())
                    .setFirstName(userDto.getFirstName())
                    .setLastName(userDto.getLastName())
                    .setPoints(userDto.getPoints())
                    .build();
            User newUser = userRepository.save(user);
            UserDto newUserDto = UserDto.builder()
                    .email(newUser.getEmail())
                    .firstName(newUser.getFirstName())
                    .lastName(newUser.getLastName())
                    .points(newUser.getPoints())
                    .build();
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
                UserBuilder builder = new UserBuilder();
                if (userDto.getFirstName() != null)
                    builder.setFirstName(userDto.getFirstName());
                else
                    builder.setFirstName(foundUser.get().getFirstName());
                if (userDto.getLastName() != null)
                    builder.setLastName(userDto.getLastName());
                else
                    builder.setLastName(foundUser.get().getLastName());
                if (userDto.getPoints() != 0)
                    builder.setPoints(userDto.getPoints());
                else
                    builder.setPoints(foundUser.get().getPoints());
                User updateUser = builder.build();
                User user = userRepository.save(updateUser);
                UserDto updatedUserDto = UserDto.builder()
                        .email(user.getEmail())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .points(user.getPoints())
                        .build();
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
            if (foundUser.isPresent())
                return UserDto.builder()
                        .email(foundUser.get().getEmail())
                        .firstName(foundUser.get().getFirstName())
                        .lastName(foundUser.get().getLastName())
                        .points(foundUser.get().getPoints())
                        .build();
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
                UserDto userDto = UserDto.builder()
                        .email(deletedUser.get().getEmail())
                        .firstName(deletedUser.get().getFirstName())
                        .lastName(deletedUser.get().getLastName())
                        .points(deletedUser.get().getPoints())
                        .build();
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
                UserBuilder updatedBuilderUser = new UserBuilder()
                        .setEmail(foundUser.get().getEmail())
                        .setFirstName(foundUser.get().getFirstName())
                        .setLastName(foundUser.get().getLastName())
                        .setPoints(foundUser.get().getPoints() + 1);
                User updatedUser = userRepository.save(updatedBuilderUser.build());
                UserDto updatedUserDto = UserDto.builder()
                        .email(updatedUser.getEmail())
                        .firstName(updatedUser.getFirstName())
                        .lastName(updatedUser.getLastName())
                        .points(updatedUser.getPoints())
                        .build();
                System.out.println("-----------------------------------");
                System.out.println("User point is updated");
                System.out.println("-----------------------------------");
                return updatedUserDto;
            }
            System.out.println("updateUser in UserServiceImp ==> " + "user with this email not found");
            return null;
        } catch (Exception e){
            System.out.println("Exception UpdateUser in UserServiceImp ==> " + e.getMessage());
            return null;
        }
    }

    @Override
    public UserDto DeleteUserPoint(String email) {
        try{
            Optional<User> foundUser = userRepository.findById(email);
            if (foundUser.isPresent()){
                UserBuilder updatedBuilderUser = new UserBuilder()
                        .setEmail(foundUser.get().getEmail())
                        .setFirstName(foundUser.get().getFirstName())
                        .setLastName(foundUser.get().getLastName())
                        .setPoints(foundUser.get().getPoints() - 1);
                User updatedUser = userRepository.save(updatedBuilderUser.build());
                UserDto updatedUserDto = UserDto.builder()
                        .email(updatedUser.getEmail())
                        .firstName(updatedUser.getFirstName())
                        .lastName(updatedUser.getLastName())
                        .points(updatedUser.getPoints())
                        .build();
                System.out.println("-----------------------------------");
                System.out.println("User point is updated");
                System.out.println("-----------------------------------");
                return updatedUserDto;
            }
            System.out.println("updateUser in UserServiceImp ==> " + "user with this email not found");
            return null;
        } catch (Exception e){
            System.out.println("Exception UpdateUser in UserServiceImp ==> " + e.getMessage());
            return null;
        }
    }
}

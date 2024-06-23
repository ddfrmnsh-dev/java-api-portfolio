package com.example.api.portfolio.controller;

import com.example.api.portfolio.dto.ApiResponse;
import com.example.api.portfolio.dto.MetaResponse;
import com.example.api.portfolio.dto.UserDto;
import com.example.api.portfolio.model.User;
import com.example.api.portfolio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ApiResponse<List<UserDto>> getAllUsers(){
        List<User> users = userService.getAllUsers();

        List<UserDto> getAllListUser = users.stream()
                .map(user -> new UserDto(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getIsActive(),
                        user.getCreatedAt(),
                        user.getUpdatedAt()
                ))
                .collect(Collectors.toList());
        MetaResponse meta = new MetaResponse("Users fetched successfully", HttpStatus.OK.value(), "success");
        return new ApiResponse<>(meta, getAllListUser);
    }

    @GetMapping("/{id}")
    public ApiResponse<UserDto> getUserById(@PathVariable Long id){
        User users = userService.getUserById(id);
        UserDto getByIdUserDto = new UserDto(users.getId(), users.getName(), users.getEmail(), users.getIsActive(), users.getCreatedAt(),users.getUpdatedAt());
        MetaResponse meta = new MetaResponse("Users with id " + users.getId(), HttpStatus.OK.value(), "success");
        return new ApiResponse<>(meta, getByIdUserDto);
    }
    @PostMapping
    public ApiResponse<UserDto> createUser(@RequestBody User user) {
        User users = userService.createUser(user);
        UserDto createdUserDto = new UserDto(users.getId(), users.getName(), users.getEmail(), users.getIsActive(), users.getCreatedAt(),users.getUpdatedAt());
        MetaResponse meta = new MetaResponse("Success create user : " + users.getName(), HttpStatus.OK.value(), "success");
        return new ApiResponse<>(meta, createdUserDto);
    }

    @PutMapping("/{id}")
    public ApiResponse<UserDto> updateUser(@PathVariable Long id, @RequestBody User user) {
        User users = userService.updateUser(id, user);
        UserDto updatedUserDto = new UserDto(users.getId(), users.getName(), users.getEmail(), users.getIsActive(), users.getCreatedAt(),users.getUpdatedAt());
        MetaResponse meta = new MetaResponse("Success update user : " + users.getName(), HttpStatus.OK.value(), "success");
        return new ApiResponse<>(meta, updatedUserDto);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<User> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        MetaResponse meta = new MetaResponse("Success delete user : " + id, HttpStatus.OK.value(), "success");
        return new ApiResponse<>(meta, null);
    }
}

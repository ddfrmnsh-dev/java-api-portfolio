package com.example.api.portfolio.controller;

import com.example.api.portfolio.dto.ApiResponse;
import com.example.api.portfolio.dto.MetaResponse;
import com.example.api.portfolio.dto.UserDto;
import com.example.api.portfolio.model.User;
import com.example.api.portfolio.service.UserService;
import com.example.api.portfolio.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ApiResponse<List<?>> getAllUsers(){
        try {
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
//        ArrayList<UserDto> getAllListUser = new ArrayList<>();
//        for (User user : users) {
//            UserDto userDto = new UserDto(
//                    user.getId(),
//                    user.getName(),
//                    user.getEmail(),        // Mendapatkan email dari User dan menambahkannya ke UserDto
//                    user.getIsActive(),     // Mendapatkan status aktif dari User dan menambahkannya ke UserDto
//                    user.getCreatedAt(),    // Mendapatkan tanggal pembuatan dari User dan menambahkannya ke UserDto
//                    user.getUpdatedAt()
//            );
//            getAllListUser.add(userDto);
//        }
            MetaResponse meta = new MetaResponse("Users fetched successfully", HttpStatus.OK.value(), "success");
            return new ApiResponse<>(meta, getAllListUser);
        }catch (Exception e){
            logger.info("Creating user with name: {}", e.getMessage());
            e.printStackTrace();

            MetaResponse meta = new MetaResponse("Failed to fetch users", HttpStatus.INTERNAL_SERVER_ERROR.value(), "error");
            return new ApiResponse<>(meta, Collections.emptyList());
        }
    }

    @GetMapping("/user/{id}")
    public ApiResponse<UserDto> getUserById(@PathVariable Long id){
        User users = userService.getUserById(id);
        UserDto getByIdUserDto = new UserDto(users.getId(), users.getName(), users.getEmail(), users.getIsActive(), users.getCreatedAt(),users.getUpdatedAt());
        MetaResponse meta = new MetaResponse("Users with id " + users.getId(), HttpStatus.OK.value(), "success");
        return new ApiResponse<>(meta, getByIdUserDto);
    }
    @PostMapping("/users")
    public ApiResponse<UserDto> createUser(@RequestBody User user) {
        User users = userService.createUser(user);
        UserDto createdUserDto = new UserDto(users.getId(), users.getName(), users.getEmail(), users.getIsActive(), users.getCreatedAt(),users.getUpdatedAt());
        MetaResponse meta = new MetaResponse("Success create user : " + users.getName(), HttpStatus.OK.value(), "success");
        return new ApiResponse<>(meta, createdUserDto);
    }

    @PutMapping("/user/{id}")
    public ApiResponse<UserDto> updateUser(@PathVariable Long id, @RequestBody User user) {
        User users = userService.updateUser(id, user);
        UserDto updatedUserDto = new UserDto(users.getId(), users.getName(), users.getEmail(), users.getIsActive(), users.getCreatedAt(),users.getUpdatedAt());
        MetaResponse meta = new MetaResponse("Success update user : " + users.getName(), HttpStatus.OK.value(), "success");
        return new ApiResponse<>(meta, updatedUserDto);
    }

    @DeleteMapping("/user/{id}")
    public ApiResponse<User> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        MetaResponse meta = new MetaResponse("Success delete user : " + id, HttpStatus.OK.value(), "success");
        return new ApiResponse<>(meta, null);
    }

    @GetMapping("/profile")
    public ApiResponse<?> getUserProfile() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User currentUser = (User) authentication.getPrincipal();

            logger.info("ada data name", currentUser);
            MetaResponse meta = new MetaResponse("Success delete user : " + currentUser, HttpStatus.OK.value(), "success");
            return new ApiResponse<>(meta, null);
        } catch(Exception e){
            MetaResponse meta = new MetaResponse(e.getMessage(), HttpStatus.OK.value(), "failed");
            return new ApiResponse<>(meta, null);
        }

    }
}

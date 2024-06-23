package com.example.api.portfolio.service;

import com.example.api.portfolio.dto.UserDto;
import com.example.api.portfolio.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long id);
    User createUser (User userDto);
    User updateUser (Long id, User user);
    void deleteUser(Long id);
}

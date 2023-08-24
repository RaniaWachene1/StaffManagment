package com.example.demo.service;

import com.example.demo.entity.User;

public interface UserService {
    User createUser(User user);
    User updateUser(User user);
    void deleteUser(String userId);
    User getUserById(String userId);
    // Additional methods for user management...
}

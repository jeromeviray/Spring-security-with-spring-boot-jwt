package com.project.springbootjwt.service;

import com.project.springbootjwt.model.User;

public interface UserService {
    void save(User user);
    void changePassword(int id, String password);
    boolean comparePassword(int id, String currentPassword);
    User findByUsername(String username);
    User findById(int id);
}

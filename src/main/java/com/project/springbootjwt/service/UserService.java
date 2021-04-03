package com.project.springbootjwt.service;

import com.project.springbootjwt.jwtUtils.JwtResponse;
import com.project.springbootjwt.model.User;
import com.project.springbootjwt.model.UserInformation;

import java.util.List;

public interface UserService {
    void save(User user);
    void changePassword(int id, String password);
    boolean comparePassword(int id, String currentPassword);
    User findByUsername(String username);
    User findById(int id);
//    UserInformation findByIdAndUsername(int id, String username);
    JwtResponse login(String username, String password);
    List<UserInformation> findAllUser();
    UserInformation findUser();
}

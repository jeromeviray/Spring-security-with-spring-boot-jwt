package com.project.springbootjwt.service.impl;

import com.project.springbootjwt.model.Role;
import com.project.springbootjwt.model.User;
import com.project.springbootjwt.repository.RoleRepository;
import com.project.springbootjwt.repository.UserRepository;
import com.project.springbootjwt.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    Logger logger = LoggerFactory.getLogger(UserService.class);


    @Override
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role role = roleRepository.findByRole("USER");
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);

        userRepository.save(user);
        logger.info("Successfully saved");
    }

    @Override
    public void changePassword(int id, String password) {
        User user = findById(id);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Override
    public boolean comparePassword(int id, String currentPassword) {
        logger.info("{}", userRepository.findById(id));
        return passwordEncoder.matches(currentPassword, userRepository.findById(id).getPassword());
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id);
    }
}

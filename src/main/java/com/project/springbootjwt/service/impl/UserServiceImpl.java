package com.project.springbootjwt.service.impl;

import com.project.springbootjwt.exception.AuthenticatingCredentialsException;
import com.project.springbootjwt.jwtUtils.JwtResponse;
import com.project.springbootjwt.jwtUtils.JwtTokenProvider;
import com.project.springbootjwt.model.Role;
import com.project.springbootjwt.model.User;
import com.project.springbootjwt.repository.RoleRepository;
import com.project.springbootjwt.repository.UserRepository;
import com.project.springbootjwt.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
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
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    Logger logger = LoggerFactory.getLogger(UserService.class);


    @Override
    public JwtResponse login(String username, String password){
        try{
            authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(username, password) );
        }catch (BadCredentialsException badCredentialsException){
            throw new AuthenticatingCredentialsException("INVALID_CREDENTIALS", badCredentialsException);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        User user = userRepository.findByUsername(userDetails.getUsername());

        final String token = jwtTokenProvider.generateToken(userDetails);

        return new JwtResponse(token, user.getId());
    }

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

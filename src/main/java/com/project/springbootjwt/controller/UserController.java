package com.project.springbootjwt.controller;

import com.project.springbootjwt.exception.PasswordException;
import com.project.springbootjwt.model.ChangePassword;
import com.project.springbootjwt.model.User;
import com.project.springbootjwt.model.UserInformation;
import com.project.springbootjwt.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "api/v1/users")
public class UserController {
    Logger logger = LoggerFactory.getLogger( UserController.class );

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void save(@RequestBody User user){
        userService.save(user);
    }

    @RequestMapping(value = "/auth/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody User user){
        return ResponseEntity.ok(userService.login(user.getUsername(), user.getPassword()));
    }
    @RequestMapping(value = "/account/change/password", method = RequestMethod.POST)
    public void changePassword(@RequestBody ChangePassword changePassword){
        logger.info("{}", userService.comparePassword( changePassword.getId(), changePassword.getCurrentPassword() ));

        if(!userService.comparePassword(changePassword.getId(), changePassword.getCurrentPassword())){
            throw new PasswordException("Mismatch Current Password");
        }else if( !changePassword.getPassword().equals(changePassword.getConfirmPassword()) ){
            throw new PasswordException("Not match New Password");
        }else {
            userService.changePassword(changePassword.getId(), changePassword.getPassword());
        }
    }

    @RequestMapping(value = "/find/all", method = RequestMethod.GET)
    public List<UserInformation> findAllUsers(){
        return userService.findAllUser();
    }
    @RequestMapping(value = "/find/me/account", method = RequestMethod.GET)
    public UserInformation findUser(){
        return userService.findUser();
    }
}

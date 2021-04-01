package com.project.springbootjwt.service.impl;

import com.project.springbootjwt.model.Role;
import com.project.springbootjwt.model.User;
import com.project.springbootjwt.repository.RoleRepository;
import com.project.springbootjwt.repository.UserRepository;
import com.project.springbootjwt.service.UserService;
import javassist.Loader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if(user == null){
            throw new UsernameNotFoundException(username +" Not Found");
        }
        List<GrantedAuthority> roles = new ArrayList<>();
        for(Role role : user.getRoles()){
            roles.add(new SimpleGrantedAuthority("ROLE_"+role));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), roles);
    }
}

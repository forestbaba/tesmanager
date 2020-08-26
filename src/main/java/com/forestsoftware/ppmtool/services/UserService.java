package com.forestsoftware.ppmtool.services;

import com.forestsoftware.ppmtool.domain.User;
import com.forestsoftware.ppmtool.exceptions.UsernameAlreadyExistException;
import com.forestsoftware.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser(User user){
        try {
            user.setPassword( bCryptPasswordEncoder.encode(user.getPassword()));
            user.setUsername(user.getUsername());
            user.setConfirmPassword("");
            return userRepository.save(user);

        }catch (Exception e){
            throw  new UsernameAlreadyExistException("username '" +user.getUsername()+"' already exist");
        }
    }
}

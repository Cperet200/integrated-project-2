package com.ip2.myapp.backend.service;

import com.ip2.myapp.backend.entity.User;
import com.ip2.myapp.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@Service
public class UserService {


    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());
    private UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    @Transactional
    public void save(User user) {
        if (user == null) {
            LOGGER.log(Level.SEVERE,
                    "Answer is null. Are you sure you have connected your form to the application?");
            return;
        }
        userRepository.save(user);
    }



}

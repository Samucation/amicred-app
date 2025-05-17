package com.exosoft.amicred.service;


import com.exosoft.amicred.model.Profile;
import com.exosoft.amicred.model.User;
import com.exosoft.amicred.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> findByProfileType(String type) {
        return userRepository.findByProfileType(type);
    }

    public List<User> findAllOrderedByUsername() {
        return userRepository.findAllOrderedByUsername();
    }

    public Long countByProfile(Profile profile) {
        return userRepository.countByProfile(profile);
    }

}

package com.udacity.jwdnd.course1.cloudstorage.services.impl;

import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.HashService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserServiceImpl implements UserService {
    public final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserMapper userMapper;
    private final HashService hashService;

    public UserServiceImpl(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    @Override
    public boolean isUsernameExist(String username) {
        logger.info("isUsernameExist");
        return getUserByUsername(username) == null;
    }

    @Override
    public Integer createUser(User user) {
        logger.info("createUser");
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
        return userMapper.insert(
                new User(user.getUsername(), encodedSalt, hashedPassword, user.getFirstname(), user.getLastname())
        );
    }

    @Override
    public User getUserByUsername(String username) {
        logger.info("getUserByUsername");
        return userMapper.getUser(username);
    }
}

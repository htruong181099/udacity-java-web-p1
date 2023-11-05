package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.models.User;

public interface UserService {
    boolean isUsernameExist(String username);
    Integer createUser(User user);
    User getUserByUsername(String username);

}

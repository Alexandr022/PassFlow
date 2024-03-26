package com.pixelpunch.passflow.service.interfaces.UserInterfaces;

import com.pixelpunch.passflow.model.User.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();
    User register(User user);
    User findByEmail(String email);

    User getUserById(Long userId);

    User findByEmailAndPassword(String email, String password);

    User saveUser(User user);

    void deleteUser(Long userId);

    void deleteAllUsers();
}

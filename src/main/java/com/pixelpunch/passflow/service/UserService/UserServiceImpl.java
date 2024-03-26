package com.pixelpunch.passflow.service.UserService;

import com.pixelpunch.passflow.model.User.User;
import com.pixelpunch.passflow.repository.UserRepository.UserRepository;
import com.pixelpunch.passflow.service.interfaces.UserInterfaces.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User register(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return null;
        }
        return userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User getUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.orElse(null);
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            userRepository.deleteById(userId);
        } else {
            throw new EntityNotFoundException("User with id " + userId + " not found");
        }
    }

    @Override
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }
}
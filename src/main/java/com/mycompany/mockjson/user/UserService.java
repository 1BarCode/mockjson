package com.mycompany.mockjson.user;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.mockjson.exception.DuplicateResourceException;
import com.mycompany.mockjson.exception.ResourceNotFoundException;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public User createUser(User user) throws DuplicateResourceException {
        // check if user with username already exists
        userRepo.findByUsername(user.getUsername())
                .orElseThrow(() -> new DuplicateResourceException("Username is already taken"));

        // check if user with email already exists
        userRepo.findByEmail(user.getEmail())
                .orElseThrow(() -> new DuplicateResourceException("Email is already registered"));

        return userRepo.save(user);
    }

    public User getUserById(UUID id) throws ResourceNotFoundException {
        User existingUser = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return existingUser;
    }

    public List<User> listUsers() {
        return userRepo.findAll();
    }

    public User updateUserById(UUID id, User user) throws ResourceNotFoundException {
        User existingUser = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());

        return userRepo.save(existingUser);
    }

    public void deleteUserById(UUID id) throws ResourceNotFoundException {
        User existingUser = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        userRepo.delete(existingUser);
    }
}

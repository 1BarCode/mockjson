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
        User existingUser = userRepo.findByUsername(user.getUsername()).orElse(null);
        if (existingUser != null) {
            throw new DuplicateResourceException("Username is already taken");
        }

        // check if user with email already exists
        existingUser = userRepo.findByEmail(user.getEmail()).orElse(null);
        if (existingUser != null) {
            throw new DuplicateResourceException("Email is already registered");
        }

        return userRepo.save(user);
    }

    public User getUserById(UUID id) throws ResourceNotFoundException {
        User existingUser = userRepo.findById(id).orElse(null);

        if (existingUser == null) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }

        return existingUser;
    }

    public List<User> listUsers() {
        return userRepo.findAll();
    }

    public User updateUserById(UUID id, User user) throws ResourceNotFoundException {
        User existingUser = userRepo.findById(id).orElse(null);

        if (existingUser == null) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }

        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());

        return userRepo.save(existingUser);
    }

    public void deleteUserById(UUID id) throws ResourceNotFoundException {
        User existingUser = userRepo.findById(id).orElse(null);

        if (existingUser == null) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }

        userRepo.delete(existingUser);
    }
}

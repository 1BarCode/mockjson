package com.mycompany.mockjson.user;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mycompany.mockjson.auth.RegistrationRequest;
import com.mycompany.mockjson.exception.DuplicateResourceException;
import com.mycompany.mockjson.exception.ResourceNotFoundException;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Construct a user with information from a RegisterRequest, the user has not
     * been persisted.
     * 
     * @param request
     * @return unpersisted user
     */
    public User constructUserFromRequest(RegistrationRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEnabled(true);
        user.setLocked(false);

        return user;
    }

    public User createUser(User user) throws DuplicateResourceException {
        // check if user with username already exists
        User existedUser = userRepo.findByUsername(user.getUsername()).orElse(null);
        if (existedUser != null)
            throw new DuplicateResourceException("Username already exists");

        // check if user with email already exists
        existedUser = userRepo.findByEmail(user.getEmail()).orElse(null);
        if (existedUser != null)
            throw new DuplicateResourceException("Email already exists");

        return userRepo.save(user);
    }

    public User getUserById(UUID id) throws UsernameNotFoundException {
        User existingUser = userRepo.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return existingUser;
    }

    public User getUserByUsername(String username) throws UsernameNotFoundException {
        User existingUser = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
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

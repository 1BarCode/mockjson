package com.mycompany.mockjson.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mycompany.mockjson.exception.DuplicateResourceException;
import com.mycompany.mockjson.exception.ResourceNotFoundException;
import com.mycompany.mockjson.util.DataGenerator;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserService userService;

    private User user;

    private List<User> users;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setUsername("testUser");
        user.setEmail("testUser.gmail.com");
        user.setFirstName("Test");
        user.setLastName("User");

        users = new ArrayList<>();
        User user1 = new User();
        user1.setUsername(DataGenerator.generateRandomString(4));
        user1.setFirstName(DataGenerator.generateRandomString(4));
        user1.setLastName(DataGenerator.generateRandomString(4));
        user1.setEmail(user1.getFirstName() + user1.getLastName() + "@gmail.com");

        users.add(user);
        users.add(user1);
    }

    @Test
    public void createUserTestSuccess() throws DuplicateResourceException {
        // Arrange
        when(userRepo.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(userRepo.save(user)).thenReturn(user);


        // Act
        User createdUser = userService.createUser(user); // test that this method correctly calls userRepo.save(user) and returns the result

        // Assert
        assertNotEquals(null,createdUser);
        assertEquals(user.getUsername(), createdUser.getUsername());
        assertEquals(user.getEmail(), createdUser.getEmail());
    }

    @Test
    public void createUserTestFail() {
        // Arrange
        when(userRepo.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        // Act
        User createdUser;

        try {
            createdUser = userService.createUser(user); // test that this method correctly calls userRepo.findByUsername(user.getUsername()) and throws a DuplicateResourceException when findByUsername returns a non-empty Optional
        } catch (DuplicateResourceException e) {
            createdUser = null;
        }

        // Assert
        assertEquals(null, createdUser);
    }

    @Test
    public void getUserByIdSuccess() throws ResourceNotFoundException {
        // Arrange
        when(userRepo.findById(user.getId())).thenReturn(Optional.of(user));

        // Act
        User foundUser = userService.getUserById(user.getId()); // test that this method correctly calls userRepo.findById(user.getId()) and returns the result

        // Assert
        assertNotEquals(null, foundUser);
        assertEquals(user.getUsername(), foundUser.getUsername());
        assertEquals(user.getEmail(), foundUser.getEmail());
    }

    @Test
    public void getUserByIdFail() {
        // Arrange
        when(userRepo.findById(user.getId())).thenReturn(Optional.empty());

        // Act
        User foundUser;
        try {
            foundUser = userService.getUserById(user.getId()); // test that this method correctly calls userRepo.findById(user.getId()) throws a ResourceNotFoundException when findById returns empty Optional
        } catch (ResourceNotFoundException e) {
            foundUser = null;
        } 

        // Assert
        assertEquals(null, foundUser);
    }

    @Test
    public void listUsersSuccess() {
        // Arrange
        when(userRepo.findAll()).thenReturn(users);

        // Act
        List<User> foundUsers = userService.listUsers();

        // Assert
        assertEquals(users.get(0).getEmail(), foundUsers.get(0).getEmail());
        assertEquals(users.get(1).getEmail(), foundUsers.get(1).getEmail());
    }

    @Test
    public void listUsersFail() {
        when(userRepo.findAll()).thenReturn(new ArrayList<User>());

        List<User> foundUsers = userService.listUsers();

        assertEquals(0, foundUsers.size());
    }

    // test update user by id fail
    @Test
    public void updateUserByIdSuccess() throws ResourceNotFoundException {
        // Arrange
        when(userRepo.findById(Mockito.any())).thenReturn(Optional.of(user));
        
        // update the user with new info to be saved
        user.setUsername("newUsername");
        user.setFirstName(DataGenerator.generateRandomString(4));
        user.setLastName(DataGenerator.generateRandomString(4));
        user.setEmail(user.getFirstName() + user.getLastName() + "@gmail.com");

        when(userRepo.save(user)).thenReturn(user);

        // Act
        User updatedUser = userService.updateUserById(user.getId(), user);

        // Assert
        assertEquals(user.getUsername(), updatedUser.getUsername());
        assertEquals(user.getEmail(), updatedUser.getEmail());
    }

    // test delete user by id success
    @Test
    public void deleteUserByIdSuccess() throws ResourceNotFoundException {
        // Arrange
        when(userRepo.findById(Mockito.any())).thenReturn(Optional.of(user));

        // Act
        userService.deleteUserById(user.getId());

        // Assert
        Mockito.verify(userRepo, Mockito.times(1)).delete(user);
    }

    // test delete user by id fail
    @Test
    public void deleteUserFail() {
        // Arrange
        when(userRepo.findById(Mockito.any())).thenReturn(Optional.empty());

        // Act
        String message = "";
        try {
            userService.deleteUserById(user.getId());
        } catch (ResourceNotFoundException e) {
            message = e.getMessage();
        }

        // Assert
        assertEquals("User not found with id: " + user.getId(), message);
    }
}

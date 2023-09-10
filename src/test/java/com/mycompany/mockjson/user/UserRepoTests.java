package com.mycompany.mockjson.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.mycompany.mockjson.util.DataGenerator;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepoTests {
    @Autowired
    private UserRepo userRepo;

    @Test
    public void testCreateUser() {
        String username = DataGenerator.generateRandomString(5);
        String firstName = DataGenerator.generateRandomString(5);
        String lastName = DataGenerator.generateRandomString(5);
        String email = firstName + lastName + "@umail.com";
        int uuidLength = 36;

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword("password");

        User savedUser = userRepo.save(user);

        System.out.println(savedUser);

        assertNotEquals(null, savedUser);
        assertNotEquals(null, savedUser.getId());
        assertEquals(uuidLength, savedUser.getId().toString().length());
        assertEquals(username, savedUser.getUsername());
        assertEquals(email, savedUser.getEmail());
        assertEquals(firstName, savedUser.getFirstName());
        assertEquals(lastName, savedUser.getLastName());
    }

    @Test
    public void testGetUserById() {
        String username = DataGenerator.generateRandomString(5);
        String firstName = DataGenerator.generateRandomString(5);
        String lastName = DataGenerator.generateRandomString(5);
        String email = firstName + lastName + "@umail.com";

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);

        User savedUser = userRepo.save(user);

        User foundUser = userRepo.findById(savedUser.getId()).orElse(null);

        System.out.println(foundUser);

        assertNotEquals(null, foundUser);
        assertNotEquals(null, foundUser.getId());
        assertEquals(savedUser.getId().toString().length(), foundUser.getId().toString().length());
        assertEquals(savedUser.getUsername(), foundUser.getUsername());
        assertEquals(savedUser.getEmail(), foundUser.getEmail());
        assertEquals(savedUser.getFirstName(), foundUser.getFirstName());
        assertEquals(savedUser.getLastName(), foundUser.getLastName());
    }

    @Test
    public void testGetNotFound() {
        UUID uuid = UUID.randomUUID();

        User foundUser = userRepo.findById(uuid).orElse(null);

        System.out.println(foundUser);
        assertEquals(null, foundUser);
    }

    @Test
    public void testGetFound() {
        UUID id = UUID.fromString("8ea5df56-9aa8-4cb3-9066-e31238b02321");

        User foundUser = userRepo.findById(id).orElse(null);

        assertNotEquals(null, foundUser);
    }

}

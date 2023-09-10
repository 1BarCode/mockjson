package com.mycompany.mockjson.auth.role;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.mycompany.mockjson.auth.authority.Authority;
import com.mycompany.mockjson.auth.authority.AuthorityRepo;
import com.mycompany.mockjson.user.User;
import com.mycompany.mockjson.user.UserRepo;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleRepoTests {
    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AuthorityRepo authorityRepo;

    @Test
    public void testListRoles() {
        List<Role> roles = roleRepo.findAll();

        assertEquals(3, roles.size());
    }

    @Test
    public void testCreateUserWithRole() {
        User user = new User();
        user.setUsername("test");
        user.setFirstName("test");
        user.setLastName("user");
        user.setEmail(user.getFirstName() + user.getLastName() + "@umail.com");
        user.setPassword("password");
        user.setEnabled(true);
        user.setLocked(false);

        Role role = roleRepo.findByName(RoleName.ROLE_USER);

        Authority authority = new Authority();
        authority.setUser(user);
        authority.setRole(role);

        user.addAuthority(authority);

        User savedUser = userRepo.save(user);

        assertNotEquals(null, savedUser);
        assertEquals(true, savedUser.getEnabled());
        assertEquals(false, savedUser.isLocked());
    }
}

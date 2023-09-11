package com.mycompany.mockjson.auth;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class RegisterRequest {
    @NotNull
    @Length(min = 4, max = 50)
    private String username;

    @NotNull
    @Email
    @Length(min = 4, max = 50)
    private String email;

    @NotNull
    @Length(min = 7, max = 255)
    private String password;

    @NotNull
    @Length(min = 2, max = 50)
    private String firstName;

    @NotNull
    @Length(min = 2, max = 50)
    private String lastName;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

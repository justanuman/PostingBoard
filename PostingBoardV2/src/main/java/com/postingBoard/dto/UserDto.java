package com.postingBoard.dto;

import java.io.Serializable;

public class UserDto implements Serializable {

    public Integer id;
    private final String username;
    private final String firstName;
    private final String lastName;
    private final String phoneNumber;
    private final String email;
    private final Integer personalRating;

    public UserDto(String username, String firstName, String lastName, String phoneNumber, String email, Integer personalRating) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.personalRating = personalRating;
    }


    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public Integer getPersonalRating() {
        return personalRating;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

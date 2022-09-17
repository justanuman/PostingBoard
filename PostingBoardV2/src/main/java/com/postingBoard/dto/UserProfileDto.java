package com.postingBoard.dto;

import java.io.Serializable;
import java.util.Objects;

public class UserProfileDto implements Serializable {
    private final String username;
    private final String firstName;
    private final String lastName;
    private final String password;
    private final String status;
    private final String phoneNumber;
    private final String bankNumber;
    private final String email;

    public UserProfileDto(String username, String firstName, String lastName, String password, String status, String phoneNumber, String bankNumber, String email) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.status = status;
        this.phoneNumber = phoneNumber;
        this.bankNumber = bankNumber;
        this.email = email;
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

    public String getPassword() {
        return password;
    }

    public String getStatus() {
        return status;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProfileDto entity = (UserProfileDto) o;
        return Objects.equals(this.username, entity.username) &&
                Objects.equals(this.firstName, entity.firstName) &&
                Objects.equals(this.lastName, entity.lastName) &&
                Objects.equals(this.password, entity.password) &&
                Objects.equals(this.status, entity.status) &&
                Objects.equals(this.phoneNumber, entity.phoneNumber) &&
                Objects.equals(this.bankNumber, entity.bankNumber) &&
                Objects.equals(this.email, entity.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, firstName, lastName, password, status, phoneNumber, bankNumber, email);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "username = " + username + ", " +
                "firstName = " + firstName + ", " +
                "lastName = " + lastName + ", " +
                "password = " + password + ", " +
                "status = " + status + ", " +
                "phoneNumber = " + phoneNumber + ", " +
                "bankNumber = " + bankNumber + ", " +
                "email = " + email + ")";
    }

}

package com.postingBoard.entity;

import com.postingBoard.dto.UserDto;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users", schema = "posting_board_db")
public class DbUser {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID")
    private Integer id;
    @Basic
    @Column(name = "username")
    private String username;
    @Basic
    @Column(name = "first_name", columnDefinition = "varchar(50) default \"not listed\"")
    private String firstName;
    @Basic
    @Column(name = "last_name", columnDefinition = "varchar(50) default \"not listed\"")
    private String lastName;
    @Basic
    @Column(name = "password")
    private String password;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", columnDefinition = "default current_timestamp")
    private Date created;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated", columnDefinition = " default current_timestamp on update current_timestamp")
    private Date updated;
    @Basic
    @Column(name = "status")
    private String status;
    @Basic
    @Column(name = "phone_number", columnDefinition = "varchar(18) unique ")
    private String phoneNumber;

    @Basic
    @Column(name = "bank_number", columnDefinition = "varchar(50) unique  ")
    private String bankNumber;
    @Basic
    @Column(name = "email", columnDefinition = "varchar(300) unique  ")
    private String email;
    @Basic
    @Column(name = "personal_rating")
    private Integer personalRating;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "ID")})
    private List<DbRole> roles;

    public List<DbRole> getRoles() {
        return roles;
    }

    public void setRoles(List<DbRole> roles) {
        this.roles = roles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPersonalRating() {
        return personalRating;
    }

    public void setPersonalRating(Integer personalRating) {
        this.personalRating = personalRating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DbUser users = (DbUser) o;
        return Objects.equals(id, users.id) && Objects.equals(username, users.username) && Objects.equals(firstName, users.firstName) && Objects.equals(lastName, users.lastName) && Objects.equals(password, users.password) && Objects.equals(created, users.created) && Objects.equals(updated, users.updated) && Objects.equals(status, users.status) && Objects.equals(phoneNumber, users.phoneNumber) && Objects.equals(bankNumber, users.bankNumber) && Objects.equals(email, users.email) && Objects.equals(personalRating, users.personalRating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, firstName, lastName, password, created, updated, status, phoneNumber, bankNumber, email, personalRating);
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                ", status='" + status + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", bankNumber='" + bankNumber + '\'' +
                ", email='" + email + '\'' +
                ", personalRating=" + personalRating +
                '}';
    }

    public UserDto touserDTO() {
        UserDto userDTO = new UserDto(username, firstName, lastName, phoneNumber, email, personalRating);
        userDTO.setId(id);
        return userDTO;
    }
}

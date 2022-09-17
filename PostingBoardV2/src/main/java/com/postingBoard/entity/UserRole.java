package com.postingBoard.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_roles", schema = "posting_board_db")
public class UserRole {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID")
    private Integer id;
    @Basic
    @Column(name = "user_ID")
    private Integer userId;
    @Basic
    @Column(name = "role_id")
    private Integer roleId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRole userRoles = (UserRole) o;
        return Objects.equals(id, userRoles.id) && Objects.equals(userId, userRoles.userId) && Objects.equals(roleId, userRoles.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, roleId);
    }
}

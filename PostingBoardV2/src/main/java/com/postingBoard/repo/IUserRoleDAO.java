package com.postingBoard.repo;

import com.postingBoard.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IUserRoleDAO extends JpaRepository<UserRole, Integer> {
    List<UserRole> findAllByUserId(int userID);
}

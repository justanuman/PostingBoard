package com.postingBoard.repo;

import com.postingBoard.entity.DbUser;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IUserDAO extends JpaRepository<DbUser, Integer> {
    DbUser findByUsername(String username);
}

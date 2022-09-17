package com.postingBoard.repo;

import com.postingBoard.entity.DbRole;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IRoleDAO extends JpaRepository<DbRole, Integer> {
    DbRole findByName(String name);

    DbRole findById(int id);
}

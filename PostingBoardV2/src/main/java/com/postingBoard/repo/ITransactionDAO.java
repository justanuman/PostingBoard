package com.postingBoard.repo;

import com.postingBoard.entity.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ITransactionDAO extends CrudRepository<Transaction, Integer> {
    @Query("select t from Transaction t where t.status = 'OPEN'")
    List<Transaction> findAllByOpenStatus();
}

package com.postingBoard.service.interfaces;

import com.postingBoard.dto.InternalTransactionsDto;
import com.postingBoard.dto.TransactionsDto;
import com.postingBoard.entity.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ITransactionService {
    TransactionsDto openTransaction(TransactionsDto input) throws Exception;

    Boolean closeTransaction(int id);

    Boolean checkIfTransactionIsOpen(int id);

    List<InternalTransactionsDto> getAllOpenTransactions();

    Transaction findById(int id);

    void checkForNull(Object object);
}

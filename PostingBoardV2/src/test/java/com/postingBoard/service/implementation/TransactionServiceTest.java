package com.postingBoard.service.implementation;

import com.postingBoard.dto.InternalTransactionsDto;
import com.postingBoard.dto.TransactionsDto;
import com.postingBoard.entity.Post;
import com.postingBoard.entity.Transaction;
import com.postingBoard.entity.DbUser;
import com.postingBoard.repo.IMessageDAO;
import com.postingBoard.repo.IPostDAO;
import com.postingBoard.repo.ITransactionDAO;
import com.postingBoard.repo.IUserDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {
    @Mock
    ITransactionDAO transactionDAO;
    @Mock
    IUserDAO userDAO;
    @Mock
    IPostDAO postDAO;
    @Mock
    IMessageDAO messageDAO;
@InjectMocks
TransactionService transactionService;
    @BeforeEach
    void initUseCase() {
        MockitoAnnotations.openMocks(transactionService);
    }
    @Test
    void openTransaction() throws NullPointerException {
        TransactionsDto input= new TransactionsDto(1,2,3, BigDecimal.TEN,"open",3);
        Transaction newTransaction = new Transaction();
        newTransaction.setTransactionValue(input.getTransactionValue());
        newTransaction.setBuyerId(input.getBuyerId());
        newTransaction.setSellerId(input.getSellerId());
        newTransaction.setPostId(input.getPostId());
        newTransaction.setSpecialPostID(input.getSpecialPostID());
        newTransaction.setBankNumber("3");
        newTransaction.setStatus("OPEN");
        DbUser user = new DbUser();
        user.setBankNumber("3");
        when(userDAO.findById(2)).thenReturn(Optional.of(user));
        when(transactionDAO.save(newTransaction)).thenReturn(newTransaction);
        Assertions.assertEquals(transactionService.openTransaction(input).equals(newTransaction.toDTO()),true);
    }

    @Test
    void closeTransaction() {
        TransactionsDto input= new TransactionsDto(1,2,3, BigDecimal.TEN,"open",3);
        Transaction newTransaction = new Transaction();
        newTransaction.setTransactionValue(input.getTransactionValue());
        newTransaction.setBuyerId(input.getBuyerId());
        newTransaction.setSellerId(input.getSellerId());
        newTransaction.setPostId(input.getPostId());
        newTransaction.setSpecialPostID(input.getSpecialPostID());
        newTransaction.setBankNumber("3");
        newTransaction.setStatus("OPEN");
        DbUser user = new DbUser();
        user.setBankNumber("3");
        Post posts = new Post();
        posts.setId(3);
        posts.setStatus("OPEN");
        when(transactionDAO.findById(1)).thenReturn(Optional.of(newTransaction));
        when(postDAO.findById(3)).thenReturn(Optional.of(posts));
        Assertions.assertEquals(transactionService.closeTransaction(1),true);
    }

    @Test
    void checkIfTransactionIsOpen() {
        TransactionsDto input= new TransactionsDto(1,2,3, BigDecimal.TEN,"open",3);
        Transaction newTransaction = new Transaction();
        newTransaction.setTransactionValue(input.getTransactionValue());
        newTransaction.setBuyerId(input.getBuyerId());
        newTransaction.setSellerId(input.getSellerId());
        newTransaction.setPostId(input.getPostId());
        newTransaction.setSpecialPostID(input.getSpecialPostID());
        newTransaction.setBankNumber("3");
        newTransaction.setStatus("OPEN");
        when(transactionDAO.findById(1)).thenReturn(Optional.of(newTransaction));
        Assertions.assertEquals(transactionService.checkIfTransactionIsOpen(1),true);
    }

    @Test
    void getAllOpenTransactions() {
        TransactionsDto input= new TransactionsDto(1,2,3, BigDecimal.TEN,"open",3);
        Transaction newTransaction = new Transaction();
        newTransaction.setTransactionValue(input.getTransactionValue());
        newTransaction.setBuyerId(input.getBuyerId());
        newTransaction.setSellerId(input.getSellerId());
        newTransaction.setPostId(input.getPostId());
        newTransaction.setSpecialPostID(input.getSpecialPostID());
        newTransaction.setBankNumber("3");
        newTransaction.setStatus("OPEN");
        List<Transaction> transactionsList= new ArrayList<>();
        List<InternalTransactionsDto> out = new ArrayList<>();
        transactionsList.add(newTransaction);
        out.add(newTransaction.toInternalDTO());
        when(transactionDAO.findAllByOpenStatus()).thenReturn(transactionsList);
        Assertions.assertEquals(transactionService.getAllOpenTransactions().get(0).equals(newTransaction.toInternalDTO()),true);
    }

    @Test
    void findById() {
        when(transactionDAO.findById(1)).thenReturn(Optional.of(new Transaction()));
        Assertions.assertNotEquals(transactionService.findById(1),null);
    }

}
package com.postingBoard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.postingBoard.dto.InternalTransactionsDto;
import com.postingBoard.dto.TransactionsDto;
import com.postingBoard.entity.DbUser;
import com.postingBoard.entity.Post;
import com.postingBoard.service.interfaces.ICommentService;
import com.postingBoard.service.interfaces.IPostService;
import com.postingBoard.service.interfaces.ITransactionService;
import com.postingBoard.service.interfaces.IUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {
    @Mock
    IUserService userService;
    @Mock
    ICommentService commentService;
    @Mock
    ITransactionService transactionService;
    @Mock
    IPostService postService;
    @InjectMocks
    TransactionController transactionController;

    @BeforeEach
    void initUseCase() {
        MockitoAnnotations.openMocks(transactionController);
    }

    @Test
    void openTransaction() throws Exception {
        Post post = new Post();
        post.setId(1);
        post.setAuthorId(1);
        post.setPrice(BigDecimal.valueOf(10));
        DbUser user = new DbUser();
        user.setId(1);
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("me");
        when(userService.findByUsername("me")).thenReturn(user);
        when(postService.findByID(1)).thenReturn(post);
        TransactionsDto transactionsDto = new TransactionsDto(1, 1, 1, BigDecimal.TEN, "OPEN", 1);
        when(transactionService.openTransaction(transactionsDto)).thenReturn(transactionsDto);
        TransactionsDto out = transactionController.openTransaction(1, 1, mockPrincipal);
        Assertions.assertEquals(out, transactionsDto);
    }

    @Test
    void promoteTransaction() throws Exception {
        Post post = new Post();
        post.setId(1);
        post.setAuthorId(1);
        post.setPrice(BigDecimal.valueOf(10));
        DbUser user = new DbUser();
        user.setId(1);
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("me");
        when(userService.findByUsername("me")).thenReturn(user);
        when(postService.findByID(1)).thenReturn(post);
        TransactionsDto transactionsDto = new TransactionsDto(1, 1, 1, BigDecimal.TEN, "OPEN", 1);
        when(transactionService.openTransaction(transactionsDto)).thenReturn(transactionsDto);
        TransactionsDto out = transactionController.openTransaction(1, 1, mockPrincipal);
        Assertions.assertEquals(out, transactionsDto);
    }

    @Test
    void promoteUserTransaction() throws Exception {
        Post post = new Post();
        post.setId(1);
        post.setAuthorId(1);
        post.setPrice(BigDecimal.valueOf(10));
        DbUser user = new DbUser();
        user.setId(1);
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("me");
        when(userService.findByUsername("me")).thenReturn(user);
        when(postService.findByID(1)).thenReturn(post);
        TransactionsDto transactionsDto = new TransactionsDto(1, 1, 1, BigDecimal.TEN, "OPEN", 1);
        when(transactionService.openTransaction(transactionsDto)).thenReturn(transactionsDto);
        TransactionsDto out = transactionController.openTransaction(1, 1, mockPrincipal);
        Assertions.assertEquals(out, transactionsDto);
    }

    @Test
    void transactionCheck() throws Exception {
        when(transactionService.checkIfTransactionIsOpen(1)).thenReturn(Boolean.TRUE);
        Assertions.assertEquals(transactionController.transactionCheck(1), Boolean.TRUE);

    }

    @Test
    void getTransactions() throws Exception {
        List<InternalTransactionsDto> out = new ArrayList<>();
        out.add(new InternalTransactionsDto(1,BigDecimal.TEN,"11"));
        when(transactionService.getAllOpenTransactions()).thenReturn(out);
        Assertions.assertEquals(transactionController.getTransactions(), out);

    }

    @Test
    void closeTransaction() {
        when(transactionService.closeTransaction(1)).thenReturn(Boolean.TRUE);
        Assertions.assertEquals(transactionController.closeTransaction(1),Boolean.TRUE);
    }
}
package com.postingBoard.controller;

import com.postingBoard.dto.InternalTransactionsDto;
import com.postingBoard.dto.TransactionsDto;
import com.postingBoard.entity.Post;
import com.postingBoard.service.interfaces.IChatService;
import com.postingBoard.service.interfaces.IPostService;
import com.postingBoard.service.interfaces.ITransactionService;
import com.postingBoard.service.interfaces.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
public class TransactionController {
    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);
    @Autowired
    IUserService userService;
    @Autowired
    ITransactionService transactionService;
    @Autowired
    IPostService postService;

    @Autowired
    IChatService chatService;

    @PostMapping(
            value = "/board/{postid}/buy",
            produces = "application/json"
    )
    @ResponseBody
    public TransactionsDto openTransaction(@PathVariable int postid, @RequestParam(required = false) Integer toBePromotedPostID, Principal principal) throws Exception {
        Post posts = postService.findByID(postid);
        if (posts != null && postid != 1 && postid != 2) {
            TransactionsDto transactionsDto = new TransactionsDto(posts.getAuthorId(), userService.findByUsername(principal.getName()).getId(), postid, posts.getPrice(), "OPEN");
            transactionsDto.setSpecialPostID(3);
            transactionsDto = transactionService.openTransaction(transactionsDto);
            return transactionsDto;
        } else if (posts != null && postid == 1) {
            TransactionsDto transactionsDto = new TransactionsDto(posts.getAuthorId(), userService.findByUsername(principal.getName()).getId(), toBePromotedPostID, posts.getPrice(), "OPEN", postid);
            transactionsDto = transactionService.openTransaction(transactionsDto);
            return transactionsDto;
        } else if (posts != null && postid == 2) {
            TransactionsDto transactionsDto = new TransactionsDto(posts.getAuthorId(), userService.findByUsername(principal.getName()).getId(), postid, posts.getPrice(), "OPEN", postid);
            transactionsDto = transactionService.openTransaction(transactionsDto);
            return transactionsDto;
        } else {
            throw new ResponseStatusException(NOT_FOUND, "Unable to find resource");
        }
    }

    @PutMapping(
            value = "/board/{postid}/promotePost",
            produces = "application/json"
    )
    @ResponseBody
    public TransactionsDto promoteTransaction(@PathVariable int postid, @RequestParam(required = false) Double amount , Principal principal) throws Exception {
        if(amount==null)
        {
            amount=1000.0;
        }
        Post posts = postService.findByID(postid);
        TransactionsDto transactionsDto = new TransactionsDto(1, userService.findByUsername(principal.getName()).getId(), postid,BigDecimal.valueOf(amount), "OPEN", 1);
        transactionsDto = transactionService.openTransaction(transactionsDto);
        return transactionsDto;
    }

    @PostMapping(
            value = "/user/promote",
            produces = "application/json"
    )
    @ResponseBody
    public TransactionsDto promoteUserTransaction(@RequestParam(required = false) Double amount , Principal principal) throws Exception {
        if(amount==null)
        {
            amount=1000.0;
        }
        TransactionsDto transactionsDto = new TransactionsDto(1, userService.findByUsername(principal.getName()).getId(), 2, BigDecimal.valueOf(amount), "OPEN", 2);
        transactionsDto = transactionService.openTransaction(transactionsDto);
        return transactionsDto;
    }

    @GetMapping(
            value = "/transactionCheck",
            produces = "application/json"
    )
    @ResponseBody
    public Boolean transactionCheck(@RequestParam int id) throws Exception {
        return transactionService.checkIfTransactionIsOpen(id);
    }

    @GetMapping(
            value = "/gettransactions",
            produces = "application/json"
    )
    @ResponseBody
    public List<InternalTransactionsDto> getTransactions() throws Exception {
        return transactionService.getAllOpenTransactions();
    }

    @PutMapping(
            value = "/closetransaction",
            produces = "application/json"
    )
    @ResponseBody
    public Boolean closeTransaction(@RequestParam int id) {
        return transactionService.closeTransaction(id);
    }

}

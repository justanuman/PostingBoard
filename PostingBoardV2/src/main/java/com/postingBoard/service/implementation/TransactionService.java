package com.postingBoard.service.implementation;

import com.postingBoard.dto.InternalTransactionsDto;
import com.postingBoard.dto.TransactionsDto;
import com.postingBoard.entity.ChatMessage;
import com.postingBoard.entity.DbUser;
import com.postingBoard.entity.Post;
import com.postingBoard.entity.Transaction;
import com.postingBoard.repo.IMessageDAO;
import com.postingBoard.repo.IPostDAO;
import com.postingBoard.repo.ITransactionDAO;
import com.postingBoard.repo.IUserDAO;
import com.postingBoard.service.interfaces.ITransactionService;
import com.postingBoard.utility.Mappers.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@Transactional
public class TransactionService implements ITransactionService {
    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    ITransactionDAO transactionDAO;
    @Autowired
    IUserDAO userDAO;
    @Autowired
    IPostDAO postDAO;

    @Autowired
    IMessageDAO messageDAO;



    TransactionDtoAdapter transactionDtoAdapter = new TransactionDtoAdapter();


    InternalTransactionDtoAdapter internalTransactionDtoAdapter = new InternalTransactionDtoAdapter();
    @Override
    public TransactionsDto openTransaction(TransactionsDto input) throws NullPointerException {
        //todo exeption
        Transaction newTransaction = new Transaction();
        newTransaction.setTransactionValue(input.getTransactionValue());
        newTransaction.setBuyerId(input.getBuyerId());
        newTransaction.setSellerId(input.getSellerId());
        newTransaction.setPostId(input.getPostId());
        newTransaction.setSpecialPostID(input.getSpecialPostID());
        String s = userDAO.findById(input.getBuyerId()).orElse(null).getBankNumber();
        if (s == null) {
            throw new NullPointerException("no buyer");
        }
        if (input.getSpecialPostID() == null) {
            throw new NullPointerException("wrong setting");
        }
        newTransaction.setBankNumber(s);
        newTransaction.setStatus("OPEN");
        logger.info("opening transasction  to bank {}", s);
       // return transactionDAO.save(newTransaction).toDTO();
        return transactionDtoAdapter.modelToDto(transactionDAO.save(newTransaction));
    }

    @Override
    public Boolean closeTransaction(int id) {
        Transaction transactions = transactionDAO.findById(id).orElse(null);
        Post post = postDAO.findById(transactions.getPostId()).orElse(null);
        Post specialPost = postDAO.findById(transactions.getSpecialPostID()).orElse(null);
        if (post == null || specialPost == null || "CLOSED".equals(post.getStatus())) {
            return false;
        }
        if (specialPost.getId().equals(1)) {
            int newScore = post.getRating() + transactions.getTransactionValue().intValue();
            post.setRating(newScore);
            transactions.setStatus("CLOSED");
            postDAO.save(post);
            transactionDAO.save(transactions);
            return true;
        } else if (specialPost.getId().equals(2)) {
            DbUser user = userDAO.findById(transactions.getBuyerId()).orElse(null);
            user.setPersonalRating(user.getPersonalRating() + transactions.getTransactionValue().intValue());
            transactions.setStatus("CLOSED");
            userDAO.save(user);
            transactionDAO.save(transactions);
            return true;
        } else if (specialPost.getId().equals(3)) {
            post.setStatus("CLOSED");
            transactions.setStatus("CLOSED");
            postDAO.save(post);
            transactionDAO.save(transactions);
            ChatMessage messages = new ChatMessage();
            messages.setAuthorId(transactions.getBuyerId());
            messages.setContents("i bought your stuff");
            messages.setRecepientId(transactions.getSellerId());
            messageDAO.save(messages);
            return true;
        }
        logger.info("closing transaction {}", id);
        transactionDAO.save(transactions);
        return false;
    }

    @Override
    public Boolean checkIfTransactionIsOpen(int id) {
        Transaction transactions = transactionDAO.findById(id).orElse(null);
        logger.info("checkIfTransactionIsOpen {}", id);
        return transactions.getStatus().equals("OPEN");
    }

    @Override
    public List<InternalTransactionsDto> getAllOpenTransactions() {
        List<Transaction> transactions = transactionDAO.findAllByOpenStatus();
        List<InternalTransactionsDto> out = new ArrayList<>();
        for (Transaction elem : transactions) {
            out.add(internalTransactionDtoAdapter.modelToDto(elem));
            //out.add(elem.toInternalDTO());
        }
        logger.info("getAllOpenTransactions");
        return out;
    }

    @Override
    public Transaction findById(int id) {
        logger.info("looking for transaction {}", id);
        return transactionDAO.findById(id).orElse(null);
    }

    @Override
    public void checkForNull(Object object) {
        if (object.equals(null)) {
            throw new ResponseStatusException(NOT_FOUND, "Unable to find resource");
        }
    }
}

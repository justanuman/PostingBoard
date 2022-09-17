package com.postingBoard.utility.Mappers;

import com.postingBoard.dto.TransactionsDto;
import com.postingBoard.entity.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionDtoAdapter implements Adapter<Transaction, TransactionsDto> {
    @Override
    public TransactionsDto modelToDto(Transaction model) {
        return new TransactionsDto(model.getSellerId(), model.getBuyerId(), model.getPostId(), model.getTransactionValue(), model.getStatus(), model.getSpecialPostID());

    }

    @Override
    public Transaction dtoToModel(TransactionsDto dto) {
        return Adapter.super.dtoToModel(dto);
    }
}

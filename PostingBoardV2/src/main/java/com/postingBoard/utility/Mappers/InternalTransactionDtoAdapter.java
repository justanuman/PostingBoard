package com.postingBoard.utility.Mappers;

import com.postingBoard.dto.InternalTransactionsDto;
import com.postingBoard.entity.Transaction;
import org.springframework.stereotype.Component;

@Component
public class InternalTransactionDtoAdapter implements Adapter<Transaction, InternalTransactionsDto> {
    @Override
    public  InternalTransactionsDto modelToDto(Transaction model) {
        return new InternalTransactionsDto(model.getId(), model.getTransactionValue(), model.getBankNumber());
    }

    @Override
    public Transaction dtoToModel(InternalTransactionsDto dto) {
        return Adapter.super.dtoToModel(dto);
    }
}

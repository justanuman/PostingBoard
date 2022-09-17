package com.postingBoard.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class InternalTransactionsDto implements Serializable {
    private final Integer id;
    private final BigDecimal transactionValue;
    private final String bankNumber;

    public InternalTransactionsDto(Integer id, BigDecimal transactionValue, String bankNumber) {
        this.id = id;
        this.transactionValue = transactionValue;
        this.bankNumber = bankNumber;
    }

    public Integer getId() {
        return id;
    }

    public BigDecimal getTransactionValue() {
        return transactionValue;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InternalTransactionsDto entity = (InternalTransactionsDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.transactionValue, entity.transactionValue) &&
                Objects.equals(this.bankNumber, entity.bankNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, transactionValue, bankNumber);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "transactionValue = " + transactionValue + ", " +
                "bankNumber = " + bankNumber + ")";
    }
}

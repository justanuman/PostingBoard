package com.postingBoard.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class TransactionsDto implements Serializable {

    private final Integer sellerId;
    private final Integer buyerId;
    private final Integer postId;
    private final BigDecimal transactionValue;
    private final String status;
    private Integer specialPostID;
    public TransactionsDto(Integer sellerId, Integer buyerId, Integer postId, BigDecimal transactionValue, String status) {

        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.postId = postId;
        this.transactionValue = transactionValue;
        this.status = status;
    }

    public TransactionsDto(Integer sellerId, Integer buyerId, Integer postId, BigDecimal transactionValue, String status, Integer specialPostID) {

        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.postId = postId;
        this.transactionValue = transactionValue;
        this.status = status;
        this.specialPostID = specialPostID;
    }

    public Integer getSpecialPostID() {
        return specialPostID;
    }

    public void setSpecialPostID(Integer specialPostID) {
        this.specialPostID = specialPostID;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public Integer getBuyerId() {
        return buyerId;
    }

    public Integer getPostId() {
        return postId;
    }

    public BigDecimal getTransactionValue() {
        return transactionValue;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionsDto entity = (TransactionsDto) o;
        return Objects.equals(this.sellerId, entity.sellerId) &&
                Objects.equals(this.buyerId, entity.buyerId) &&
                Objects.equals(this.postId, entity.postId) &&
                Objects.equals(this.transactionValue, entity.transactionValue) &&
                Objects.equals(this.status, entity.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sellerId, buyerId, postId, transactionValue, status);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "sellerId = " + sellerId + ", " +
                "buyerId = " + buyerId + ", " +
                "postId = " + postId + ", " +
                "transactionValue = " + transactionValue + ", " +
                "status = " + status + ")";
    }
}

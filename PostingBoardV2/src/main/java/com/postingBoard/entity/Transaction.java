package com.postingBoard.entity;

import com.postingBoard.dto.InternalTransactionsDto;
import com.postingBoard.dto.TransactionsDto;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "transactions", schema = "posting_board_db")
public class Transaction {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID")
    private Integer id;
    @Basic
    @Column(name = "seller_ID")
    private Integer sellerId;
    @Basic
    @Column(name = "buyer_ID")
    private Integer buyerId;
    @Basic
    @Column(name = "post_ID")
    private Integer postId;
    @Basic
    @Column(name = "transaction_value")
    private BigDecimal transactionValue;
    @Basic
    @Column(name = "bank_number")
    private String bankNumber;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", columnDefinition = "default current_timestamp")
    private Date created;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated", columnDefinition = " default current_timestamp on update current_timestamp")
    private Date updated;
    @Basic
    @Column(name = "status")
    private String status;
    @Basic
    @Column(name = "special_post_ID")
    private int specialPostID;

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public int getSpecialPostID() {
        return specialPostID;
    }

    public void setSpecialPostID(int specialPostID) {
        this.specialPostID = specialPostID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public Integer getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Integer buyerId) {
        this.buyerId = buyerId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public BigDecimal getTransactionValue() {
        return transactionValue;
    }

    public void setTransactionValue(BigDecimal transactionValue) {
        this.transactionValue = transactionValue;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id) && Objects.equals(sellerId, that.sellerId) && Objects.equals(buyerId, that.buyerId) && Objects.equals(postId, that.postId) && Objects.equals(transactionValue, that.transactionValue) && Objects.equals(created, that.created) && Objects.equals(updated, that.updated) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sellerId, buyerId, postId, transactionValue, created, updated, status);
    }

    public TransactionsDto toDTO() {
        return new TransactionsDto(sellerId, buyerId, postId, transactionValue, status);
    }

    public InternalTransactionsDto toInternalDTO() {
        return new InternalTransactionsDto(id, transactionValue, bankNumber);
    }
}

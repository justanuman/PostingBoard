package com.postingBoard.entity;

import com.postingBoard.dto.ChatMessagesDto;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "chat_messages", schema = "posting_board_db")
public class ChatMessage {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID")
    private Integer id;
    @Basic
    @Column(name = "author_ID")
    private Integer authorId;


    @Basic
    @Column(name = "recepient_ID")
    private Integer recepientId;
    @Basic
    @Column(name = "contents")
    private String contents;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "send", columnDefinition = "default current_timestamp")
    private Date send;

    @Basic
    @Column(name = "status")
    private String status;

    public Integer getRecepientId() {
        return recepientId;
    }

    public void setRecepientId(Integer recepientId) {
        this.recepientId = recepientId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Date getSend() {
        return send;
    }

    public void setSend(Date send) {
        this.send = send;
    }

    public void setSend(Timestamp send) {
        this.send = send;
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
        ChatMessage that = (ChatMessage) o;
        return Objects.equals(id, that.id) && Objects.equals(authorId, that.authorId) && Objects.equals(contents, that.contents) && Objects.equals(send, that.send) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, authorId, contents, send, status);
    }

    public ChatMessagesDto toDTO() {
        return new ChatMessagesDto(authorId, recepientId, contents, send);
    }
}

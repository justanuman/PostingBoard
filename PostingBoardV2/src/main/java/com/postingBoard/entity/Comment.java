package com.postingBoard.entity;

import com.postingBoard.dto.CommentsDto;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "comments", schema = "posting_board_db")
public class Comment {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID")
    private Integer id;
    @Basic
    @Column(name = "author_ID")
    private Integer authorId;
    @Basic
    @Column(name = "post_ID")
    private Integer postId;
    @Basic
    @Column(name = "contents")
    private String contents;
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

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
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
        Comment comments = (Comment) o;
        return Objects.equals(id, comments.id) && Objects.equals(authorId, comments.authorId) && Objects.equals(postId, comments.postId) && Objects.equals(contents, comments.contents) && Objects.equals(created, comments.created) && Objects.equals(updated, comments.updated) && Objects.equals(status, comments.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, authorId, postId, contents, created, updated, status);
    }

    public CommentsDto toCommentsDto() {
        return new CommentsDto(authorId, postId, contents, id);
    }
}

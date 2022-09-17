package com.postingBoard.entity;

import com.postingBoard.dto.PostsDto;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "posts", schema = "posting_board_db")
public class Post {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID")
    private Integer id;
    @Basic
    @Column(name = "title")
    private String title;
    @Basic
    @Column(name = "category")
    private String category;
    @Basic
    @Column(name = "author_ID")
    private Integer authorId;
    @Basic
    @Column(name = "price")
    private BigDecimal price;
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
    @Column(name = "rating")
    private Integer rating;
    @Basic
    @Column(name = "status")
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
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
        Post posts = (Post) o;
        return Objects.equals(id, posts.id) && Objects.equals(title, posts.title) && Objects.equals(category, posts.category) && Objects.equals(authorId, posts.authorId) && Objects.equals(price, posts.price) && Objects.equals(contents, posts.contents) && Objects.equals(created, posts.created) && Objects.equals(updated, posts.updated) && Objects.equals(rating, posts.rating) && Objects.equals(status, posts.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, category, authorId, price, contents, created, updated, rating, status);
    }

    public PostsDto toPostsDTO() {
        return new PostsDto(id, title, category, authorId, price, contents, created);
    }
}

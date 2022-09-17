package com.postingBoard.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class PostsInputDto implements Serializable {

    private String title;
    private String category;
    private Integer authorId;
    private BigDecimal price;
    private String contents;

    public PostsInputDto(Integer id, String title, String category, Integer authorId, BigDecimal price, String contents) {

        this.title = title;
        this.category = category;
        this.authorId = authorId;
        this.price = price;
        this.contents = contents;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostsInputDto entity = (PostsInputDto) o;
        return
                Objects.equals(this.title, entity.title) &&
                        Objects.equals(this.category, entity.category) &&
                        Objects.equals(this.authorId, entity.authorId) &&
                        Objects.equals(this.price, entity.price) &&
                        Objects.equals(this.contents, entity.contents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, category, authorId, price, contents);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "title = " + title + ", " +
                "category = " + category + ", " +
                "authorId = " + authorId + ", " +
                "price = " + price + ", " +
                "contents = " + contents + ")";
    }
}

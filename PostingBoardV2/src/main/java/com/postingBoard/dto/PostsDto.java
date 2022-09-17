package com.postingBoard.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class PostsDto implements Serializable {
    private final Integer id;
    private final String title;
    private final String category;
    private final Integer authorId;
    private final BigDecimal price;
    private final String contents;
    private final Date created;

    public PostsDto(Integer id, String title, String category, Integer authorId, BigDecimal price, String contents, Date created) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.authorId = authorId;
        this.price = price;
        this.contents = contents;
        this.created = created;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getContents() {
        return contents;
    }

    public Date getCreated() {
        return created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostsDto entity = (PostsDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.title, entity.title) &&
                Objects.equals(this.category, entity.category) &&
                Objects.equals(this.authorId, entity.authorId) &&
                Objects.equals(this.price, entity.price) &&
                Objects.equals(this.contents, entity.contents) &&
                Objects.equals(this.created, entity.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, category, authorId, price, contents, created);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "title = " + title + ", " +
                "category = " + category + ", " +
                "authorId = " + authorId + ", " +
                "price = " + price + ", " +
                "contents = " + contents + ", " +
                "created = " + created + ")";
    }
}

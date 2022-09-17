package com.postingBoard.dto;

import java.io.Serializable;
import java.util.Objects;

public class CommentsDto implements Serializable {
    private Integer authorId;
    private Integer postId;
    private String contents;
    private Integer commID;

    public CommentsDto(Integer authorId, Integer postId, String contents, Integer commID) {
        this.authorId = authorId;
        this.postId = postId;
        this.contents = contents;
        this.commID = commID;
    }

    public void setCommID(int commID) {
        this.commID = commID;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentsDto entity = (CommentsDto) o;
        return Objects.equals(this.authorId, entity.authorId) &&
                Objects.equals(this.postId, entity.postId) &&
                Objects.equals(this.contents, entity.contents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorId, postId, contents);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + commID +
                "authorId = " + authorId + ", " +
                "postId = " + postId + ", " +
                "contents = " + contents + ")";
    }
}

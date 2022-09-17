package com.postingBoard.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class ChatMessagesDto implements Serializable {
    private final Integer authorId;
    private final Integer recepientId;
    private final String contents;
    private final Date send;

    public ChatMessagesDto(Integer authorId, Integer recepientId, String contents, Date send) {
        this.authorId = authorId;
        this.recepientId = recepientId;
        this.contents = contents;
        this.send = send;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public Integer getRecepientId() {
        return recepientId;
    }

    public String getContents() {
        return contents;
    }

    public Date getSend() {
        return send;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatMessagesDto entity = (ChatMessagesDto) o;
        return Objects.equals(this.authorId, entity.authorId) &&
                Objects.equals(this.recepientId, entity.recepientId) &&
                Objects.equals(this.contents, entity.contents) &&
                Objects.equals(this.send, entity.send);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorId, recepientId, contents, send);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "authorId = " + authorId + ", " +
                "recepientId = " + recepientId + ", " +
                "contents = " + contents + ", " +
                "send = " + send + ")";
    }
}

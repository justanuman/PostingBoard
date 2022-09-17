package com.postingBoard.repo;

import com.postingBoard.entity.ChatMessage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMessageDAO extends CrudRepository<ChatMessage, Integer> {
    List<ChatMessage> findByAuthorIdAndRecepientId(int author, int recepient);

    List<ChatMessage> findByRecepientId(int recepient);

    @Query("select c from ChatMessage c where c.authorId = ?1")
    List<ChatMessage> findByAuthorId(int author);

    @Query("select c from ChatMessage c where c.authorId = ?1 or c.recepientId = ?2")
    List<ChatMessage> findByAuthorIdOrRecepientId(int author, int recepient);
}

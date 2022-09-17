package com.postingBoard.repo;

import com.postingBoard.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IPostDAO extends PagingAndSortingRepository<Post, Integer> {
    //, Sort.by("rating")
    @Query("select p from Post p where p.created >= ?1 AND p.status='OPEN'")
    List<Post> findAllByCreatedAfter(Date after, Pageable pageable);

    @Query("select p from Post p where p.category = ?1 AND p.status='OPEN'")
    List<Post> findAllByCategory(String category, Pageable pageable);

    @Query("select p from Post p where p.title = ?1 AND p.status='OPEN'")
    List<Post> findAllByTitle(String title, Pageable pageable);

    @Query("select p from Post p where p.authorId = ?1 AND p.status='OPEN'")
    List<Post> findAllByAuthorId(int id, Pageable pageable);

    @Query("select p from Post p where p.authorId = ?1 and p.status='CLOSED'")
    List<Post> findAuthorHistory(int id, Pageable pageable);

    @Query("select p from Post p where p.id = ?1 and p.authorId = ?2")
    Optional<Post> findByIdAndAuthorId(int id, int authorid);
}

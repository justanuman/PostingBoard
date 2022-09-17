package com.postingBoard.repo;

import com.postingBoard.entity.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICommentDAO extends PagingAndSortingRepository<Comment, Integer> {
    List<Comment> findAllByPostId(int postId, Pageable pageable);
}

package com.postingBoard.service.implementation;

import com.postingBoard.entity.Comment;
import com.postingBoard.repo.ICommentDAO;
import com.postingBoard.repo.IPostDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {
    @Mock
    ICommentDAO commentDAO;
    @Mock
    IPostDAO postDAO;
    @InjectMocks
    CommentService commentService;
    @BeforeEach
    void initUseCase() {
        MockitoAnnotations.openMocks(commentService);
    }
    @Test
    void postNewComment() {
        Comment comm = new Comment();
        comm.setAuthorId(3);
        comm.setContents("test");
        comm.setPostId(3);
        comm.setStatus("OPEN");
        Comment comm2 = new Comment();
        comm2.setAuthorId(3);
        comm2.setContents("test");
        comm2.setPostId(3);
        comm2.setStatus("OPEN");
        comm2.setId(1);
        when(commentDAO.save(comm)).thenReturn(comm2);
        Assertions.assertEquals((commentService.postNewComment(comm.toCommentsDto())).getAuthorId(),comm.getAuthorId());
        Assertions.assertEquals((commentService.postNewComment(comm.toCommentsDto())).getContents(),comm.getContents());

    }

    @Test
    void showComments() {
        List<Comment> comments = new ArrayList<>();
        Comment comm = new Comment();
        comm.setAuthorId(3);
        comm.setContents("test");
        comm.setPostId(3);
        comm.setStatus("OPEN");
        comments.add(comm);
        Pageable page = PageRequest.of(0, 20);
        when(commentDAO.findAllByPostId(3, page)).thenReturn(comments);
        Assertions.assertEquals((commentService.showComments(3,0).get(0).equals(comm.toCommentsDto())),true);
    }

    @Test
    void removeComment() {
        commentService.removeComment(1);
        verify(commentDAO,times(1)).deleteById(1);
    }

    @Test
    void findComment() {
        Comment comm = new Comment();
        comm.setAuthorId(3);
        comm.setContents("test");
        comm.setPostId(3);
        comm.setStatus("OPEN");
        when(commentDAO.findById(3)).thenReturn(Optional.of(comm));
        Assertions.assertEquals(commentService.findComment(3).equals(comm),true);
    }

}
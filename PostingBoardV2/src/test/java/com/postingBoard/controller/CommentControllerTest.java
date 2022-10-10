package com.postingBoard.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.postingBoard.entity.Comment;
import com.postingBoard.dto.CommentsDto;
import com.postingBoard.dto.PostsInputDto;
import com.postingBoard.entity.Post;
import com.postingBoard.entity.DbUser;
import com.postingBoard.service.interfaces.ICommentService;
import com.postingBoard.service.interfaces.IPostService;
import com.postingBoard.service.interfaces.IUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class CommentControllerTest {
    @Mock
    IUserService userService;
    @Mock
    ICommentService commentService;
    @Mock
    IPostService postService;
    @InjectMocks
    CommentController commentController;
    @BeforeEach
    void initUseCase() {
        MockitoAnnotations.openMocks(commentController);
    }
    @Test
    void comment() throws JsonProcessingException {
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("me");
        Post post = new Post();
        PostsInputDto postsDto = new PostsInputDto(0,"test","cat",2, BigDecimal.valueOf(12123.0),"lol");
        post.setAuthorId(postsDto.getAuthorId());
        post.setCategory(postsDto.getCategory());
        post.setContents(postsDto.getContents());
        post.setPrice(postsDto.getPrice());
        post.setTitle(postsDto.getTitle());
        post.setRating(100);
        post.setId(1);
        when(postService.findByID(1)).thenReturn(post);
        DbUser user  = new DbUser();
        user.setId(1);
        when(userService.findByUsername("me")).thenReturn(user);
        Comment comm = new Comment();
        comm.setAuthorId(1);
        comm.setContents("test");
        comm.setPostId(1);
        comm.setStatus("OPEN");
        when(commentService.postNewComment(comm.toCommentsDto())).thenReturn(comm.toCommentsDto());
        Assertions.assertEquals(commentController.comment(1,"test",mockPrincipal),comm.toCommentsDto());
    }

    @Test
    void removeComment() throws JsonProcessingException {
        Comment comm = new Comment();
        comm.setAuthorId(1);
        comm.setContents("test");
        comm.setPostId(1);
        comm.setStatus("OPEN");
        when(commentService.findComment(1)).thenReturn(comm);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString("deleted");
        Assertions.assertEquals(commentController.removeComment(1,1).equals(json),true);
    }

    @Test
    void checkComments() throws JsonProcessingException {
        Post post = new Post();
        PostsInputDto postsDto = new PostsInputDto(0,"test","cat",2, BigDecimal.valueOf(12123.0),"lol");
        post.setAuthorId(postsDto.getAuthorId());
        post.setCategory(postsDto.getCategory());
        post.setContents(postsDto.getContents());
        post.setPrice(postsDto.getPrice());
        post.setTitle(postsDto.getTitle());
        post.setRating(100);
        post.setId(1);
        when(postService.findByID(1)).thenReturn(post);
        Comment comm = new Comment();
        comm.setAuthorId(1);
        comm.setContents("test");
        comm.setPostId(1);
        comm.setStatus("OPEN");
        List<CommentsDto> commentsDtos = new ArrayList<>();
        commentsDtos.add(comm.toCommentsDto());
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(commentsDtos);
        when(commentService.showComments(1,0)).thenReturn(commentsDtos);
        Assertions.assertEquals(commentController.checkComments(1,0),commentsDtos);
    }
}
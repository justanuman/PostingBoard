package com.postingBoard.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.postingBoard.dto.PostsDto;
import com.postingBoard.dto.PostsInputDto;
import com.postingBoard.entity.DbUser;
import com.postingBoard.entity.Post;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostingBoardControllerTest {
    @Mock
    IUserService userService;
    @Mock
    ICommentService commentService;
    @Mock
    IPostService postService;
    @InjectMocks
    PostingBoardController postingBoardController;

    @BeforeEach
    void initUseCase() {
        MockitoAnnotations.openMocks(postingBoardController);
    }

    @Test
    void createNewPost() throws JsonProcessingException {
        DbUser user = new DbUser();
        user.setId(1);
        when(userService.findByUsername("me")).thenReturn(user);
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("me");
        PostsInputDto postsInputDto = new PostsInputDto(0, "title", "category", 1, BigDecimal.TEN, "contents");
        PostsDto postsDto = new PostsDto(1, "title", "category", 1, BigDecimal.TEN, "contents", Date.from(Instant.now()));
        when(postService.createNewPost(1, postsInputDto)).thenReturn(postsDto);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(postsDto);
        Assertions.assertEquals(json, postingBoardController.createNewPost("title", "category", "contents", BigDecimal.TEN, mockPrincipal));
    }

    @Test
    void updatePost() throws JsonProcessingException {
        DbUser user = new DbUser();
        user.setId(1);
        when(userService.findByUsername("me")).thenReturn(user);
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("me");
        PostsInputDto postsInputDto = new PostsInputDto(0, "title", "category", 1, BigDecimal.TEN, "contents");
        PostsDto postsDto = new PostsDto(1, "title", "category", 1, BigDecimal.TEN, "contents", Date.from(Instant.now()));
        Post post = new Post();
        post.setId(1);
        when(postService.findByIDAndAuthor(1, 1)).thenReturn(post);
        when(postService.updatePost(1, postsInputDto)).thenReturn(postsDto);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(postsDto);
        Assertions.assertEquals(json, postingBoardController.updatePost(1, "title", "category", BigDecimal.TEN, "contents", mockPrincipal));
    }

    @Test
    void archivepost() throws JsonProcessingException {
        DbUser user = new DbUser();
        user.setId(1);
        when(userService.findByUsername("me")).thenReturn(user);
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("me");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

        String json = ow.writeValueAsString("archived");
        Assertions.assertEquals(json, postingBoardController.archivepost(1, mockPrincipal));

    }

    @Test
    void testArchivepost() throws JsonProcessingException {

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

        String json = ow.writeValueAsString("archived");
        Assertions.assertEquals(json, postingBoardController.archivepost(1));

    }

    @Test
    void postsFeed() throws JsonProcessingException {
        Pageable page = PageRequest.of(0, 20);
        List<PostsDto> out = new ArrayList<>();
        PostsDto postsDto = new PostsDto(1, "title", "category", 1, BigDecimal.TEN, "contents", Date.from(Instant.now()));
        out.add(postsDto);
        when(postService.showFeedByCategory("category", 0)).thenReturn(out);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(out);
        Assertions.assertEquals(json, postingBoardController.postsFeed(1,0,null,"category",null));

    }

    @Test
    void getHistory()throws JsonProcessingException {
        List<PostsDto> out = new ArrayList<>();
        PostsDto postsDto = new PostsDto(1, "title", "category", 1, BigDecimal.TEN, "contents", Date.from(Instant.now()));
        out.add(postsDto);
        when(postService.showAuthorHistory(1, 0)).thenReturn(out);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

        String json = ow.writeValueAsString(out);
        Assertions.assertEquals(json, postingBoardController.getHistory(1,0));

    }

    @Test
    void promotePost() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString("promoted");
        Assertions.assertEquals(json, postingBoardController.promotePost(1,0));
    }
}
package com.postingBoard.service.implementation;

import com.postingBoard.dto.PostsInputDto;
import com.postingBoard.entity.Post;
import com.postingBoard.entity.DbUser;
import com.postingBoard.repo.ICommentDAO;
import com.postingBoard.repo.IPostDAO;
import com.postingBoard.repo.IUserDAO;
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
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {
    @Mock
    IPostDAO postDAO;
    @Mock
    ICommentDAO commentDAO;
    @Mock
    IUserDAO userDAO;
    @InjectMocks
    PostService postService;
    @BeforeEach
    void initUseCase() {
        MockitoAnnotations.openMocks(postService);
    }

    @Test
    void createNewPost() {
        DbUser user = new DbUser();
        user.setPersonalRating(100);
        when(userDAO.findById(2)).thenReturn(Optional.of(user));
        Post post = new Post();
        PostsInputDto postsDto = new PostsInputDto(0,"test","ct",2,BigDecimal.valueOf(12123.0),"lol");
        post.setAuthorId(postsDto.getAuthorId());
        post.setCategory(postsDto.getCategory());
        post.setContents(postsDto.getContents());
        post.setPrice(postsDto.getPrice());
        post.setTitle(postsDto.getTitle());
        post.setRating(user.getPersonalRating());
        post.setStatus("OPEN");
        when(postDAO.save(post)).thenReturn(post);
        Assertions.assertEquals(postService.createNewPost(2,postsDto).getAuthorId(),2);
    }


    @Test
    void promotePost() {
        Post post = new Post();
        PostsInputDto postsDto = new PostsInputDto(0,"test","ct",2,BigDecimal.valueOf(12123.0),"lol");
        post.setAuthorId(postsDto.getAuthorId());
        post.setCategory(postsDto.getCategory());
        post.setContents(postsDto.getContents());
        post.setPrice(postsDto.getPrice());
        post.setTitle(postsDto.getTitle());
        post.setRating(100);
        when(postDAO.findById(1)).thenReturn(Optional.of(post));
        postService.promotePost(1,100);
        post.setStatus("OPEN");
        verify(postDAO,times(1)).findById(1);
        verify(postDAO,times(1)).save(post);
    }

    @Test
    void updatePost() {
        Post post = new Post();
        PostsInputDto postsDto = new PostsInputDto(0,"test","ct",2,BigDecimal.valueOf(12123.0),"lol");
        post.setAuthorId(postsDto.getAuthorId());
        post.setCategory(postsDto.getCategory());
        post.setContents(postsDto.getContents());
        post.setPrice(postsDto.getPrice());
        post.setTitle(postsDto.getTitle());
        post.setRating(100);
        post.setId(1);
        when(postDAO.findById(1)).thenReturn(Optional.of(post));
        Assertions.assertEquals(postService.updatePost(1,postsDto),post.toPostsDTO());
    }

    @Test
    void findByID() {
        Post post = new Post();
        PostsInputDto postsDto = new PostsInputDto(0,"test","ct",2,BigDecimal.valueOf(12123.0),"lol");
        post.setAuthorId(postsDto.getAuthorId());
        post.setCategory(postsDto.getCategory());
        post.setContents(postsDto.getContents());
        post.setPrice(postsDto.getPrice());
        post.setTitle(postsDto.getTitle());
        post.setRating(100);
        post.setId(1);
        when(postDAO.findById(1)).thenReturn(Optional.of(post));
        Assertions.assertEquals(postService.findByID(1),post);

    }

    @Test
    void findByIDAndAuthor() {
        Post post = new Post();
        PostsInputDto postsDto = new PostsInputDto(0,"test","ct",2,BigDecimal.valueOf(12123.0),"lol");
        post.setAuthorId(postsDto.getAuthorId());
        post.setCategory(postsDto.getCategory());
        post.setContents(postsDto.getContents());
        post.setPrice(postsDto.getPrice());
        post.setTitle(postsDto.getTitle());
        post.setRating(100);
        post.setId(1);
        when(postDAO.findByIdAndAuthorId(1,1)).thenReturn(Optional.of(post));
        Assertions.assertEquals(postService.findByIDAndAuthor(1,1),post);
    }

    @Test
    void showFeed() {
        Post post = new Post();
        PostsInputDto postsDto = new PostsInputDto(0,"test","ct",2,BigDecimal.valueOf(12123.0),"lol");
        post.setAuthorId(postsDto.getAuthorId());
        post.setCategory(postsDto.getCategory());
        post.setContents(postsDto.getContents());
        post.setPrice(postsDto.getPrice());
        post.setTitle(postsDto.getTitle());
        post.setRating(100);
        post.setId(1);
        Date after = new Date();
        Pageable page = PageRequest.of(0, 20, Sort.by("rating").descending());
        List<Post> posts = new ArrayList<>();
        posts.add(post);
        when(postDAO.findAllByCreatedAfter(after, page)).thenReturn(posts);
        Assertions.assertEquals(postService.showFeed(after,0).get(0).equals(post.toPostsDTO()),true);
    }

    @Test
    void showFeedByCategory() {
        Post post = new Post();
        PostsInputDto postsDto = new PostsInputDto(0,"test","cat",2,BigDecimal.valueOf(12123.0),"lol");
        post.setAuthorId(postsDto.getAuthorId());
        post.setCategory(postsDto.getCategory());
        post.setContents(postsDto.getContents());
        post.setPrice(postsDto.getPrice());
        post.setTitle(postsDto.getTitle());
        post.setRating(100);
        post.setId(1);
        Pageable page = PageRequest.of(0, 20, Sort.by("rating"));
        List<Post> posts = new ArrayList<>();
        posts.add(post);
        when(postDAO.findAllByCategory("cat", page)).thenReturn(posts);
        Assertions.assertEquals(postService.showFeedByCategory("cat",0).get(0).equals(post.toPostsDTO()),true);
    }

    @Test
    void showFeedByAuthor() {
        Post post = new Post();
        PostsInputDto postsDto = new PostsInputDto(0,"test","cat",2,BigDecimal.valueOf(12123.0),"lol");
        post.setAuthorId(postsDto.getAuthorId());
        post.setCategory(postsDto.getCategory());
        post.setContents(postsDto.getContents());
        post.setPrice(postsDto.getPrice());
        post.setTitle(postsDto.getTitle());
        post.setRating(100);
        post.setId(1);
        Pageable page = PageRequest.of(0, 20, Sort.by("rating"));
        List<Post> posts = new ArrayList<>();
        posts.add(post);
        when(postDAO.findAllByAuthorId(2, page)).thenReturn(posts);
        Assertions.assertEquals(postService.showFeedByAuthor(2,0).get(0).equals(post.toPostsDTO()),true);
    }



    @Test
    void archivePost() {
        Post post = new Post();
        PostsInputDto postsDto = new PostsInputDto(0,"test","ct",2,BigDecimal.valueOf(12123.0),"lol");
        post.setAuthorId(postsDto.getAuthorId());
        post.setCategory(postsDto.getCategory());
        post.setContents(postsDto.getContents());
        post.setPrice(postsDto.getPrice());
        post.setTitle(postsDto.getTitle());
        post.setRating(100);
        post.setId(1);
        when(postDAO.findById(1)).thenReturn(Optional.of(post));
        post.setStatus("CLOSED");
        postService.archivePost(1);
        verify(postDAO,times(1)).save(post);
    }
}
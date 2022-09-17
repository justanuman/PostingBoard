package com.postingBoard.service.interfaces;

import com.postingBoard.dto.PostsDto;
import com.postingBoard.dto.PostsInputDto;
import com.postingBoard.entity.Post;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public interface IPostService {


    @PreAuthorize(" hasRole('ROLE_USER') and #id == authentication.principal.id")
    PostsDto createNewPost(int id, PostsInputDto postsDto);

    @PreAuthorize(" hasRole('ROLE_MODERATOR')  or hasRole('ROLE_ADMIN')or #id == authentication.principal.id")
    void deletePost(Integer id);

    @PreAuthorize(" hasRole('ROLE_MODERATOR')  or hasRole('ROLE_ADMIN')")
    void promotePost(Integer id, int score);


    @PreAuthorize("hasRole('ROLE_USER')")
    PostsDto updatePost(int post, PostsInputDto postsDto);

    Post findByID(int id);


    Post findByIDAndAuthor(int id, int author);

    List<PostsDto> showFeed(Date after, int pageNum);

    List<PostsDto> showFeedByCategory(String category, int pageNum);


    List<PostsDto> showFeedByAuthor(int id, int pageNum);

    List<PostsDto> showAuthorHistory(int id, int pageNum);

    void checkForNull(Object object);

    @PreAuthorize(" hasRole('ROLE_MODERATOR')  or hasRole('ROLE_ADMIN')")
    void archivePost(int id);

    @PreAuthorize(" hasRole('ROLE_MODERATOR')  or hasRole('ROLE_ADMIN')")
    void archivePostAdmin(int id);
}

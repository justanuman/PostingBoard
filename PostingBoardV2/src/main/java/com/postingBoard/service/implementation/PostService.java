package com.postingBoard.service.implementation;


import com.postingBoard.dto.PostsDto;
import com.postingBoard.dto.PostsInputDto;
import com.postingBoard.entity.DbUser;
import com.postingBoard.entity.Post;
import com.postingBoard.repo.IPostDAO;
import com.postingBoard.repo.IUserDAO;
import com.postingBoard.service.interfaces.IPostService;
import com.postingBoard.utility.Mappers.PostsDtoAdapter;
import com.postingBoard.utility.Mappers.UserDtoAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@Transactional
public class PostService implements IPostService {
    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    @Autowired
    IPostDAO postDAO;

    @Autowired
    IUserDAO userDAO;


    UserDtoAdapter userDtoAdapter = new UserDtoAdapter();


    PostsDtoAdapter postsDtoAdapter = new PostsDtoAdapter();

    @Override
    @PreAuthorize(" hasRole('ROLE_USER') and #id == authentication.principal.id")
    public PostsDto createNewPost(int id, PostsInputDto postsDto) {
        Post post = new Post();
        DbUser user = userDAO.findById(postsDto.getAuthorId()).orElse(null);
        post.setAuthorId(postsDto.getAuthorId());
        post.setCategory(postsDto.getCategory());
        post.setContents(postsDto.getContents());
        post.setPrice(postsDto.getPrice());
        post.setTitle(postsDto.getTitle());
        post.setRating(user.getPersonalRating());
        post.setStatus("OPEN");
        if (postsDto.getCategory().equals("PROMOTION") || postsDto.getTitle().equals("BUY POST PROMOTION") || postsDto.getTitle().equals("BUY USER PROMOTION")) {//TODO
            logger.debug("wrong category");
            throw new AccessDeniedException("wrong cat");
        } else {
            post = postDAO.save(post);
        }
        logger.info("new post by {}", id);
        // return post.toPostsDTO();
        return postsDtoAdapter.modelToDto(post);
    }

    @Override
    @Deprecated
    @PreAuthorize(" hasRole('ROLE_MODERATOR')  or hasRole('ROLE_ADMIN')or #id == authentication.principal.id")
    public void deletePost(Integer id) {
        logger.info(" post  {} deleted", id);
        postDAO.deleteById(id);
    }

    @Override
    @PreAuthorize(" hasRole('ROLE_MODERATOR')  or hasRole('ROLE_ADMIN')")
    public void promotePost(Integer id, int score) {
        Post post = postDAO.findById(id).orElse(null);
        if (post == null) {
            throw new ResponseStatusException(NOT_FOUND);
        }
        int newScore = post.getRating() + score;
        post.setRating(newScore);
        logger.info("post {} promoted by {} points", id, score);
        postDAO.save(post);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    public PostsDto updatePost(int postid, PostsInputDto postsDto) {
        Post post = postDAO.findById(postid).orElse(null);
        if (postsDto.getCategory() != null) {
            post.setCategory(postsDto.getCategory());
            //postsDto.getCategory();
        }
        if (postsDto.getContents() != null) {
            post.setContents(postsDto.getContents());
        }
        if (postsDto.getPrice() != null) {
            post.setPrice(postsDto.getPrice());
        }
        if (postsDto.getTitle() != null) {
            post.setTitle(postsDto.getTitle());
        }
        postDAO.save(post);
        logger.info("post {} updated ", postid);
        //return post.toPostsDTO();
        return postsDtoAdapter.modelToDto(post);
    }

    @Override
    public Post findByID(int id) {
        logger.info("looking for post {} ", id);
        return postDAO.findById(id).orElse(null);
    }

    @Override
    public Post findByIDAndAuthor(int id, int author) {
        logger.info("looking for post {} ", id);
        return postDAO.findByIdAndAuthorId(id, author).orElse(null);
    }


    @Override
    public List<PostsDto> showFeed(Date after, int pageNum) {
        Pageable page = PageRequest.of(pageNum, 20, Sort.by("rating").descending());
        List<Post> posts = postDAO.findAllByCreatedAfter(after, page);
        List<PostsDto> out = new ArrayList<>();
        for (Post post : posts) {
            //out.add(post.toPostsDTO());
            out.add(postsDtoAdapter.modelToDto(post));
        }
        logger.info("showing feed date {} , page {}", after, pageNum);
        return out;
    }

    @Override
    public List<PostsDto> showFeedByCategory(String category, int pageNum) {
        Pageable page = PageRequest.of(pageNum, 20, Sort.by("rating"));
        List<Post> posts = postDAO.findAllByCategory(category, page);
        List<PostsDto> out = new ArrayList<>();
        for (Post post : posts) {
            out.add(postsDtoAdapter.modelToDto(post));
            //out.add(post.toPostsDTO());
        }
        logger.info("showing feed by category {}, page {}", category, pageNum);
        return out;
    }


    @Override
    public List<PostsDto> showFeedByAuthor(int id, int pageNum) {
        Pageable page = PageRequest.of(pageNum, 20, Sort.by("rating"));
        List<Post> posts = postDAO.findAllByAuthorId(id, page);
        List<PostsDto> out = new ArrayList<>();
        for (Post post : posts) {
            out.add(postsDtoAdapter.modelToDto(post));
            //out.add(post.toPostsDTO());
        }
        logger.info("showing feed by author {}, page {}", id, pageNum);
        return out;
    }

    @Override
    public List<PostsDto> showAuthorHistory(int id, int pageNum) {
        Pageable page = PageRequest.of(pageNum, 20);
        List<Post> posts = postDAO.findAuthorHistory(id, page);
        List<PostsDto> out = new ArrayList<>();
        for (Post post : posts) {
            out.add(postsDtoAdapter.modelToDto(post));
            // out.add(post.toPostsDTO());
        }
        logger.info("showing history of author {}, page {}", id, pageNum);
        return out;
    }

    @Override
    public void checkForNull(Object object) {
        if (object.equals(null)) {
            throw new ResponseStatusException(NOT_FOUND, "Unable to find resource");
        }
    }

    @Override
    //  @PreAuthorize(" hasRole('ROLE_MODERATOR')  or hasRole('ROLE_ADMIN')")
    public void archivePost(int id) {
        Post post = postDAO.findById(id).orElse(null);
        post.setStatus("CLOSED BY USER");
        postDAO.save(post);
        logger.info("archiving post {}", id);
    }

    @Override
    @PreAuthorize(" hasRole('ROLE_MODERATOR')  or hasRole('ROLE_ADMIN')")
    public void archivePostAdmin(int id) {
        Post post = postDAO.findById(id).orElse(null);
        post.setStatus("CLOSED BY ADMIN");
        postDAO.save(post);
        logger.info("archiving post {}", id);
    }


}

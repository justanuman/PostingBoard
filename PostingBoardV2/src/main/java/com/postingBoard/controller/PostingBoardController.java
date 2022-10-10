package com.postingBoard.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.postingBoard.dto.PostsDto;
import com.postingBoard.dto.PostsInputDto;
import com.postingBoard.entity.Post;
import com.postingBoard.service.interfaces.ICommentService;
import com.postingBoard.service.interfaces.IPostService;
import com.postingBoard.service.interfaces.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Controller
public class PostingBoardController {
    private static final Logger logger = LoggerFactory.getLogger(PostingBoardController.class);
    @Autowired
    IUserService userService;
    @Autowired
    ICommentService commentService;
    @Autowired
    IPostService postService;

    @PostMapping(
            value = "/user/createnewpost",
            produces = "application/json"
    )
    @ResponseBody
    public String createNewPost(@RequestParam String title,
                                @RequestParam String category,
                                @RequestParam String contents,
                                @RequestParam BigDecimal price, Principal principal) throws JsonProcessingException {
        int id = userService.findByUsername(principal.getName()).getId();
        PostsInputDto postsInputDto = new PostsInputDto(0, title, category, id, price, contents);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        PostsDto postsDto = postService.createNewPost(id, postsInputDto);
        String json = ow.writeValueAsString(postsDto);
        return json;
    }

    @PutMapping(
            value = "/user/updatepost/{post}",
            produces = "application/json"
    )
    @ResponseBody
    public PostsDto updatePost(@PathVariable int post,
                               @RequestParam(required = false) String title,
                               @RequestParam(required = false) String category,
                               @RequestParam(required = false) BigDecimal price,
                               @RequestParam(required = false) String contents, Principal principal) throws JsonProcessingException {
        int user = userService.findByUsername(principal.getName()).getId();
        Post posts = postService.findByIDAndAuthor(post, user);
        postService.checkForNull(posts);
        PostsInputDto updated = new PostsInputDto(posts.getId(), title, category, user, price, contents);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        // if (user != null && posts != null && posts.getAuthorId().equals(user.getId())) {
        PostsDto postsDto = postService.updatePost(posts.getId(), updated);
        return postsDto;
    }

    @DeleteMapping(
            value = "/user/archivepost/{postid}",
            produces = "application/json"
    )
    @ResponseBody
    public String archivepost(@PathVariable int postid, Principal principal) throws JsonProcessingException {
        int user = userService.findByUsername(principal.getName()).getId();
        Post posts = postService.findByIDAndAuthor(postid, user);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        postService.checkForNull(posts);
        postService.archivePost(Integer.valueOf(postid));
        String json = ow.writeValueAsString("archived");
        return json;

    }

    @DeleteMapping(
            value = "/archivepost/{postid}",
            produces = "application/json"
    )
    @ResponseBody
    public String archivepost(@PathVariable int postid) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        postService.archivePost(Integer.valueOf(postid));
        String json = ow.writeValueAsString("archived");
        return json;

    }

    @GetMapping(
            value = "/board/postsfeed",
            produces = "application/json"
    )
    @ResponseBody
    public List<PostsDto> postsFeed(@RequestParam int choice, @RequestParam int page, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd-hh-mm-ss") Date date, @RequestParam(required = false) String category, @RequestParam(required = false) Integer authorID) throws JsonProcessingException {
        List<PostsDto> out;
        if (choice == 0) {
            out = postService.showFeed(date, page);
            return out;
            /*String json = ow.writeValueAsString(out);
            return json;*/
        }
        if (choice == 1) {
            out = postService.showFeedByCategory(category, page);
            return out;
           /* String json = ow.writeValueAsString(out);
            return json;*/
        }
        if (choice == 2) {
            out = postService.showFeedByAuthor(authorID, page);
            return out;
           /* String json = ow.writeValueAsString(out);
            return json;*/
        }
        if (choice == 3) {
            out = postService.showFeed(Date.from(Instant.now().minusSeconds(2628000)), page);
            return out;
           /* String json = ow.writeValueAsString(out);
            return json;*/
        }
        return null;
    }

    @GetMapping(
            value = "/user/{id}/history",
            produces = "application/json"
    )
    @ResponseBody
    public List<PostsDto> getHistory(@PathVariable int id, @RequestParam int page) throws JsonProcessingException {
        List<PostsDto> out = postService.showAuthorHistory(id, page);
        return out;
    }

    @PutMapping(
            value = "/board/{id}/promote",
            produces = "application/json"
    )
    @ResponseBody
    public String promotePost(@PathVariable int id, @RequestParam int score) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        postService.promotePost(id, score);
        String json = ow.writeValueAsString("promoted");
        return json;

    }

}

package com.postingBoard.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.postingBoard.dto.CommentsDto;
import com.postingBoard.entity.Comment;
import com.postingBoard.entity.Post;
import com.postingBoard.service.interfaces.ICommentService;
import com.postingBoard.service.interfaces.IPostService;
import com.postingBoard.service.interfaces.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
    @Autowired
    IUserService userService;
    @Autowired
    ICommentService commentService;
    @Autowired
    IPostService postService;

    @PostMapping(
            value = "/board/{post}/comment",
            produces = "application/json"
    )
    @ResponseBody
    public String comment(@PathVariable int post, @RequestParam String content, Principal principal) throws JsonProcessingException {
        Post posts = postService.findByID(post);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        postService.checkForNull(posts);
        CommentsDto commentsDto = new CommentsDto(userService.findByUsername(principal.getName()).getId(), post, content, null);
        commentsDto = commentService.postNewComment(commentsDto);
        String json = ow.writeValueAsString(commentsDto);
        return json;

    }

    @DeleteMapping(
            value = "/board/{post}/removeComment/{commID}",
            produces = "application/json"
    )
    @ResponseBody
    public String removeComment(@PathVariable int post, @PathVariable int commID) throws JsonProcessingException {
        // Posts posts = postService.findByID(post);
        Comment comments = commentService.findComment(commID);
        commentService.checkForNull(comments);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        commentService.removeComment(commID);
        String json = ow.writeValueAsString("deleted");
        return json;
    }

    @GetMapping(
            value = "/board/{post}/checkcomments",
            produces = "application/json"
    )
    @ResponseBody
    public String checkComments(@PathVariable int post, @RequestParam int page) throws JsonProcessingException {
        Post posts = postService.findByID(post);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        postService.checkForNull(posts);
        List<CommentsDto> comments = commentService.showComments(post, page);
        String json = ow.writeValueAsString(comments);
        return json;
    }
}


package com.postingBoard.service.implementation;

import com.postingBoard.dto.CommentsDto;
import com.postingBoard.entity.Comment;
import com.postingBoard.repo.ICommentDAO;
import com.postingBoard.service.interfaces.ICommentService;
import com.postingBoard.utility.Mappers.CommentsDtoAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@Transactional
public class CommentService implements ICommentService {
    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);

    @Autowired
    ICommentDAO commentDAO;


    CommentsDtoAdapter commentsDtoAdapter = new CommentsDtoAdapter();

    @Override
    @PreAuthorize(" hasRole('ROLE_USER')")
    public CommentsDto postNewComment(CommentsDto input) {
        Comment comm = new Comment();
        comm.setAuthorId(input.getAuthorId());
        comm.setContents(input.getContents());
        comm.setPostId(input.getPostId());
        comm.setStatus("OPEN");
        comm = commentDAO.save(comm);
        // input.setCommID(comm.getId());
        input = commentsDtoAdapter.modelToDto(comm);
        logger.info("new comment by {}", input.getAuthorId());
        return input;
    }

    @Override
    public List<CommentsDto> showComments(int postID, int pageNum) {
        Pageable page = PageRequest.of(pageNum, 20);
        List<Comment> comments = commentDAO.findAllByPostId(postID, page);
        List<CommentsDto> out = new ArrayList<>();
        for (Comment comment : comments) {
            //out.add(comment.toCommentsDto());
            out.add(commentsDtoAdapter.modelToDto(comment));
        }
        logger.info("checked comments");
        return out;
    }

    @Override
    @PreAuthorize(" hasRole('ROLE_MODERATOR')  or hasRole('ROLE_ADMIN') or #id == authentication.principal.id")
    public void removeComment(int commID) {
        logger.info("deleted comment id:{}", commID);
        commentDAO.deleteById(commID);
    }

    @Override
    public Comment findComment(int id) {
        logger.info("looking for  Comment id:{}", id);
        return commentDAO.findById(id).orElse(null);
    }

    @Override
    public void checkForNull(Object object) {
        if (object.equals(null)) {
            throw new ResponseStatusException(NOT_FOUND, "Unable to find resource");
        }
    }
}

package com.postingBoard.service.interfaces;

import com.postingBoard.dto.CommentsDto;
import com.postingBoard.entity.Comment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ICommentService {

    @PreAuthorize(" hasRole('ROLE_USER')")
    CommentsDto postNewComment(CommentsDto comments);


    List<CommentsDto> showComments(int postID, int pageNum);


    @PreAuthorize(" hasRole('ROLE_MODERATOR')  or hasRole('ROLE_ADMIN')")
    void removeComment(int commID);

    Comment findComment(int id);

    void checkForNull(Object object);
}

package com.postingBoard.utility.Mappers;

import com.postingBoard.dto.CommentsDto;
import com.postingBoard.entity.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentsDtoAdapter implements Adapter<Comment, CommentsDto> {
    @Override
    public  CommentsDto modelToDto(Comment model) {
        return new CommentsDto(model.getAuthorId(), model.getPostId(), model.getContents(), model.getId());
    }

    @Override
    public Comment dtoToModel(CommentsDto dto) {
        return Adapter.super.dtoToModel(dto);
    }
}

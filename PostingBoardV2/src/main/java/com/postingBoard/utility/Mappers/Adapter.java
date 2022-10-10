package com.postingBoard.utility.Mappers;

import com.postingBoard.dto.CommentsDto;
import com.postingBoard.entity.Comment;
import org.springframework.stereotype.Component;

@Component
public interface Adapter<M, D> {
    default   D modelToDto(M model) {
        throw new UnsupportedOperationException();
    }


    default M dtoToModel(D dto) {
        throw new UnsupportedOperationException();
    }
}

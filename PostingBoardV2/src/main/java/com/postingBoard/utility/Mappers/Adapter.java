package com.postingBoard.utility.Mappers;

import com.postingBoard.dto.CommentsDto;
import com.postingBoard.entity.Comment;

public interface Adapter<M, D> {
    default   D modelToDto(M model) {
        throw new UnsupportedOperationException();
    }


    default M dtoToModel(D dto) {
        throw new UnsupportedOperationException();
    }
}

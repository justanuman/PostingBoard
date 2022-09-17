package com.postingBoard.utility.Mappers;

import com.postingBoard.dto.PostsInputDto;
import com.postingBoard.entity.Post;
import org.springframework.stereotype.Component;

@Component
public class PostInputDtoAdapter implements Adapter<Post, PostsInputDto> {
    @Override
    public PostsInputDto modelToDto(Post model) {
        return new PostsInputDto(model.getId(), model.getTitle(), model.getCategory(), model.getAuthorId(), model.getPrice(), model.getContents());
    }

    @Override
    public Post dtoToModel(PostsInputDto dto) {
        return Adapter.super.dtoToModel(dto);
    }
}

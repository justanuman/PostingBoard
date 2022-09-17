package com.postingBoard.utility.Mappers;


import com.postingBoard.dto.PostsDto;
import com.postingBoard.entity.Post;
import org.springframework.stereotype.Component;

@Component
public class PostsDtoAdapter implements Adapter<Post, PostsDto> {
    @Override
    public PostsDto modelToDto(Post model) {
        return new PostsDto(model.getId(), model.getTitle(), model.getCategory(), model.getAuthorId(), model.getPrice(), model.getContents(), model.getCreated());
    }

    @Override
    public Post dtoToModel(PostsDto dto) {
        return Adapter.super.dtoToModel(dto);
    }
}

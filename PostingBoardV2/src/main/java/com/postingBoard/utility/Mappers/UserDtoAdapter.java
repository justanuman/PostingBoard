package com.postingBoard.utility.Mappers;

import com.postingBoard.dto.UserDto;
import com.postingBoard.entity.DbUser;
import org.springframework.stereotype.Component;

@Component
public class UserDtoAdapter implements Adapter<DbUser, UserDto> {
    @Override
    public UserDto modelToDto(DbUser model) {

        UserDto userDTO = new UserDto(model.getUsername(), model.getFirstName(), model.getLastName(), model.getPhoneNumber(), model.getEmail(), model.getPersonalRating());
        userDTO.setId(model.getId());
        return userDTO;
    }

    @Override
    public DbUser dtoToModel(UserDto dto) {
        return Adapter.super.dtoToModel(dto);
    }
}

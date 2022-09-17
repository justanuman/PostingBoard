package com.postingBoard.utility.Mappers;


import com.postingBoard.dto.UserProfileDto;
import com.postingBoard.entity.DbUser;
import org.springframework.stereotype.Component;

@Component
public class UserProfileDtoAdapter implements Adapter<DbUser, UserProfileDto> {
    @Override
    public UserProfileDto modelToDto(DbUser model) {
      return new UserProfileDto(model.getUsername(), model.getFirstName(), model.getLastName(), model.getPassword(), model.getStatus(), model.getPhoneNumber(), model.getBankNumber(), model.getEmail());
    }

    @Override
    public DbUser dtoToModel(UserProfileDto dto) {
        return Adapter.super.dtoToModel(dto);
    }
}

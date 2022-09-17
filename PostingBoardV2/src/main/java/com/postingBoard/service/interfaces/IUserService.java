package com.postingBoard.service.interfaces;


import com.postingBoard.dto.UserDto;
import com.postingBoard.dto.UserProfileDto;
import com.postingBoard.entity.DbUser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface IUserService {

    DbUser register(DbUser user);

    List<DbUser> getAll();

    DbUser findByUsername(String username);

    DbUser findById(int id);

    void delete(int id);

    @PreAuthorize("hasRole(ROLE_USER) and #id == authentication.principal.id")
    UserDto updateProfile(Integer id, UserProfileDto userProfileDto, DbUser user);

    Boolean isUser(DbUser user);

    Boolean isModerator(DbUser user);

    Boolean isAdmin(DbUser user);


   /* @PreAuthorize("hasRole(ROLE_ADMIN) or hasRole(ROLE_MODERATOR)")
    UserDTO deactivateUser(int id,  Principal principal);*/

    @PreAuthorize("hasRole(ROLE_ADMIN)")
    UserDto deactivateModerator(int id, DbUser user);

    @PreAuthorize("hasRole(ROLE_ADMIN) or hasRole(ROLE_MODERATOR)")
    UserDto activateUser(DbUser user);

    @PreAuthorize("hasRole(ROLE_ADMIN) or hasRole(ROLE_MODERATOR)")
    UserDto promoteUser(DbUser user);

    @PreAuthorize("hasRole(ROLE_ADMIN) or hasRole(ROLE_MODERATOR)")
    UserDto changeUserRating(DbUser user, int score);

    UserDto payForRating(DbUser users, int score);

    void checkForNull(Object object);
}
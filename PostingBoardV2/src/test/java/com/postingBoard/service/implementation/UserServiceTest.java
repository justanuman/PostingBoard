package com.postingBoard.service.implementation;

import com.postingBoard.dto.UserProfileDto;
import com.postingBoard.entity.DbUser;
import com.postingBoard.repo.IRoleDAO;
import com.postingBoard.repo.IUserDAO;
import com.postingBoard.repo.IUserRoleDAO;
import com.postingBoard.utility.Mappers.UserDtoAdapter;
import com.postingBoard.utility.Mappers.UserProfileDtoAdapter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
 IUserDAO userDAO;
    @Mock
 IRoleDAO roleDAO;
    @Mock
 IUserRoleDAO userRoleDAO;
    @Mock
    UserDtoAdapter userDtoAdapter;
    @Mock
    UserProfileDtoAdapter userProfileDtoAdapter;
    @InjectMocks
    UserService userService;
    @BeforeEach
    void initUseCase() {
        MockitoAnnotations.openMocks(userService);
    }
    @Test
    void register() {
    }

    @Test
    void getAll() {
        List<DbUser> users = new ArrayList<>();
        when(userDAO.findAll()).thenReturn(users);
        Assertions.assertEquals(userService.getAll().equals(users),true);

    }

    @Test
    void findByUsername() {
        DbUser user =new DbUser();
        when(userDAO.findByUsername("d")).thenReturn(user);
        Assertions.assertEquals(userService.findByUsername("d").equals(user),true);
    }

    @Test
    void findById() {
        DbUser user =new DbUser();
        when(userDAO.findById(1)).thenReturn(Optional.of(user));
        Assertions.assertEquals(userService.findById(1).equals(user),true);
    }


    @Test
    void updateProfile() {
        UserProfileDto userProfileDto = new UserProfileDto("lol",null,null,null,null,null,null,null);
        DbUser user =new DbUser();
        user.setUsername("lol");
        when(userDAO.save(user)).thenReturn(user);
        Assertions.assertEquals(userService.updateProfile(1,userProfileDto,user).getUsername(),"lol");
    }

    @Test
    void deactivateModerator() {

        DbUser user =new DbUser();
        user.setUsername("l");
        user.setId(1);
        user.setPassword("ll");
        Assertions.assertEquals(userService.deactivateModerator(1,user).getUsername(),"l");
    }

  /*  @Test
    void activateUser() {
    }*/

    @Test
    void promoteUser() {
    }

    @Test
    void changeUserRating() {
    }

    @Test
    void payForRating() {
        DbUser users = new DbUser();
        users.setPersonalRating(100);
        Assertions.assertEquals(userService.payForRating(users,100).getPersonalRating(),200);


    }

}
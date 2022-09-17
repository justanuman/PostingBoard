package com.postingBoard.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.postingBoard.dto.UserDto;
import com.postingBoard.dto.UserProfileDto;
import com.postingBoard.entity.DbUser;
import com.postingBoard.service.interfaces.ICommentService;
import com.postingBoard.service.interfaces.IPostService;
import com.postingBoard.service.interfaces.ITransactionService;
import com.postingBoard.service.interfaces.IUserService;
import com.postingBoard.utility.Mappers.UserDtoAdapter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserProfileControllerTest {
    @Mock
    IUserService userService;

    @InjectMocks
    UserProfileController userProfileController;
    @BeforeEach
    void initUseCase() {
        MockitoAnnotations.openMocks(userProfileController);
    }
    @Test
    void getUserInfo() throws JsonProcessingException {
        DbUser user = new DbUser();
        user.setId(1);
        user.setUsername("me");
        user.setPassword("token");
        user.setEmail("ll");
        when(userService.findById(1)).thenReturn(user);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(user.touserDTO());
        Assertions.assertEquals(userProfileController.getUserInfo(1),json);
    }

    @Test
    void editProfileInfo() throws JsonProcessingException {
        DbUser user = new DbUser();
        user.setId(1);
        user.setUsername("me");
        user.setPassword("token");
        user.setEmail("ll");
        UserDtoAdapter userDtoAdapter = new UserDtoAdapter();
        UserDto userDTO = userDtoAdapter.modelToDto(user);
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("me");
        UserProfileDto userProfileDto = new UserProfileDto("me", null, null, null, null, null, null, null);

        when(userService.findByUsername("me")).thenReturn(user);
//        when(userService.findById(1)).thenReturn(user);
        when( userService.updateProfile(user.getId(), userProfileDto, user)).thenReturn(userDTO);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(userDTO);
        Assertions.assertEquals(userProfileController.editProfileInfo("me",null,null,null,null,null,null,null,mockPrincipal),json);
    }

    @Test
    void promoteUser() throws JsonProcessingException {
        DbUser user = new DbUser();
        user.setId(1);
        user.setUsername("me");
        user.setPassword("token");
        user.setEmail("ll");
        when(userService.findById(1)).thenReturn(user);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(user.touserDTO());
        Assertions.assertEquals(userProfileController.promoteUser(1),json);
        verify(userService, times(1)).promoteUser(user);

    }

    @Test
    void getPersonalInfo() throws JsonProcessingException {
        DbUser user = new DbUser();
        user.setId(1);
        user.setUsername("me");
        user.setPassword("token");
        user.setEmail("ll");
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("me");
        when(userService.findByUsername("me")).thenReturn(user);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(user.touserDTO());
        Assertions.assertEquals(userProfileController.getPersonalInfo(mockPrincipal),json);
    }
}
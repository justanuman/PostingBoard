package com.postingBoard.controller;

import com.postingBoard.config.security.JWT.JwtTokenProvider;
import com.postingBoard.entity.DbUser;
import com.postingBoard.service.implementation.UserService;
import com.postingBoard.service.interfaces.IUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserAuthControllerTest {
    @Mock
    IUserService userService;
    @Mock
    JwtTokenProvider jwtTokenProvider;
    @Mock
    AuthenticationManager authenticationManager;
    @InjectMocks
    UserAuthController userAuthController;

    @BeforeEach
    void initUseCase() {
        MockitoAnnotations.openMocks(userAuthController);
    }

    @Test
    void login() {
        DbUser user = new DbUser();
        user.setId(1);
        when(userService.findByUsername("me")).thenReturn(user);
        when( jwtTokenProvider.createToken("me", user.getRoles())).thenReturn("token");
        Map<Object, Object> response = new HashMap<>();
        response.put("username", "me");
        response.put("token", "token");
        Assertions.assertEquals(userAuthController.login("me","token"),ResponseEntity.ok(response));
    }

    @Test
    void register() {
        DbUser user = new DbUser();
       // user.setId(1);
        user.setUsername("me");
        user.setPassword("token");
        user.setEmail("ll");
        when(userService.findByUsername("me")).thenReturn(user);
        when( jwtTokenProvider.createToken("me", user.getRoles())).thenReturn("token");
        Map<Object, Object> response = new HashMap<>();
        response.put("username", "me");
        response.put("token", "token");
        Assertions.assertEquals(userAuthController.register("me","token","ll"),ResponseEntity.ok(response));
        verify(userService, times(1)).register(user);
    }
}
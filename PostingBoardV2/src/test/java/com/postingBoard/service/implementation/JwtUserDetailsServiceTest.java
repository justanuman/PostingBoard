package com.postingBoard.service.implementation;

import com.postingBoard.entity.DbRole;
import com.postingBoard.entity.DbUser;
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

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtUserDetailsServiceTest {
    @Mock
    UserService userService;

    @InjectMocks
    JwtUserDetailsService jwtUserDetailsService;
    @BeforeEach
    void initUseCase() {
        MockitoAnnotations.openMocks(jwtUserDetailsService);
    }
    @Test
    void loadUserByUsername() {
        DbUser users = new DbUser();
        users.setId(1);
        users.setUsername("test");
        users.setPassword("test");
        users.setStatus("ENABLED");
        List<DbRole> roles = new ArrayList<>();
        DbRole r = new DbRole();
        r.setName("lol");
        r.setId(1);
        roles.add(r);
        users.setRoles(roles);
        when(userService.findByUsername("test")).thenReturn(users);
        Assertions.assertEquals(jwtUserDetailsService.loadUserByUsername("test").getUsername(),"test");

    }
}
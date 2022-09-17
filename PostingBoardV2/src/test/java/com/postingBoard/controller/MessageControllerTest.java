package com.postingBoard.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.postingBoard.dto.ChatMessagesDto;
import com.postingBoard.entity.DbUser;
import com.postingBoard.service.interfaces.IChatService;
import com.postingBoard.service.interfaces.IUserService;
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
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageControllerTest {
    @Mock
    IUserService userService;
    @Mock
    IChatService chatService;
    @InjectMocks
    MessageController messageController;
    @BeforeEach
    void initUseCase() {
        MockitoAnnotations.openMocks(messageController);
    }
    @Test
    void sendMessage() throws JsonProcessingException {
        DbUser user  = new DbUser();
        user.setId(1);
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("me");
        when(userService.findByUsername("me")).thenReturn(user);
        when(userService.findById(1)).thenReturn(user);
        ChatMessagesDto chatMessagesDto = new ChatMessagesDto(1,1,"test", Date.from(Instant.now()));
        when(chatService.sendMessage(any(ChatMessagesDto.class) )).thenReturn(chatMessagesDto);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(chatMessagesDto);
        String response = messageController.sendMessage(1,"test",mockPrincipal);
        Assertions.assertEquals(response,json);
    }

    @Test
    void checkMail() throws JsonProcessingException {
        DbUser user  = new DbUser();
        user.setId(1);
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("me");
        when(userService.findByUsername("me")).thenReturn(user);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        List<ChatMessagesDto> chatMessagesDto = new ArrayList<>();
        ChatMessagesDto chatMessage = new ChatMessagesDto(1,1,"test", Date.from(Instant.now()));
        chatMessagesDto.add(chatMessage);
        when(userService.findById(1)).thenReturn(user);
        when(chatService.checkAllMail(user)).thenReturn(chatMessagesDto);
        when(chatService.checkMailFromUser(user,user)).thenReturn(chatMessagesDto);
        String json = ow.writeValueAsString(chatMessagesDto);
        Assertions.assertEquals(messageController.checkMail(mockPrincipal),json);
        Assertions.assertEquals(messageController.checkMail(1,mockPrincipal),json);

    }
}
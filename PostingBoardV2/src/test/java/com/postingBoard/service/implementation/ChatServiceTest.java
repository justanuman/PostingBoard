package com.postingBoard.service.implementation;

import com.postingBoard.entity.ChatMessage;
import com.postingBoard.dto.ChatMessagesDto;
import com.postingBoard.entity.DbUser;
import com.postingBoard.repo.IMessageDAO;
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
class ChatServiceTest {
    @Mock
    IMessageDAO messageDAO;
    @InjectMocks
    ChatService chatService;
    @BeforeEach
    void initUseCase() {
        MockitoAnnotations.openMocks(chatService);
    }
    @Test
    void sendMessage() {
        ChatMessage messages = new ChatMessage();
        messages.setAuthorId(3);
        messages.setContents("this is a test");
        messages.setRecepientId(4);
        ChatMessagesDto chatMessagesDto = new ChatMessagesDto(3,4,"this is a test",null);
        when(messageDAO.save(messages)).thenReturn(messages);
        Assertions.assertEquals((chatService.sendMessage(chatMessagesDto)).getAuthorId(),messages.getAuthorId());
        Assertions.assertEquals((chatService.sendMessage(chatMessagesDto)).getRecepientId(),messages.getRecepientId());
        Assertions.assertEquals((chatService.sendMessage(chatMessagesDto)).getContents(),messages.getContents());
    }

    @Test
    void checkAllMail() {
        ChatMessage messages = new ChatMessage();
        messages.setAuthorId(3);
        messages.setContents("this is a test");
        messages.setRecepientId(4);
        List<ChatMessage> chatMessagesList = new ArrayList<>();
        chatMessagesList.add(messages);
        when(messageDAO.findByRecepientId(4)).thenReturn(chatMessagesList);
        DbUser user = new DbUser();
        user.setId(4);
        Assertions.assertEquals((chatService.checkAllMail(user)).get(0).equals(messages.toDTO()),true);
    }

    @Test
    void checkMailFromUser() {
        ChatMessage messages = new ChatMessage();
        messages.setAuthorId(3);
        messages.setContents("this is a test");
        messages.setRecepientId(4);
        List<ChatMessage> chatMessagesList = new ArrayList<>();
        chatMessagesList.add(messages);
        when(messageDAO.findByAuthorIdAndRecepientId(3,4)).thenReturn(chatMessagesList);
        DbUser user = new DbUser();
        user.setId(4);
        DbUser user2 = new DbUser();
        user2.setId(3);
        Assertions.assertEquals((chatService.checkMailFromUser(user,user2)).get(0).equals(messages.toDTO()),true);
    }

}
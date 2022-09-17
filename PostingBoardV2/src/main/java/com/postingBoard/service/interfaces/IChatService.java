package com.postingBoard.service.interfaces;

import com.postingBoard.dto.ChatMessagesDto;
import com.postingBoard.entity.DbUser;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface IChatService {


    ChatMessagesDto sendMessage(ChatMessagesDto chatMessagesDto);

    List<ChatMessagesDto> checkAllMail(DbUser user);

    List<ChatMessagesDto> checkMailFromUser(DbUser recepient, DbUser author);

    List<ChatMessagesDto> checkSentMail(DbUser author);

    List<ChatMessagesDto> getAllMail(DbUser author);

    void checkForNull(Object object);
}

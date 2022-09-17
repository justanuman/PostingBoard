package com.postingBoard.service.implementation;

import com.postingBoard.dto.ChatMessagesDto;
import com.postingBoard.entity.ChatMessage;
import com.postingBoard.entity.DbUser;
import com.postingBoard.repo.IMessageDAO;
import com.postingBoard.service.interfaces.IChatService;
import com.postingBoard.utility.Mappers.ChatMessagesDtoAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class ChatService implements IChatService {
    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);

    @Autowired
    IMessageDAO messageDAO;


    ChatMessagesDtoAdapter chatMessagesDtoAdapter = new ChatMessagesDtoAdapter();
    @Override
    public ChatMessagesDto sendMessage(ChatMessagesDto chatMessagesDto) {
        ChatMessage messages = new ChatMessage();
        messages.setAuthorId(chatMessagesDto.getAuthorId());
        messages.setContents(chatMessagesDto.getContents());
        messages.setRecepientId(chatMessagesDto.getRecepientId());
        logger.info("message send by {}", messages.getAuthorId());
        //return messageDAO.save(messages).toDTO();
        ChatMessage chatMessage=messageDAO.save(messages);
        return chatMessagesDtoAdapter.modelToDto(chatMessage);
    }

    @Override
    public List<ChatMessagesDto> checkAllMail(DbUser user) {
        List<ChatMessage> chatMessagesList = messageDAO.findByRecepientId(user.getId());
        List<ChatMessagesDto> out = new ArrayList<>();
        for (ChatMessage elem : chatMessagesList) {
            out.add(chatMessagesDtoAdapter.modelToDto(elem));
            //out.add(elem.toDTO());
        }
        logger.info(" {} checked messages", user.getId());
        return out;
    }

    @Override
    public List<ChatMessagesDto> checkMailFromUser(DbUser recepient, DbUser author) {
        List<ChatMessage> chatMessagesList = messageDAO.findByAuthorIdAndRecepientId(author.getId(), recepient.getId());
        List<ChatMessagesDto> out = new ArrayList<>();
        for (ChatMessage elem : chatMessagesList) {
            //out.add(elem.toDTO());
            out.add(chatMessagesDtoAdapter.modelToDto(elem));
        }
        logger.info(" {} checked messages", recepient.getId());
        return out;
    }

    @Override
    public List<ChatMessagesDto> checkSentMail(DbUser author) {
        List<ChatMessage> chatMessagesList = messageDAO.findByAuthorId(author.getId());
        List<ChatMessagesDto> out = new ArrayList<>();
        for (ChatMessage elem : chatMessagesList) {
            //out.add(elem.toDTO());
            out.add(chatMessagesDtoAdapter.modelToDto(elem));
        }
        logger.info(" {} checkSentMail ", author.getId());
        return out;
    }

    @Override
    public List<ChatMessagesDto> getAllMail(DbUser author) {
        List<ChatMessage> chatMessagesList = messageDAO.findByAuthorIdOrRecepientId(author.getId(), author.getId());
        List<ChatMessagesDto> out = new ArrayList<>();
        for (ChatMessage elem : chatMessagesList) {
            //out.add(elem.toDTO());
            out.add(chatMessagesDtoAdapter.modelToDto(elem));
        }
        logger.info(" {} checkSentMail ", author.getId());
        return out;
    }

    @Override
    public void checkForNull(Object object) {
        if (object.equals(null)) {
            throw new ResponseStatusException(NOT_FOUND, "Unable to find resource");
        }
    }
}

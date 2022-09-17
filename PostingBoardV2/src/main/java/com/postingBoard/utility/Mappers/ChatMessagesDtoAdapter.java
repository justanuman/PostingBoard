package com.postingBoard.utility.Mappers;

import com.postingBoard.dto.ChatMessagesDto;
import com.postingBoard.entity.ChatMessage;
import org.springframework.stereotype.Component;

@Component
public class ChatMessagesDtoAdapter implements Adapter<ChatMessage, ChatMessagesDto> {
    @Override
    public  ChatMessagesDto modelToDto(ChatMessage model) {

        return new ChatMessagesDto(model.getAuthorId(), model.getRecepientId(), model.getContents(), model.getSend());
    }

    @Override
    public ChatMessage dtoToModel(ChatMessagesDto dto) {
        return Adapter.super.dtoToModel(dto);
    }
}

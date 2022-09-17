package com.postingBoard.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.postingBoard.dto.ChatMessagesDto;
import com.postingBoard.entity.DbUser;
import com.postingBoard.service.interfaces.IChatService;
import com.postingBoard.service.interfaces.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Controller
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
    @Autowired
    IUserService userService;
    @Autowired
    IChatService chatService;

    @PostMapping(
            value = "messageservice/sendmessage",
            produces = "application/json"
    )
    @ResponseBody
    public String sendMessage(@RequestParam int recepientID, @RequestParam String message, Principal principal) throws JsonProcessingException {
        DbUser user = userService.findById(recepientID);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        userService.checkForNull(user);
        ChatMessagesDto chatMessagesDto = new ChatMessagesDto(userService.findByUsername(principal.getName()).getId(), recepientID, message, Date.from(Instant.now()));
        chatMessagesDto = chatService.sendMessage(chatMessagesDto);
        String json = ow.writeValueAsString(chatMessagesDto);
        return json;

    }

    @GetMapping(
            value = "messageservice/checkMail",
            produces = "application/json"
    )
    @ResponseBody
    public String checkMail(Principal principal) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        List<ChatMessagesDto> chatMessagesDto = chatService.checkAllMail(userService.findByUsername(principal.getName()));
        String json = ow.writeValueAsString(chatMessagesDto);
        return json;
    }

    @GetMapping(
            value = "messageservice/checkMailFrom",
            produces = "application/json"
    )
    @ResponseBody
    public String checkMail(@RequestParam int sender, Principal principal) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        userService.checkForNull(userService.findById(sender));
        List<ChatMessagesDto> chatMessagesDto = chatService.checkMailFromUser(userService.findByUsername(principal.getName()), userService.findById(sender));
        String json = ow.writeValueAsString(chatMessagesDto);
        return json;
    }

    @GetMapping(
            value = "messageservice/checksentmail",
            produces = "application/json"
    )
    @ResponseBody
    public String checkSentMail(Principal principal) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        List<ChatMessagesDto> chatMessagesDto = chatService.checkSentMail(userService.findByUsername(principal.getName()));
        String json = ow.writeValueAsString(chatMessagesDto);
        return json;
    }

    @GetMapping(
            value = "messageservice/getallmail",
            produces = "application/json"
    )
    @ResponseBody
    public String getAllMail(Principal principal) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        List<ChatMessagesDto> chatMessagesDto = chatService.getAllMail(userService.findByUsername(principal.getName()));
        String json = ow.writeValueAsString(chatMessagesDto);
        return json;
    }
}

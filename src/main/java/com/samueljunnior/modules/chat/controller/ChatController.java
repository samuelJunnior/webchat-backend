package com.samueljunnior.modules.chat.controller;

import com.samueljunnior.modules.chat.service.ChatService;
import com.samueljunnior.modules.chatmessage.entity.ChatMessage;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/messages/{roomId}")
    public ChatMessage sendMessage(@DestinationVariable String roomId, ChatMessage message) {
        return chatService.sendMessage(roomId, message);
    }
}
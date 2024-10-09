package com.samueljunnior.modules.chatmessage.service;

import com.samueljunnior.modules.chatmessage.entity.ChatMessage;
import com.samueljunnior.modules.chatmessage.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    public ChatMessage saveMessage(ChatMessage message) {
        message.setCreationInstant(Instant.now());
        return chatMessageRepository.save(message);
    }

    public List<ChatMessage> getMessagesByRoomId(String roomId) {
        return chatMessageRepository.findByRoomIdOrderByCreationInstantAsc(roomId);
    }

    public void deleteMessagesByRoomId(String roomId){
        chatMessageRepository.deleteByRoomId(roomId);
    }
}

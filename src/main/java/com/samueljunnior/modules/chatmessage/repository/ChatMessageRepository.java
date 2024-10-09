package com.samueljunnior.modules.chatmessage.repository;

import com.samueljunnior.modules.chatmessage.entity.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    List<ChatMessage> findByRoomIdOrderByCreationInstantAsc(String roomId);

    void deleteByRoomId(String roomId);

}
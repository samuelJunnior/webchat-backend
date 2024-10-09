package com.samueljunnior.modules.chatmessage.entity;

import com.samueljunnior.modules.user.entity.UserEntity;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "chatMessages")
public class ChatMessage{

    @Id
    private String id;
    private String roomId;
    private UserEntity sender;
    private String content;
    private Instant creationInstant;
}
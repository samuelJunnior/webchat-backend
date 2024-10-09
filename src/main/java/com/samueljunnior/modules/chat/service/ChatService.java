package com.samueljunnior.modules.chat.service;

import com.mongodb.MongoException;
import com.samueljunnior.core.exception.BusinessException;
import com.samueljunnior.modules.chat.dto.NotificationPayload;
import com.samueljunnior.modules.chatmessage.entity.ChatMessage;
import com.samueljunnior.modules.chatmessage.service.ChatMessageService;
import com.samueljunnior.modules.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;
    private final RoomService roomService;

    @Transactional
    public ChatMessage sendMessage(String roomId, ChatMessage payload) {
        final var room = roomService.findRoomById(UUID.fromString(roomId));
        try{
            roomService.addRoomUserRelationship(room, payload.getSender());
            final var lastChatMessage = chatMessageService.saveMessage(payload);

            final var msgNotification = new NotificationPayload(
                    room.getRoomName(),
                    payload.getSender().getUsername(),
                    payload.getContent()
            );

            room.getUsers().stream()
                    .filter(u -> !u.getId().equals(payload.getSender().getId()))
                    .forEach(user -> sendNotification(user.getId().toString(), msgNotification));

            return lastChatMessage;
        }catch (MongoException e){
            log.error("Garantindo consistência entre os bancos. Desfazendo operação do db_relacional.");
            roomService.removeRoomUserRelationship(room, payload.getSender());
            throw new BusinessException("Erro ao salvar mensagem.");
        }
    }

    private void sendNotification(String userId, NotificationPayload notificationMessage) {
        messagingTemplate.convertAndSend("/topic/notifications/" + userId, notificationMessage);
    }
}

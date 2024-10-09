package com.samueljunnior.modules.chat.dto;

public record NotificationPayload(
        String roomName,
        String sender,
        String content
) {
}

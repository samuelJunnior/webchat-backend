package com.samueljunnior.modules.chatmessage.controller;

import com.samueljunnior.modules.chatmessage.entity.ChatMessage;
import com.samueljunnior.modules.chatmessage.service.ChatMessageService;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/chat-message")
@Tag(
        name = "Chat Message",
        description = "Operações sobre as mensagens de uma sala.",
        externalDocs = @ExternalDocumentation(description = "Developer WebSite", url = "https://samueljunnior.github.io/about-me/")
)
public class ChatMessageController {
    private final ChatMessageService chatMessageService;

    @Operation(
            summary = "Busca as mensagens de uma sala por id.",
            description = "Busca as mensagens dos usuários de uma sala pelo id.",
            responses = @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            array = @ArraySchema(
                                    schema = @Schema(
                                            implementation = ChatMessage.class
                                    )
                            )
                    )
            )
    )
    @GetMapping("/{idRoom}")
    public ResponseEntity<List<ChatMessage>> findMessagesByRoomId(@PathVariable String idRoom) {
        return ResponseEntity.ok(chatMessageService.getMessagesByRoomId(idRoom));
    }

}

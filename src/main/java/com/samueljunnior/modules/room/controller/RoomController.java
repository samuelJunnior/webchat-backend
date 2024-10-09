package com.samueljunnior.modules.room.controller;

import com.samueljunnior.modules.room.dto.CreateRoomDTO;
import com.samueljunnior.modules.room.dto.PageResponser;
import com.samueljunnior.modules.room.dto.RoomDTO;
import com.samueljunnior.modules.room.dto.RoomFilter;
import com.samueljunnior.modules.room.service.RoomService;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/room")
@Tag(
        name = "Room",
        description = "Operaçẽos sobre as web salas.",
        externalDocs = @ExternalDocumentation(description = "Developer WebSite", url = "https://samueljunnior.github.io/about-me/")
)
public class RoomController {

    private final RoomService service;

    @Operation(
            summary = "Cria uma nova sala.",
            description = "Cria uma nova sala.",
            responses = @ApiResponse(
                    responseCode = "200"
            )
    )
    @PostMapping
    public ResponseEntity<UUID> createRoom(@RequestBody @Valid CreateRoomDTO body){
        return ResponseEntity.ok(service.createRoom(body));
    }

    @Operation(
            summary = "Busca as salas cadastradas.",
            description = "Busca, por filtro e com paginação, as salas cadastradas.",
            responses = @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                        schema = @Schema(
                                implementation = PageResponser.class
                        )
                    )
            )
    )
    @GetMapping
    @Parameters({
        @Parameter(name = "idRoom", description = "Filtro por id da sala", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
        @Parameter(name = "roomName", description = "Filtro por nome da sala", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
        @Parameter(name = "filter", hidden = true)
    })
    public ResponseEntity<PageResponser> findRoom(
            @RequestParam(defaultValue = "1") Integer pageNumber,
            @RequestParam(defaultValue = "8") Integer pageSize,
            @RequestParam(defaultValue = "roomName") String sortField,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortDirecton,
            RoomFilter filter
    ){
        final var pageRequest = PageRequest.of(
                pageNumber - 1,
                pageSize,
                sortDirecton,
                sortField
        );

        return ResponseEntity.ok(service.findRoomPageble(pageRequest, filter));
    }

    @Operation(
            summary = "Exclui uma sala por id.",
            description = "Exclui uma sala por id. Ao excluir a sala, os usuários vinculados serão desvinculados e as mensagens apagadas.",
            responses = @ApiResponse(
                    responseCode = "200"
            )
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable UUID id){
        service.deleteRoom(id);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Busca uma sala pelo id.",
            description = "Busca uma sala pelo id.",
            responses = @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                        schema = @Schema(
                                implementation = RoomDTO.class
                        )
                    )
            )
    )
    @GetMapping("/{id}")
    public ResponseEntity<RoomDTO> findRoomById(@PathVariable UUID id){
        return ResponseEntity.ok(service.findRoom(id));
    }

}

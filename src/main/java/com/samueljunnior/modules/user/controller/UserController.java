package com.samueljunnior.modules.user.controller;

import com.samueljunnior.modules.user.dto.UserDto;
import com.samueljunnior.modules.user.service.UserService;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user")
@Tag(
        name = "User",
        description = "Operações sobre usuário.",
        externalDocs = @ExternalDocumentation(description = "Developer WebSite", url = "https://samueljunnior.github.io/about-me/")
)
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Busca um usuário por id.",
            description = "Busca um usuário por id.",
            responses = @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                        schema = @Schema(
                                implementation = UserDto.class
                        )
                    )
            )
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findUser(@PathVariable Long id){
        return ResponseEntity.ok(userService.findUser(id));
    }

    @Operation(
            summary = "Busca um usuário por e-mail.",
            description = "Busca um usuário por e-mail.",
            responses = @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                        schema = @Schema(
                                implementation = UserDto.class
                        )
                    )
            )
    )
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> findUserByEmail(@PathVariable String email){
        return ResponseEntity.ok(userService.findUserByEmail(email));
    }

    @Operation(
            summary = "Cria um novo usuário.",
            description = "Cria um novo usuário.",
            responses = @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                        schema = @Schema(
                                implementation = UserDto.class
                        )
                    )
            )
    )
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto user){
        return ResponseEntity.ok(userService.saveUser(user));
    }
}

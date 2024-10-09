package com.samueljunnior.modules.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "tb_usuarios")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    public static final String USER_ID = "id_usuario";

    @Id
    @Column(name = USER_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "des_nome_usuario")
    private String username;

    @Column(name = "des_email", unique = true)
    private String email;

    @Column(name = "dt_cadastro")
    private Instant registrationDate;
}



package com.samueljunnior.modules.room.entity;

import com.samueljunnior.modules.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tb_web_sala")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomEntity {

    public static final String ID_ROOM = "id_sala";

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "des_nome", nullable = false)
    private String roomName;

    @Column(name = "dt_criacao")
    private Instant creationTimestamp;

    @ManyToOne
    @JoinColumn(name = UserEntity.USER_ID)
    private UserEntity owner;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tb_sala_usuario",
            joinColumns = @JoinColumn(name = ID_ROOM ),
            inverseJoinColumns = @JoinColumn(name =  UserEntity.USER_ID)
    )
    @Builder.Default
    private Set<UserEntity> users = new LinkedHashSet<>();
}

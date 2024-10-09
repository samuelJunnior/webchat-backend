package com.samueljunnior.modules.room.service;

import com.samueljunnior.core.exception.BusinessException;
import com.samueljunnior.core.exception.NotFoundException;
import com.samueljunnior.modules.chatmessage.service.ChatMessageService;
import com.samueljunnior.modules.room.dto.CreateRoomDTO;
import com.samueljunnior.modules.room.dto.PageResponser;
import com.samueljunnior.modules.room.dto.RoomDTO;
import com.samueljunnior.modules.room.dto.RoomFilter;
import com.samueljunnior.modules.room.entity.RoomEntity;
import com.samueljunnior.modules.room.mapper.RoomMapper;
import com.samueljunnior.modules.room.repository.RoomRepository;
import com.samueljunnior.modules.user.entity.UserEntity;
import com.samueljunnior.modules.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;
    private final UserService userService;
    private final ChatMessageService chatMessageService;

    public UUID createRoom(CreateRoomDTO room){
        final var user = userService.findUserById(room.userId());
        final var roomEntity = roomRepository.save(
                RoomEntity.builder()
                        .roomName(room.roomName())
                        .creationTimestamp(Instant.now())
                        .owner(user)
                        .build()
        );

        return roomEntity.getId();
    }

    public PageResponser findRoomPageble(PageRequest pageble, RoomFilter filter){
        final var filterEntity = RoomEntity.builder()
                .id(this.getUUIDField(filter.idRoom()))
                .roomName(filter.roomName()
                ).build();

        final var exm = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withMatcher("id", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("roomName", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

        final var ex = Example.of(filterEntity, exm);

        final var data = roomRepository.findAll(ex, pageble);

        return new PageResponser(data.getTotalPages(), data.getTotalElements(), roomMapper.toDto(data.getContent()));
    }

    private UUID getUUIDField(String idRoom) {
        if(Objects.nonNull(idRoom)){
            try{
                return UUID.fromString(idRoom);
            }catch (Exception e){
                throw new BusinessException("Informe um UUID válido.");
            }
        }
        return null;
    }

    public RoomEntity findRoomById(UUID id){
        return roomRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public RoomDTO findRoom(UUID id){
        return roomMapper.toDto(this.findRoomById(id));
    }
    public void addRoomUserRelationship(RoomEntity room, UserEntity user){
        room.getUsers().stream()
                .filter(u -> u.getId().equals(user.getId()))
                .findAny()
                .ifPresentOrElse(
                        (u) -> log.info("Usuario ja relacionado a turma."),
                        () -> {
                            room.getUsers().add(user);
                            roomRepository.save(room);
                        }
                );
    }

    public void removeRoomUserRelationship(RoomEntity room, UserEntity user){
        room.getUsers().remove(user);
        roomRepository.save(room);
    }

    public void deleteRoom(UUID id) {
        roomRepository.deleteById(id);
        chatMessageService.deleteMessagesByRoomId(id.toString());
    }
}

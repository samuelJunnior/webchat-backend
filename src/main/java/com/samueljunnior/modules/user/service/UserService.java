package com.samueljunnior.modules.user.service;

import com.samueljunnior.core.exception.NotFoundException;
import com.samueljunnior.modules.user.dto.UserDto;
import com.samueljunnior.modules.user.entity.UserEntity;
import com.samueljunnior.modules.user.mapper.UserMapper;
import com.samueljunnior.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto findUser(Long id) {
        return userMapper.toDto(this.findUserById(id));
    }

    public UserDto findUserByEmail(String email) {
        return userMapper.toDto(userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(NotFoundException::new));
    }

    public UserEntity findUserById(Long idUser) {
        return userRepository.findById(idUser)
                .orElseThrow(NotFoundException::new);
    }

    public UserDto saveUser(UserDto user){
        final var entity = userMapper.toEntity(user);
        entity.setRegistrationDate(Instant.now());
        return userMapper.toDto(userRepository.save(entity));
    }

}

package com.samueljunnior.modules.user.mapper;


import com.samueljunnior.core.mapper.BaseMapper;
import com.samueljunnior.modules.user.dto.UserDto;
import com.samueljunnior.modules.user.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends BaseMapper<UserEntity, UserDto> {
}
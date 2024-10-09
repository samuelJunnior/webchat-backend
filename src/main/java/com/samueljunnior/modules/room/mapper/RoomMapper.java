package com.samueljunnior.modules.room.mapper;


import com.samueljunnior.core.mapper.BaseMapper;
import com.samueljunnior.modules.room.dto.RoomDTO;
import com.samueljunnior.modules.room.entity.RoomEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoomMapper extends BaseMapper<RoomEntity, RoomDTO> {
}
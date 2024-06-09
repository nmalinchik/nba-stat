package com.example.nbastat.statisticsservice.mapper;

import com.example.nbastat.statisticsservice.model.PlayerStatistic;
import com.example.nbastat.statisticsservice.model.PlayerStatisticDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PlayerStatisticMapper {

    PlayerStatisticMapper INSTANCE = Mappers.getMapper(PlayerStatisticMapper.class);

    PlayerStatistic toEntity(PlayerStatisticDto playerStatisticDto);
    PlayerStatisticDto toDto(PlayerStatistic playerStatistic);

    PlayerStatistic updateEntity(@MappingTarget PlayerStatistic playerStatistic, PlayerStatisticDto playerStatisticDto);

}

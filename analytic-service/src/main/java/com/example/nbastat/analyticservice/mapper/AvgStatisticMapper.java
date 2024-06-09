package com.example.nbastat.analyticservice.mapper;

import com.example.nbastat.analyticservice.model.dto.PlayerDto;
import com.example.nbastat.analyticservice.model.dto.PlayerStatisticAvgDto;
import com.example.nbastat.analyticservice.model.dto.StatisticAvgDto;
import com.example.nbastat.analyticservice.model.dto.TeamDto;
import com.example.nbastat.analyticservice.model.dto.TeamStatisticAvgDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AvgStatisticMapper {
    AvgStatisticMapper INSTANCE = Mappers.getMapper(AvgStatisticMapper.class);

    PlayerStatisticAvgDto toPlayerStatisticAvgDto(StatisticAvgDto statisticAvgDto, PlayerDto playerDto);

    TeamStatisticAvgDto toTeamStatisticAvgDto(StatisticAvgDto statisticAvgDto, TeamDto teamDto);
}

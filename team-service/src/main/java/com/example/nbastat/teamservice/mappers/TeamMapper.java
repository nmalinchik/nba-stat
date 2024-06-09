package com.example.nbastat.teamservice.mappers;

import com.example.nbastat.teamservice.model.PlayerDto;
import com.example.nbastat.teamservice.model.Team;
import com.example.nbastat.teamservice.model.TeamDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TeamMapper {
    TeamMapper INSTANCE = Mappers.getMapper(TeamMapper.class);

    Team toEntity(TeamDto teamDto);
    TeamDto toDto(Team team);

    TeamDto toTeamDto(Team team, List<PlayerDto> players);

    @Mapping(target = "id", ignore = true)
    Team updateTeam(@MappingTarget Team team, TeamDto teamDto);
}

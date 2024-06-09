package com.example.nbastat.playerservice.mappers;

import com.example.nbastat.playerservice.model.Player;
import com.example.nbastat.playerservice.model.PlayerDto;
import com.example.nbastat.playerservice.model.TeamDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PlayerMapper {
    PlayerMapper INSTANCE = Mappers.getMapper(PlayerMapper.class);

    @Mapping(target = "teamId", source = "teamDto.id")
    Player toEntity(PlayerDto playerDto);

    @Mapping(target = "teamDto.id", source = "teamId")
    PlayerDto toDto(Player player);

    @Mapping(source = "player.id", target = "id")
    PlayerDto fromPlayer(Player player, TeamDto teamDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "teamId", source = "teamDto.id")
    Player updatePlayer(@MappingTarget Player player, PlayerDto playerDto);

}

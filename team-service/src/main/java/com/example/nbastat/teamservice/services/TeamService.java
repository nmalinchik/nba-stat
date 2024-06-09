package com.example.nbastat.teamservice.services;

import com.example.nbastat.teamservice.mappers.TeamMapper;
import com.example.nbastat.teamservice.model.Team;
import com.example.nbastat.teamservice.model.TeamDto;
import com.example.nbastat.teamservice.repos.TeamRepository;
import com.example.nbastat.teamservice.services.external.PlayerClient;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final PlayerClient playerClient;

    public Team getTeamEntity(UUID teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFoundException("No team found with id: " + teamId));
    }

    public TeamDto getTeam(UUID id) {
        var team = getTeamEntity(id);
        var players = playerClient.getTeamPlayers(team.getId());
        return TeamMapper.INSTANCE.toTeamDto(team, players);
    }

    public TeamDto saveTeam(TeamDto teamDto) {
        var saved = teamRepository.save(TeamMapper.INSTANCE.toEntity(teamDto));
        return TeamMapper.INSTANCE.toDto(saved);
    }

    public TeamDto updateTeam(UUID id, TeamDto teamDto) {
        var team = getTeamEntity(id);
        var updated = teamRepository.save(TeamMapper.INSTANCE.updateTeam(team, teamDto));
        return TeamMapper.INSTANCE.toDto(updated);
    }

    public void deleteTeam(UUID id) {
        teamRepository.deleteById(id);
    }
}

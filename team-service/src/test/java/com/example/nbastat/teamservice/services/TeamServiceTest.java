package com.example.nbastat.teamservice.services;

import com.example.nbastat.teamservice.model.PlayerDto;
import com.example.nbastat.teamservice.model.Team;
import com.example.nbastat.teamservice.model.TeamDto;
import com.example.nbastat.teamservice.repos.TeamRepository;
import com.example.nbastat.teamservice.services.external.PlayerClient;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private PlayerClient playerClient;

    @InjectMocks
    private TeamService teamService;

    private UUID teamId;
    private Team team;
    private TeamDto teamDto;
    private List<PlayerDto> players;

    @BeforeEach
    void setUp() {
        teamId = UUID.randomUUID();
        team = new Team(teamId, "Team Name");
        players = List.of(new PlayerDto(UUID.randomUUID(), "John", "Doe"));
        teamDto = new TeamDto(teamId, "Team Name", players);
    }

    @Test
    void getTeamEntity_existingId_shouldReturnTeam() {
        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));
        Team foundTeam = teamService.getTeamEntity(teamId);
        assertNotNull(foundTeam);
        assertEquals(teamId, foundTeam.getId());
        verify(teamRepository, times(1)).findById(teamId);
    }

    @Test
    void getTeamEntity_nonExistingId_shouldThrowException() {
        when(teamRepository.findById(teamId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> teamService.getTeamEntity(teamId));
        verify(teamRepository, times(1)).findById(teamId);
    }

    @Test
    void getTeam_existingId_shouldReturnTeamDto() {
        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));
        when(playerClient.getTeamPlayers(teamId)).thenReturn(players);
        TeamDto foundTeamDto = teamService.getTeam(teamId);
        assertNotNull(foundTeamDto);
        assertEquals(teamId, foundTeamDto.getId());
        assertEquals("Team Name", foundTeamDto.getName());
        assertEquals(players, foundTeamDto.getPlayers());
        verify(teamRepository, times(1)).findById(teamId);
        verify(playerClient, times(1)).getTeamPlayers(teamId);
    }

    @Test
    void saveTeam_shouldReturnSavedTeamDto() {
        when(teamRepository.save(any(Team.class))).thenReturn(team);
        TeamDto savedTeamDto = teamService.saveTeam(teamDto);
        assertNotNull(savedTeamDto);
        assertEquals(teamId, savedTeamDto.getId());
        assertEquals("Team Name", savedTeamDto.getName());
        verify(teamRepository, times(1)).save(any(Team.class));
    }

    @Test
    void updateTeam_existingId_shouldReturnUpdatedTeamDto() {
        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));
        when(teamRepository.save(any(Team.class))).thenReturn(team);
        TeamDto updatedTeamDto = teamService.updateTeam(teamId, teamDto);
        assertNotNull(updatedTeamDto);
        assertEquals(teamId, updatedTeamDto.getId());
        assertEquals("Team Name", updatedTeamDto.getName());
        verify(teamRepository, times(1)).findById(teamId);
        verify(teamRepository, times(1)).save(any(Team.class));
    }

    @Test
    void updateTeam_nonExistingId_shouldThrowException() {
        when(teamRepository.findById(teamId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> teamService.updateTeam(teamId, teamDto));
        verify(teamRepository, times(1)).findById(teamId);
    }

    @Test
    void deleteTeam_existingId_shouldDeleteTeam() {
        doNothing().when(teamRepository).deleteById(teamId);
        teamService.deleteTeam(teamId);
        verify(teamRepository, times(1)).deleteById(teamId);
    }
}

package com.example.nbastat.playerservice.services;

import com.example.nbastat.playerservice.model.Player;
import com.example.nbastat.playerservice.model.PlayerDto;
import com.example.nbastat.playerservice.model.TeamDto;
import com.example.nbastat.playerservice.repos.PlayerRepository;
import com.example.nbastat.playerservice.services.external.TeamClient;
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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private TeamClient teamClient;

    @InjectMocks
    private PlayerService playerService;

    private Player player;
    private PlayerDto playerDto;
    private UUID playerId;
    private UUID teamId;
    private TeamDto teamDto;

    @BeforeEach
    void setUp() {
        playerId = UUID.randomUUID();
        teamId = UUID.randomUUID();
        player = new Player(playerId, "John", "Doe", teamId);
        teamDto = new TeamDto(teamId, "Team Name");
        playerDto = new PlayerDto(playerId, "John", "Doe", teamDto);
    }

    @Test
    void getShortPlayer_existingId_shouldReturnPlayer() {
        when(playerRepository.findById(playerId)).thenReturn(Optional.of(player));
        Player foundPlayer = playerService.getShortPlayer(playerId);
        assertEquals(player, foundPlayer);
        verify(playerRepository, times(1)).findById(playerId);
    }

    @Test
    void getShortPlayer_nonExistingId_shouldThrowException() {
        when(playerRepository.findById(playerId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> playerService.getShortPlayer(playerId));
        verify(playerRepository, times(1)).findById(playerId);
    }

    @Test
    void getPlayer_existingId_shouldReturnPlayerDto() {
        when(playerRepository.findById(playerId)).thenReturn(Optional.of(player));
        when(teamClient.getTeamById(teamId)).thenReturn(teamDto);
        PlayerDto foundPlayerDto = playerService.getPlayer(playerId);
        assertNotNull(foundPlayerDto);
        assertEquals(playerDto.getId(), foundPlayerDto.getId());
        assertEquals(playerDto.getTeamDto(), foundPlayerDto.getTeamDto());
        verify(playerRepository, times(1)).findById(playerId);
        verify(teamClient, times(1)).getTeamById(teamId);
    }

    @Test
    void getTeamPlayers_existingTeamId_shouldReturnPlayerList() {
        when(playerRepository.findByTeamId(teamId)).thenReturn(List.of(player));
        List<Player> teamPlayers = playerService.getTeamPlayers(teamId);
        assertNotNull(teamPlayers);
        assertFalse(teamPlayers.isEmpty());
        assertEquals(player, teamPlayers.get(0));
        verify(playerRepository, times(1)).findByTeamId(teamId);
    }

    @Test
    void savePlayer_shouldReturnSavedPlayerDto() {
        when(playerRepository.save(any(Player.class))).thenReturn(player);
        PlayerDto savedPlayerDto = playerService.savePlayer(playerDto);
        assertNotNull(savedPlayerDto);
        assertEquals(playerDto.getId(), savedPlayerDto.getId());
        assertEquals(playerDto.getTeamDto().getId(), savedPlayerDto.getTeamDto().getId());
        verify(playerRepository, times(1)).save(any(Player.class));
    }

    @Test
    void updatePlayer_existingId_shouldReturnUpdatedPlayerDto() {
        when(playerRepository.findById(playerId)).thenReturn(Optional.of(player));
        when(playerRepository.save(any(Player.class))).thenReturn(player);
        PlayerDto updatedPlayerDto = playerService.updatePlayer(playerId, playerDto);
        assertNotNull(updatedPlayerDto);
        assertEquals(playerDto.getId(), updatedPlayerDto.getId());
        assertEquals(playerDto.getTeamDto().getId(), updatedPlayerDto.getTeamDto().getId());
        verify(playerRepository, times(1)).findById(playerId);
        verify(playerRepository, times(1)).save(any(Player.class));
    }

    @Test
    void updatePlayer_nonExistingId_shouldThrowException() {
        when(playerRepository.findById(playerId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> playerService.updatePlayer(playerId, playerDto));
        verify(playerRepository, times(1)).findById(playerId);
        verify(playerRepository, times(0)).save(any(Player.class));
    }

    @Test
    void deletePlayer_existingId_shouldDeletePlayer() {
        doNothing().when(playerRepository).deleteById(playerId);
        playerService.deletePlayer(playerId);
        verify(playerRepository, times(1)).deleteById(playerId);
    }
}
package com.example.nbastat.playerservice.services;

import com.example.nbastat.playerservice.mappers.PlayerMapper;
import com.example.nbastat.playerservice.model.Player;
import com.example.nbastat.playerservice.model.PlayerDto;
import com.example.nbastat.playerservice.repos.PlayerRepository;
import com.example.nbastat.playerservice.services.external.TeamClient;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final TeamClient teamClient;

    public Player getShortPlayer(UUID playerId) {
        return playerRepository.findById(playerId)
                .orElseThrow(() -> new EntityNotFoundException("Player with id " + playerId + " not found"));
    }

    public PlayerDto getPlayer(UUID playerId) {
        var player = getShortPlayer(playerId);
        var team = teamClient.getTeamById(player.getTeamId());
        return PlayerMapper.INSTANCE.fromPlayer(player, team);
    }

    public List<Player> getTeamPlayers(UUID teamId) {
        return playerRepository.findByTeamId(teamId);
    }

    public PlayerDto savePlayer(PlayerDto playerDto) {
        var saved = playerRepository.save(PlayerMapper.INSTANCE.toEntity(playerDto));
        return PlayerMapper.INSTANCE.toDto(saved);
    }

    public PlayerDto updatePlayer(UUID playerId, PlayerDto playerDto) {
        var player = playerRepository.findById(playerId)
                .map(pl -> PlayerMapper.INSTANCE.updatePlayer(pl, playerDto))
                .orElseThrow(() -> new EntityNotFoundException("Player with id " + playerId + " not found"));
         var saved = playerRepository.save(player);
         return PlayerMapper.INSTANCE.toDto(saved);
    }

    public void deletePlayer(UUID playerId) {
        playerRepository.deleteById(playerId);
    }

}

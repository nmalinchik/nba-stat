package com.example.nbastat.statisticsservice.service;

import com.example.nbastat.statisticsservice.model.PlayerStatistic;
import com.example.nbastat.statisticsservice.model.PlayerStatisticDto;
import com.example.nbastat.statisticsservice.repos.PlayerStatisticRepository;
import com.example.nbastat.statisticsservice.service.external.AnalyticClient;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerStatisticServiceTest {

    @Mock
    private PlayerStatisticRepository playerStatisticRepository;

    @Mock
    private AnalyticClient analyticClient;

    @InjectMocks
    private PlayerStatisticService playerStatisticService;

    private UUID playerId;
    private UUID teamId;
    private Year season;
    private PlayerStatistic playerStatistic;
    private PlayerStatisticDto playerStatisticDto;

    @BeforeEach
    void setUp() {
        playerId = UUID.randomUUID();
        teamId = UUID.randomUUID();
        season = Year.of(2023);

        playerStatistic = new PlayerStatistic(
                UUID.randomUUID(), playerId, teamId, 25, 5, 10, 2, 3, 4, 2, 35.0, season
        );

        playerStatisticDto = new PlayerStatisticDto(
                playerStatistic.getId(), playerId, teamId, 25, 5, 10, 2, 3, 4, 2, 35.0, season
        );
    }

    @Test
    void getById_existingId_shouldReturnPlayerStatisticDto() {
        when(playerStatisticRepository.findById(playerStatistic.getId())).thenReturn(Optional.of(playerStatistic));

        PlayerStatisticDto result = playerStatisticService.getById(playerStatistic.getId());

        assertNotNull(result);
        assertEquals(playerStatisticDto, result);
        verify(playerStatisticRepository, times(1)).findById(playerStatistic.getId());
    }

    @Test
    void getById_nonExistingId_shouldThrowException() {
        when(playerStatisticRepository.findById(playerStatistic.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> playerStatisticService.getById(playerStatistic.getId()));
        verify(playerStatisticRepository, times(1)).findById(playerStatistic.getId());
    }

    @Test
    void getPlayerStatistics_existingPlayerIdAndSeason_shouldReturnPlayerStatistics() {
        when(playerStatisticRepository.findByPlayerIdAndSeason(playerId, season)).thenReturn(List.of(playerStatistic));

        List<PlayerStatisticDto> result = playerStatisticService.getPlayerStatistics(playerId, season);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(playerStatisticDto, result.get(0));
        verify(playerStatisticRepository, times(1)).findByPlayerIdAndSeason(playerId, season);
    }

    @Test
    void getTeamStatistics_existingTeamIdAndSeason_shouldReturnTeamStatistics() {
        when(playerStatisticRepository.findByTeamIdAndSeason(teamId, season)).thenReturn(List.of(playerStatistic));

        List<PlayerStatisticDto> result = playerStatisticService.getTeamStatistics(teamId, season);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(playerStatisticDto, result.get(0));
        verify(playerStatisticRepository, times(1)).findByTeamIdAndSeason(teamId, season);
    }

    @Test
    void save_validPlayerStatisticDto_shouldSaveAndReturnDto() {
        when(playerStatisticRepository.save(any(PlayerStatistic.class))).thenReturn(playerStatistic);

        PlayerStatisticDto result = playerStatisticService.save(playerStatisticDto);

        assertNotNull(result);
        assertEquals(playerStatisticDto, result);
        verify(playerStatisticRepository, times(1)).save(any(PlayerStatistic.class));
        verify(analyticClient, times(1)).clearPlayerStatisticCache(playerId, season);
        verify(analyticClient, times(1)).clearTeamStatisticCache(teamId, season);
    }

    @Test
    void update_existingId_shouldUpdateAndReturnDto() {
        when(playerStatisticRepository.findById(playerStatistic.getId())).thenReturn(Optional.of(playerStatistic));
        when(playerStatisticRepository.save(any(PlayerStatistic.class))).thenReturn(playerStatistic);

        PlayerStatisticDto result = playerStatisticService.update(playerStatistic.getId(), playerStatisticDto);

        assertNotNull(result);
        assertEquals(playerStatisticDto, result);
        verify(playerStatisticRepository, times(1)).findById(playerStatistic.getId());
        verify(playerStatisticRepository, times(1)).save(any(PlayerStatistic.class));
        verify(analyticClient, times(1)).clearPlayerStatisticCache(playerId, season);
        verify(analyticClient, times(1)).clearTeamStatisticCache(teamId, season);
    }

    @Test
    void update_nonExistingId_shouldThrowException() {
        when(playerStatisticRepository.findById(playerStatistic.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> playerStatisticService.update(playerStatistic.getId(), playerStatisticDto));
        verify(playerStatisticRepository, times(1)).findById(playerStatistic.getId());
    }

    @Test
    void delete_existingId_shouldDeleteAndCleanCache() {
        when(playerStatisticRepository.findById(playerStatistic.getId())).thenReturn(Optional.of(playerStatistic));

        playerStatisticService.delete(playerStatistic.getId());

        verify(playerStatisticRepository, times(1)).findById(playerStatistic.getId());
        verify(playerStatisticRepository, times(1)).deleteById(playerStatistic.getId());
        verify(analyticClient, times(1)).clearPlayerStatisticCache(playerId, season);
        verify(analyticClient, times(1)).clearTeamStatisticCache(teamId, season);
    }

    @Test
    void delete_nonExistingId_shouldThrowException() {
        when(playerStatisticRepository.findById(playerStatistic.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> playerStatisticService.delete(playerStatistic.getId()));
        verify(playerStatisticRepository, times(1)).findById(playerStatistic.getId());
    }
}

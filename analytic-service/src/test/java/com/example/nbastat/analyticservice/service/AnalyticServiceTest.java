package com.example.nbastat.analyticservice.service;

import com.example.nbastat.analyticservice.model.dto.PlayerDto;
import com.example.nbastat.analyticservice.model.dto.PlayerStatisticAvgDto;
import com.example.nbastat.analyticservice.model.dto.StatisticAvgDto;
import com.example.nbastat.analyticservice.model.dto.TeamDto;
import com.example.nbastat.analyticservice.model.dto.TeamStatisticAvgDto;
import com.example.nbastat.analyticservice.repos.PlayerStatisticRepository;
import com.example.nbastat.analyticservice.service.external.PlayerClient;
import com.example.nbastat.analyticservice.service.external.TeamClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Year;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnalyticServiceTest {

    @Mock
    private PlayerClient playerClient;

    @Mock
    private TeamClient teamClient;

    @Mock
    private PlayerStatisticRepository playerStatisticRepository;

    @InjectMocks
    private AnalyticService analyticService;

    @Test
    void testGetAveragePlayerStatistic() {
        UUID playerId = UUID.randomUUID();
        Year season = Year.now();
        PlayerDto playerDto = new PlayerDto();
        StatisticAvgDto statisticAvgDto = new StatisticAvgDto();
        PlayerStatisticAvgDto playerStatisticAvgDto = new PlayerStatisticAvgDto(new PlayerDto(null, null, null));

        when(playerClient.getShortPlayer(playerId)).thenReturn(playerDto);
        when(playerStatisticRepository.findAverageStatisticsByPlayerAndSeason(playerId, season)).thenReturn(statisticAvgDto);

        PlayerStatisticAvgDto result = analyticService.getAveragePlayerStatistic(playerId, season);

        assertNotNull(result);
        assertEquals(playerStatisticAvgDto, result);
        verify(playerClient).getShortPlayer(playerId);
        verify(playerStatisticRepository).findAverageStatisticsByPlayerAndSeason(playerId, season);
    }

    @Test
    void testGetAverageTeamStatistic() {
        UUID teamId = UUID.randomUUID();
        Year season = Year.now();
        TeamDto teamDto = new TeamDto();
        StatisticAvgDto statisticAvgDto = new StatisticAvgDto();
        TeamStatisticAvgDto teamStatisticAvgDto = new TeamStatisticAvgDto(new TeamDto(null,null));

        when(teamClient.getTeamById(teamId)).thenReturn(teamDto);
        when(playerStatisticRepository.findAverageStatisticsByTeamAndSeason(teamId, season)).thenReturn(statisticAvgDto);

        TeamStatisticAvgDto result = analyticService.getAverageTeamStatistic(teamId, season);

        assertNotNull(result);
        assertEquals(teamStatisticAvgDto, result);
        verify(teamClient).getTeamById(teamId);
        verify(playerStatisticRepository).findAverageStatisticsByTeamAndSeason(teamId, season);
    }

    @Test
    void testClearPlayerStatisticsCache() {
        UUID playerId = UUID.randomUUID();
        Year season = Year.now();

        analyticService.clearPlayerStatisticsCache(playerId, season);
    }

    @Test
    void testClearTeamStatisticsCache() {
        UUID teamId = UUID.randomUUID();
        Year season = Year.now();

        analyticService.clearTeamStatisticsCache(teamId, season);
    }
}

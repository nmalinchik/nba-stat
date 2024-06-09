package com.example.nbastat.analyticservice.service;

import com.example.nbastat.analyticservice.exception.DataNotFoundException;
import com.example.nbastat.analyticservice.mapper.AvgStatisticMapper;
import com.example.nbastat.analyticservice.model.dto.PlayerDto;
import com.example.nbastat.analyticservice.model.dto.PlayerStatisticAvgDto;
import com.example.nbastat.analyticservice.model.dto.StatisticAvgDto;
import com.example.nbastat.analyticservice.model.dto.TeamDto;
import com.example.nbastat.analyticservice.model.dto.TeamStatisticAvgDto;
import com.example.nbastat.analyticservice.repos.PlayerStatisticRepository;
import com.example.nbastat.analyticservice.service.external.PlayerClient;
import com.example.nbastat.analyticservice.service.external.TeamClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnalyticService {

    private final PlayerClient playerClient;
    private final TeamClient teamClient;
    private final PlayerStatisticRepository playerStatisticRepository;

    @Cacheable(value = "playerStatistics", key = "#playerId + '-' + #season")
    public PlayerStatisticAvgDto getAveragePlayerStatistic(UUID playerId, Year season) {
        log.info("Get statistics for playerId: {} season: {}", playerId, season);
        PlayerDto playerDto = playerClient.getShortPlayer(playerId);
        StatisticAvgDto averageStat;
        try{
            averageStat = playerStatisticRepository.findAverageStatisticsByPlayerAndSeason(playerId, season);
        } catch (Exception e){
            throw new DataNotFoundException("Data for player " + playerId + " and season " + season + " not found");
        }
        return AvgStatisticMapper.INSTANCE.toPlayerStatisticAvgDto(averageStat, playerDto);
    }

    @Cacheable(value = "teamStatistics", key = "#teamId + '-' + #season")
    public TeamStatisticAvgDto getAverageTeamStatistic(UUID teamId, Year season) {
        TeamDto teamDto = teamClient.getTeamById(teamId);
        StatisticAvgDto averageStat;
        try{
            averageStat = playerStatisticRepository.findAverageStatisticsByTeamAndSeason(teamId, season);
        } catch (Exception e){
            throw new DataNotFoundException("Data for team " + teamId + " and season " + season + " not found");
        }
        return AvgStatisticMapper.INSTANCE.toTeamStatisticAvgDto(averageStat, teamDto);
    }

    @CacheEvict(value = "playerStatistics", key = "#playerId + '-' + #season")
    public void clearPlayerStatisticsCache(UUID playerId, Year season) {
        log.info("Clear cache for player {} and season {}", playerId, season);
    }

    @CacheEvict(value = "teamStatistics", key = "#teamId + '-' + #season")
    public void clearTeamStatisticsCache(UUID teamId, Year season) {
        log.info("Clear cache for team {} and season {}", teamId, season);
    }

}

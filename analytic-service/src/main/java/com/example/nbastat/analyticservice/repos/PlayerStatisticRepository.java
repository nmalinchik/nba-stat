package com.example.nbastat.analyticservice.repos;

import com.example.nbastat.analyticservice.model.PlayerStatistic;
import com.example.nbastat.analyticservice.model.dto.StatisticAvgDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Year;
import java.util.UUID;

@Repository
public interface PlayerStatisticRepository extends JpaRepository<PlayerStatistic, UUID> {

    @Query("SELECT new com.example.nbastat.analyticservice.model.dto.StatisticAvgDto(" +
            "AVG(ps.points), AVG(ps.rebounds), AVG(ps.assists), AVG(ps.steals), " +
            "AVG(ps.blocks), AVG(ps.fouls), AVG(ps.turnovers), AVG(ps.minutesPlayed)) " +
            "FROM PlayerStatistic ps WHERE ps.playerId = :playerId AND ps.season = :season")
    StatisticAvgDto findAverageStatisticsByPlayerAndSeason(@Param("playerId") UUID playerId, @Param("season") Year season);

    @Query("SELECT new com.example.nbastat.analyticservice.model.dto.StatisticAvgDto(" +
            "AVG(ps.points), AVG(ps.rebounds), AVG(ps.assists), AVG(ps.steals), " +
            "AVG(ps.blocks), AVG(ps.fouls), AVG(ps.turnovers), AVG(ps.minutesPlayed)) " +
            "FROM PlayerStatistic ps WHERE ps.teamId = :teamId AND ps.season = :season")
    StatisticAvgDto findAverageStatisticsByTeamAndSeason(@Param("teamId") UUID teamId, @Param("season") Year season);
}

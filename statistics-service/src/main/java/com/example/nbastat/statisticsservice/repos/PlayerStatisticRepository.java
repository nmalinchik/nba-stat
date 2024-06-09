package com.example.nbastat.statisticsservice.repos;

import com.example.nbastat.statisticsservice.model.PlayerStatistic;
import org.springframework.data.repository.CrudRepository;

import java.time.Year;
import java.util.List;
import java.util.UUID;

public interface PlayerStatisticRepository extends CrudRepository<PlayerStatistic, UUID> {

    List<PlayerStatistic> findByPlayerIdAndSeason(UUID playerId, Year season);
    List<PlayerStatistic> findByTeamIdAndSeason(UUID id, Year season);

}

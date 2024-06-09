package com.example.nbastat.analyticservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TeamStatisticAvgDto extends StatisticAvgDto {
    private TeamDto teamDto;

    public TeamStatisticAvgDto(double avgPoints, double avgRebounds, double avgAssists, double avgSteals, double avgBlocks,
                               double avgFouls, double avgTurnovers, double avgMinutesPlayed, TeamDto teamDto) {
        super(avgPoints, avgRebounds, avgAssists, avgSteals, avgBlocks, avgFouls, avgTurnovers, avgMinutesPlayed);
        this.teamDto = teamDto;
    }
}
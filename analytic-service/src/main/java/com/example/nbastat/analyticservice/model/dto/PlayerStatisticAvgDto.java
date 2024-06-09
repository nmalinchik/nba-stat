package com.example.nbastat.analyticservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PlayerStatisticAvgDto extends StatisticAvgDto {
    private PlayerDto playerDto;

    public PlayerStatisticAvgDto(double avgPoints, double avgRebounds, double avgAssists, double avgSteals, double avgBlocks,
                                 double avgFouls, double avgTurnovers, double avgMinutesPlayed, PlayerDto playerDto) {
        super(avgPoints, avgRebounds, avgAssists, avgSteals, avgBlocks, avgFouls, avgTurnovers, avgMinutesPlayed);
        this.playerDto = playerDto;
    }
}
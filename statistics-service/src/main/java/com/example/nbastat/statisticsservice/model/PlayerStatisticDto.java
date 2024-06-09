package com.example.nbastat.statisticsservice.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerStatisticDto {

    private UUID id;
    @NotNull
    private UUID playerId;
    @NotNull
    private UUID teamId;

    @Positive
    private Integer points;
    @Positive
    private Integer rebounds;
    @Positive
    private Integer assists;
    @Positive
    private Integer steals;
    @Positive
    private Integer blocks;
    @Max(6)
    @Positive
    private Integer fouls;
    @Positive
    private Integer turnovers;
    @Min(0)
    @Max(48)
    private Double minutesPlayed;

    private Year season;
}

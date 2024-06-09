package com.example.nbastat.analyticservice.model.dto;

import com.example.nbastat.analyticservice.common.DoubleToTwoDecimalSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticAvgDto {
    @JsonSerialize(using = DoubleToTwoDecimalSerializer.class)
    private double avgPoints;
    @JsonSerialize(using = DoubleToTwoDecimalSerializer.class)
    private double avgRebounds;
    @JsonSerialize(using = DoubleToTwoDecimalSerializer.class)
    private double avgAssists;
    @JsonSerialize(using = DoubleToTwoDecimalSerializer.class)
    private double avgSteals;
    @JsonSerialize(using = DoubleToTwoDecimalSerializer.class)
    private double avgBlocks;
    @JsonSerialize(using = DoubleToTwoDecimalSerializer.class)
    private double avgFouls;
    @JsonSerialize(using = DoubleToTwoDecimalSerializer.class)
    private double avgTurnovers;
    @JsonSerialize(using = DoubleToTwoDecimalSerializer.class)
    private double avgMinutesPlayed;
}
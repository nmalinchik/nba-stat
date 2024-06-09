package com.example.nbastat.analyticservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.Year;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "statistic")
public class PlayerStatistic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private UUID playerId;
    private UUID teamId;
    private Integer points;
    private Integer rebounds;
    private Integer assists;
    private Integer steals;
    private Integer blocks;
    private Integer fouls;
    private Integer turnovers;
    private Double minutesPlayed;
    private Year season;
}

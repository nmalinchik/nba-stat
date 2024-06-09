package com.example.nbastat.statisticsservice.controllers;

import com.example.nbastat.statisticsservice.model.PlayerStatisticDto;
import com.example.nbastat.statisticsservice.service.PlayerStatisticService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Year;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlayerStatisticController.class)
class PlayerStatisticControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayerStatisticService playerStatisticService;

    private UUID playerId;
    private UUID teamId;
    private PlayerStatisticDto playerStatisticDto;

    @BeforeEach
    void setUp() {
        playerId = UUID.randomUUID();
        teamId = UUID.randomUUID();
        playerStatisticDto = new PlayerStatisticDto(
                UUID.randomUUID(), playerId, teamId, 25, 5, 10, 2, 3, 2, 4, 35.0, Year.of(2023)
        );
    }

    @Test
    void getPlayerStatistic_shouldReturnPlayerStatisticDto() throws Exception {
        when(playerStatisticService.getById(playerStatisticDto.getId())).thenReturn(playerStatisticDto);

        mockMvc.perform(get("/statistic/{id}", playerStatisticDto.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(playerStatisticDto.getId().toString()))
                .andExpect(jsonPath("$.playerId").value(playerStatisticDto.getPlayerId().toString()))
                .andExpect(jsonPath("$.teamId").value(playerStatisticDto.getTeamId().toString()))
                .andExpect(jsonPath("$.points").value(playerStatisticDto.getPoints()))
                .andExpect(jsonPath("$.rebounds").value(playerStatisticDto.getRebounds()))
                .andExpect(jsonPath("$.assists").value(playerStatisticDto.getAssists()))
                .andExpect(jsonPath("$.steals").value(playerStatisticDto.getSteals()))
                .andExpect(jsonPath("$.blocks").value(playerStatisticDto.getBlocks()))
                .andExpect(jsonPath("$.turnovers").value(playerStatisticDto.getTurnovers()))
                .andExpect(jsonPath("$.fouls").value(playerStatisticDto.getFouls()))
                .andExpect(jsonPath("$.minutesPlayed").value(playerStatisticDto.getMinutesPlayed()))
                .andExpect(jsonPath("$.season").value(playerStatisticDto.getSeason().toString()));

        verify(playerStatisticService, times(1)).getById(playerStatisticDto.getId());
    }

    @Test
    void getPlayerStatisticForSeason_shouldReturnListOfPlayerStatisticDtos() throws Exception {
        when(playerStatisticService.getPlayerStatistics(playerId, Year.of(2023))).thenReturn(List.of(playerStatisticDto));

        mockMvc.perform(get("/statistic/player/{id}/{season}", playerId, 2023))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(playerStatisticDto.getId().toString()))
                .andExpect(jsonPath("$[0].playerId").value(playerStatisticDto.getPlayerId().toString()))
                .andExpect(jsonPath("$[0].teamId").value(playerStatisticDto.getTeamId().toString()))
                .andExpect(jsonPath("$[0].points").value(playerStatisticDto.getPoints()))
                .andExpect(jsonPath("$[0].rebounds").value(playerStatisticDto.getRebounds()))
                .andExpect(jsonPath("$[0].assists").value(playerStatisticDto.getAssists()))
                .andExpect(jsonPath("$[0].steals").value(playerStatisticDto.getSteals()))
                .andExpect(jsonPath("$[0].blocks").value(playerStatisticDto.getBlocks()))
                .andExpect(jsonPath("$[0].turnovers").value(playerStatisticDto.getTurnovers()))
                .andExpect(jsonPath("$[0].fouls").value(playerStatisticDto.getFouls()))
                .andExpect(jsonPath("$[0].minutesPlayed").value(playerStatisticDto.getMinutesPlayed()))
                .andExpect(jsonPath("$[0].season").value(playerStatisticDto.getSeason().toString()));

        verify(playerStatisticService, times(1)).getPlayerStatistics(playerId, Year.of(2023));
    }

    @Test
    void getTeamStatisticForSeason_shouldReturnListOfPlayerStatisticDtos() throws Exception {
        when(playerStatisticService.getTeamStatistics(teamId, Year.of(2023))).thenReturn(List.of(playerStatisticDto));

        mockMvc.perform(get("/statistic/team/{id}/{season}", teamId, 2023))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(playerStatisticDto.getId().toString()))
                .andExpect(jsonPath("$[0].playerId").value(playerStatisticDto.getPlayerId().toString()))
                .andExpect(jsonPath("$[0].teamId").value(playerStatisticDto.getTeamId().toString()))
                .andExpect(jsonPath("$[0].points").value(playerStatisticDto.getPoints()))
                .andExpect(jsonPath("$[0].rebounds").value(playerStatisticDto.getRebounds()))
                .andExpect(jsonPath("$[0].assists").value(playerStatisticDto.getAssists()))
                .andExpect(jsonPath("$[0].steals").value(playerStatisticDto.getSteals()))
                .andExpect(jsonPath("$[0].blocks").value(playerStatisticDto.getBlocks()))
                .andExpect(jsonPath("$[0].turnovers").value(playerStatisticDto.getTurnovers()))
                .andExpect(jsonPath("$[0].fouls").value(playerStatisticDto.getFouls()))
                .andExpect(jsonPath("$[0].minutesPlayed").value(playerStatisticDto.getMinutesPlayed()))
                .andExpect(jsonPath("$[0].season").value(playerStatisticDto.getSeason().toString()));

        verify(playerStatisticService, times(1)).getTeamStatistics(teamId, Year.of(2023));
    }

    @Test
    void savePlayerStatistic_shouldSaveAndReturnPlayerStatisticDto() throws Exception {
        when(playerStatisticService.save(any(PlayerStatisticDto.class))).thenReturn(playerStatisticDto);

        mockMvc.perform(post("/statistic")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"playerId\":\"" + playerStatisticDto.getPlayerId() + "\",\"teamId\":\"" + playerStatisticDto.getTeamId() + "\",\"points\":25,\"rebounds\":5,\"assists\":10,\"steals\":2,\"blocks\":3,\"turnovers\":2,\"fouls\":4,\"minutesPlayed\":35.0,\"season\":2023}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(playerStatisticDto.getId().toString()))
                .andExpect(jsonPath("$.playerId").value(playerStatisticDto.getPlayerId().toString()))
                .andExpect(jsonPath("$.teamId").value(playerStatisticDto.getTeamId().toString()))
                .andExpect(jsonPath("$.points").value(playerStatisticDto.getPoints()))
                .andExpect(jsonPath("$.rebounds").value(playerStatisticDto.getRebounds()))
                .andExpect(jsonPath("$.assists").value(playerStatisticDto.getAssists()))
                .andExpect(jsonPath("$.steals").value(playerStatisticDto.getSteals()))
                .andExpect(jsonPath("$.blocks").value(playerStatisticDto.getBlocks()))
                .andExpect(jsonPath("$.turnovers").value(playerStatisticDto.getTurnovers()))
                .andExpect(jsonPath("$.fouls").value(playerStatisticDto.getFouls()))
                .andExpect(jsonPath("$.minutesPlayed").value(playerStatisticDto.getMinutesPlayed()))
                .andExpect(jsonPath("$.season").value(playerStatisticDto.getSeason().toString()));

        verify(playerStatisticService, times(1)).save(any(PlayerStatisticDto.class));
    }

    @Test
    void updatePlayerStatistic_shouldUpdateAndReturnPlayerStatisticDto() throws Exception {
        when(playerStatisticService.update(eq(playerStatisticDto.getId()), any(PlayerStatisticDto.class))).thenReturn(playerStatisticDto);

        mockMvc.perform(put("/statistic/{id}", playerStatisticDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"playerId\":\"" + playerStatisticDto.getPlayerId() + "\",\"teamId\":\"" + playerStatisticDto.getTeamId() + "\",\"points\":25,\"rebounds\":5,\"assists\":10,\"steals\":2,\"blocks\":3,\"turnovers\":2,\"fouls\":4,\"minutesPlayed\":35.0,\"season\":2023}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(playerStatisticDto.getId().toString()))
                .andExpect(jsonPath("$.playerId").value(playerStatisticDto.getPlayerId().toString()))
                .andExpect(jsonPath("$.teamId").value(playerStatisticDto.getTeamId().toString()))
                .andExpect(jsonPath("$.points").value(playerStatisticDto.getPoints()))
                .andExpect(jsonPath("$.rebounds").value(playerStatisticDto.getRebounds()))
                .andExpect(jsonPath("$.assists").value(playerStatisticDto.getAssists()))
                .andExpect(jsonPath("$.steals").value(playerStatisticDto.getSteals()))
                .andExpect(jsonPath("$.blocks").value(playerStatisticDto.getBlocks()))
                .andExpect(jsonPath("$.turnovers").value(playerStatisticDto.getTurnovers()))
                .andExpect(jsonPath("$.fouls").value(playerStatisticDto.getFouls()))
                .andExpect(jsonPath("$.minutesPlayed").value(playerStatisticDto.getMinutesPlayed()))
                .andExpect(jsonPath("$.season").value(playerStatisticDto.getSeason().toString()));

        verify(playerStatisticService, times(1)).update(eq(playerStatisticDto.getId()), any(PlayerStatisticDto.class));
    }

    @Test
    void deletePlayerStatistic_shouldDeletePlayerStatistic() throws Exception {
        mockMvc.perform(delete("/statistic/{id}", playerStatisticDto.getId()))
                .andExpect(status().isNoContent());

        verify(playerStatisticService, times(1)).delete(playerStatisticDto.getId());
    }
}

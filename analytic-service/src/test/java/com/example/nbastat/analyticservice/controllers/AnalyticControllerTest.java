package com.example.nbastat.analyticservice.controllers;

import com.example.nbastat.analyticservice.model.dto.PlayerDto;
import com.example.nbastat.analyticservice.model.dto.PlayerStatisticAvgDto;
import com.example.nbastat.analyticservice.model.dto.TeamDto;
import com.example.nbastat.analyticservice.model.dto.TeamStatisticAvgDto;
import com.example.nbastat.analyticservice.service.AnalyticService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Year;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AnalyticController.class)
class AnalyticControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnalyticService analyticService;

    @Test
    void testGetPlayerStatistic() throws Exception {
        UUID playerId = UUID.randomUUID();
        Year season = Year.now();
        PlayerDto playerDto = new PlayerDto(playerId, "John", "Doe");
        PlayerStatisticAvgDto playerStatisticAvgDto = new PlayerStatisticAvgDto(25.5, 8.3, 5.7, 1.2, 0.9, 2.1, 3.4, 35.6, playerDto);

        when(analyticService.getAveragePlayerStatistic(any(UUID.class), any(Year.class))).thenReturn(playerStatisticAvgDto);

        mockMvc.perform(get("/analytic/player/{id}/{season}", playerId, season)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.avgPoints").value(25.5))
                .andExpect(jsonPath("$.avgRebounds").value(8.3))
                .andExpect(jsonPath("$.avgAssists").value(5.7))
                .andExpect(jsonPath("$.playerDto.id").value(playerId.toString()))
                .andExpect(jsonPath("$.playerDto.firstName").value("John"))
                .andExpect(jsonPath("$.playerDto.lastName").value("Doe"));

        verify(analyticService, times(1)).getAveragePlayerStatistic(playerId, season);
    }

    @Test
    void testGetTeamStatistic() throws Exception {
        UUID teamId = UUID.randomUUID();
        Year season = Year.now();
        TeamDto teamDto = new TeamDto(teamId, "Lakers");
        TeamStatisticAvgDto teamStatisticAvgDto = new TeamStatisticAvgDto(102.5, 42.3, 25.7, 7.2, 4.9, 19.1, 12.4, 48.6, teamDto);

        when(analyticService.getAverageTeamStatistic(any(UUID.class), any(Year.class))).thenReturn(teamStatisticAvgDto);

        mockMvc.perform(get("/analytic/team/{id}/{season}", teamId, season)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.avgPoints").value(102.5))
                .andExpect(jsonPath("$.avgRebounds").value(42.3))
                .andExpect(jsonPath("$.avgAssists").value(25.7))
                .andExpect(jsonPath("$.teamDto.id").value(teamId.toString()))
                .andExpect(jsonPath("$.teamDto.name").value("Lakers"));

        verify(analyticService, times(1)).getAverageTeamStatistic(teamId, season);
    }

    @Test
    void testClearPlayerStatisticCache() throws Exception {
        UUID playerId = UUID.randomUUID();
        Year season = Year.now();

        doNothing().when(analyticService).clearPlayerStatisticsCache(any(UUID.class), any(Year.class));

        mockMvc.perform(delete("/analytic/player/{id}/{season}", playerId, season)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(analyticService, times(1)).clearPlayerStatisticsCache(playerId, season);
    }

    @Test
    void testClearTeamStatisticCache() throws Exception {
        UUID teamId = UUID.randomUUID();
        Year season = Year.now();

        doNothing().when(analyticService).clearTeamStatisticsCache(any(UUID.class), any(Year.class));

        mockMvc.perform(delete("/analytic/team/{id}/{season}", teamId, season)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(analyticService, times(1)).clearTeamStatisticsCache(teamId, season);
    }
}

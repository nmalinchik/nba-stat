package com.example.nbastat.playerservice.controllers;


import com.example.nbastat.playerservice.common.GlobalExceptionHandler;
import com.example.nbastat.playerservice.model.Player;
import com.example.nbastat.playerservice.model.PlayerDto;
import com.example.nbastat.playerservice.model.TeamDto;
import com.example.nbastat.playerservice.services.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PlayerControllerTest {

    @Mock
    private PlayerService playerService;

    @InjectMocks
    private PlayerController playerController;

    private MockMvc mockMvc;
    private Player player;
    private PlayerDto playerDto;
    private UUID playerId;
    private UUID teamId;

    @BeforeEach
    void setUp() {
        playerId = UUID.randomUUID();
        teamId = UUID.randomUUID();
        player = new Player(playerId, "John", "Doe", teamId);
        playerDto = new PlayerDto(playerId, "John", "Doe", new TeamDto(teamId, "Team Name"));
        mockMvc = MockMvcBuilders
                .standaloneSetup(playerController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void getPlayer_existingId_shouldReturnPlayerDto() throws Exception {
        when(playerService.getPlayer(playerId)).thenReturn(playerDto);
        mockMvc.perform(get("/player/{id}", playerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(playerId.toString()))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.teamDto.id").value(teamId.toString()))
                .andExpect(jsonPath("$.teamDto.name").value("Team Name"));
        verify(playerService, times(1)).getPlayer(playerId);
    }

    @Test
    void getShortPlayer_existingId_shouldReturnPlayer() throws Exception {
        when(playerService.getShortPlayer(playerId)).thenReturn(player);
        mockMvc.perform(get("/player/short/{id}", playerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(playerId.toString()))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.teamId").value(teamId.toString()));
        verify(playerService, times(1)).getShortPlayer(playerId);
    }

    @Test
    void getTeamPlayers_existingTeamId_shouldReturnPlayerList() throws Exception {
        when(playerService.getTeamPlayers(teamId)).thenReturn(List.of(player));
        mockMvc.perform(get("/player/team/{id}", teamId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(playerId.toString()))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"))
                .andExpect(jsonPath("$[0].teamId").value(teamId.toString()));
        verify(playerService, times(1)).getTeamPlayers(teamId);
    }

    @Test
    void savePlayer_shouldReturnSavedPlayerDto() throws Exception {
        when(playerService.savePlayer(any(PlayerDto.class))).thenReturn(playerDto);
        mockMvc.perform(post("/player")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\": \"John\", \"lastName\": \"Doe\", \"teamDto\": {\"id\": \"" + teamId + "\", \"name\": \"Team Name\"}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(playerId.toString()))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.teamDto.id").value(teamId.toString()))
                .andExpect(jsonPath("$.teamDto.name").value("Team Name"));
        verify(playerService, times(1)).savePlayer(any(PlayerDto.class));
    }

    @Test
    void updatePlayer_existingId_shouldReturnUpdatedPlayerDto() throws Exception {
        when(playerService.updatePlayer(eq(playerId), any(PlayerDto.class))).thenReturn(playerDto);
        mockMvc.perform(put("/player/{id}", playerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\": \"John\", \"lastName\": \"Doe\", \"teamDto\": {\"id\": \"" + teamId + "\", \"name\": \"Team Name\"}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(playerId.toString()))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.teamDto.id").value(teamId.toString()))
                .andExpect(jsonPath("$.teamDto.name").value("Team Name"));
        verify(playerService, times(1)).updatePlayer(eq(playerId), any(PlayerDto.class));
    }

    @Test
    void deletePlayer_existingId_shouldDeletePlayer() throws Exception {
        doNothing().when(playerService).deletePlayer(playerId);
        mockMvc.perform(delete("/player/{id}", playerId))
                .andExpect(status().isNoContent());
        verify(playerService, times(1)).deletePlayer(playerId);
    }

    @Test
    void savePlayer_invalidData_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/player")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\": \"\", \"lastName\": \"\", \"teamDto\": {\"id\": \"" + teamId + "\"}}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.firstName").value("must not be blank"))
                .andExpect(jsonPath("$.lastName").value("must not be blank"));
    }

    @Test
    void updatePlayer_invalidData_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(put("/player/{id}", playerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\": \"\", \"lastName\": \"\", \"teamDto\": {\"id\": \"" + teamId + "\"}}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.firstName").value("must not be blank"))
                .andExpect(jsonPath("$.lastName").value("must not be blank"));
    }
}
package com.example.nbastat.teamservice.controllers;

import com.example.nbastat.teamservice.common.GlobalExceptionHandler;
import com.example.nbastat.teamservice.model.Team;
import com.example.nbastat.teamservice.model.TeamDto;
import com.example.nbastat.teamservice.services.TeamService;
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
class TeamControllerTest {

    @Mock
    private TeamService teamService;

    @InjectMocks
    private TeamController teamController;

    private MockMvc mockMvc;
    private TeamDto teamDto;
    private UUID teamId;
    private Team team;

    @BeforeEach
    void setUp() {
        teamId = UUID.randomUUID();
        team = new Team(teamId, "Team Name");
        teamDto = new TeamDto(teamId, "Team Name", List.of());
        mockMvc = MockMvcBuilders
                .standaloneSetup(teamController)
                .setControllerAdvice(new GlobalExceptionHandler()) // Registering the exception handler
                .build();
    }

    @Test
    void getShortTeamById_existingId_shouldReturnTeam() throws Exception {
        when(teamService.getTeamEntity(teamId)).thenReturn(team);
        mockMvc.perform(get("/team/short/{id}", teamId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(teamId.toString()))
                .andExpect(jsonPath("$.name").value("Team Name"));
        verify(teamService, times(1)).getTeamEntity(teamId);
    }

    @Test
    void getTeam_existingId_shouldReturnTeamDto() throws Exception {
        when(teamService.getTeam(teamId)).thenReturn(teamDto);
        mockMvc.perform(get("/team/{id}", teamId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(teamId.toString()))
                .andExpect(jsonPath("$.name").value("Team Name"));
        verify(teamService, times(1)).getTeam(teamId);
    }

    @Test
    void saveTeam_shouldReturnSavedTeamDto() throws Exception {
        when(teamService.saveTeam(any(TeamDto.class))).thenReturn(teamDto);
        mockMvc.perform(post("/team")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Team Name\", \"players\": []}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(teamId.toString()))
                .andExpect(jsonPath("$.name").value("Team Name"));
        verify(teamService, times(1)).saveTeam(any(TeamDto.class));
    }

    @Test
    void updateTeam_existingId_shouldReturnUpdatedTeamDto() throws Exception {
        when(teamService.updateTeam(eq(teamId), any(TeamDto.class))).thenReturn(teamDto);
        mockMvc.perform(put("/team/{id}", teamId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Team Name\", \"players\": []}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(teamId.toString()))
                .andExpect(jsonPath("$.name").value("Team Name"));
        verify(teamService, times(1)).updateTeam(eq(teamId), any(TeamDto.class));
    }

    @Test
    void deleteTeam_existingId_shouldDeleteTeam() throws Exception {
        doNothing().when(teamService).deleteTeam(teamId);
        mockMvc.perform(delete("/team/{id}", teamId))
                .andExpect(status().isNoContent());
        verify(teamService, times(1)).deleteTeam(teamId);
    }

    @Test
    void saveTeam_invalidData_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/team")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"\", \"players\": []}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("must not be empty"));
    }

    @Test
    void updateTeam_invalidData_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(put("/team/{id}", teamId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"\", \"players\": []}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("must not be empty"));
    }
}

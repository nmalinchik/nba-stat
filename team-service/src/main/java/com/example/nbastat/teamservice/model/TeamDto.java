package com.example.nbastat.teamservice.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamDto {

    private UUID id;

    @NotEmpty
    private String name;

    private List<PlayerDto> players;
}

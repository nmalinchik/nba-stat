package com.example.nbastat.playerservice.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDto {
    private UUID id;

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

    @Valid
    private TeamDto teamDto;
}

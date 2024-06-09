package com.example.nbastat.teamservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDto {
    private UUID id;

    private String firstName;
    private String lastName;
}

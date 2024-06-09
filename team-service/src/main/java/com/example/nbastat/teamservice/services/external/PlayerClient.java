package com.example.nbastat.teamservice.services.external;

import com.example.nbastat.teamservice.exception.ServiceUnAvailableException;
import com.example.nbastat.teamservice.model.PlayerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "player-service", fallback = PlayerClient.PlayerClientFallback.class
)
public interface PlayerClient {

    @GetMapping("/player/team/{id}")
    List<PlayerDto> getTeamPlayers(@PathVariable UUID id);

    @Component
    class PlayerClientFallback implements PlayerClient {

        @Override
        public List<PlayerDto> getTeamPlayers(UUID id) {
            throw new ServiceUnAvailableException("Player service is not available");
        }
    }
}

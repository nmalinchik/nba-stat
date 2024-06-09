package com.example.nbastat.analyticservice.service.external;

import com.example.nbastat.analyticservice.exception.ServiceUnAvailableException;
import com.example.nbastat.analyticservice.model.dto.PlayerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "player-service", fallback = PlayerClient.PlayerClientFallback.class
)
public interface PlayerClient {

    @GetMapping("/player/short/{id}")
    PlayerDto getShortPlayer(@PathVariable UUID id);

    @Component
    class PlayerClientFallback implements PlayerClient {

        @Override
        public PlayerDto getShortPlayer(UUID id) {
            throw new ServiceUnAvailableException("Player service is not available");
        }
    }
}

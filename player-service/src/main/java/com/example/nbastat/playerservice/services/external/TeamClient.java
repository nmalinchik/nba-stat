package com.example.nbastat.playerservice.services.external;

import com.example.nbastat.playerservice.exception.ServiceUnAvailableException;
import com.example.nbastat.playerservice.model.TeamDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "team-service", fallback = TeamClient.TeamClientFallback.class
)
public interface TeamClient {

    @GetMapping("/team/short/{id}")
    TeamDto getTeamById(@PathVariable("id") UUID id);

    @Component
    class TeamClientFallback implements TeamClient {

        @Override
        public TeamDto getTeamById(UUID id) {
            throw new ServiceUnAvailableException("Team service is unavailable");
        }
    }
}

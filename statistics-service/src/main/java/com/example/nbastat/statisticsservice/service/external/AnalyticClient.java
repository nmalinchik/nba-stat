package com.example.nbastat.statisticsservice.service.external;

import com.example.nbastat.statisticsservice.exception.ServiceUnAvailableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.Year;
import java.util.UUID;

@FeignClient(name = "analytic-service",
        fallback = AnalyticClient.AnalyticClientFallback.class
)
public interface AnalyticClient {

    @DeleteMapping("/analytic/player/{id}/{season}")
    void clearPlayerStatisticCache(@PathVariable UUID id, @PathVariable Year season);

    @DeleteMapping("/analytic/team/{id}/{season}")
    void clearTeamStatisticCache(@PathVariable UUID id, @PathVariable Year season);

    @Slf4j
    @Component
    class AnalyticClientFallback implements AnalyticClient {

        @Override
        public void clearPlayerStatisticCache(UUID id, Year season) {
            log.error("Analytic service is not available");
            throw new ServiceUnAvailableException("Analytic service is not available");
        }

        @Override
        public void clearTeamStatisticCache(UUID id, Year season) {
            log.error("Analytic service is not available");
            throw new ServiceUnAvailableException("Analytic service is not available");
        }
    }
}

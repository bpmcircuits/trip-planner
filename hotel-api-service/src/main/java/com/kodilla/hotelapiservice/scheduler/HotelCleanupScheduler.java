package com.kodilla.hotelapiservice.scheduler;

import com.kodilla.hotelapiservice.exception.DatabaseOperationException;
import com.kodilla.hotelapiservice.service.HotelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class HotelCleanupScheduler {

    private final HotelService hotelService;
    
    @Value("${hotel.cleanup.enabled:true}")
    private boolean cleanupEnabled;
    @Scheduled(cron = "${hotel.cleanup.cron:0 0 0 * * ?}")
    public void cleanupOutdatedOffers() {
        if (!cleanupEnabled) {
            log.info("Hotel cleanup is disabled");
            return;
        }
        
        log.info("Starting scheduled cleanup of outdated hotel offers");
        try {
            int removedCount = hotelService.removeOutdatedOffers();
            log.info("Scheduled cleanup completed. Removed {} outdated hotel offers", removedCount);
        } catch (DatabaseOperationException e) {
            log.error("Error during scheduled cleanup of outdated hotel offers", e);
        }
    }
}
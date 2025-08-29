package com.kodilla.hotelapiservice.scheduler;

import com.kodilla.hotelapiservice.exception.DatabaseOperationException;
import com.kodilla.hotelapiservice.service.HotelService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HotelCleanupSchedulerTest {

    @Mock
    private HotelService hotelService;

    @InjectMocks
    private HotelCleanupScheduler hotelCleanupScheduler;

    @Test
    void shouldCleanupOutdatedOffers() throws DatabaseOperationException {
        // Given
        ReflectionTestUtils.setField(hotelCleanupScheduler, "cleanupEnabled", true);
        doReturn(5).when(hotelService).removeOutdatedOffers();

        // When
        hotelCleanupScheduler.cleanupOutdatedOffers();

        // Then
        verify(hotelService, times(1)).removeOutdatedOffers();
    }

    @Test
    void shouldNotCleanupWhenDisabled() throws DatabaseOperationException {
        // Given
        ReflectionTestUtils.setField(hotelCleanupScheduler, "cleanupEnabled", false);
        
        // When
        hotelCleanupScheduler.cleanupOutdatedOffers();

        // Then
        verify(hotelService, never()).removeOutdatedOffers();
        
        // Reset for other tests
        ReflectionTestUtils.setField(hotelCleanupScheduler, "cleanupEnabled", true);
    }
}
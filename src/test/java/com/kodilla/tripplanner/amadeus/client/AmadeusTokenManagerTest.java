package com.kodilla.tripplanner.amadeus.client;

import com.kodilla.tripplanner.amadeus.dto.AmadeusTokenResponse;
import com.kodilla.tripplanner.amadeus.properties.AmadeusProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AmadeusTokenManagerTest {

    @Mock
    private AmadeusAuthClient authClient;

    @Mock
    private AmadeusProperties properties;

    @InjectMocks
    private AmadeusTokenManager tokenManager;

    private final String TEST_TOKEN = "test-access-token";
    private final int REFRESH_SKEW_SECONDS = 60;

    @BeforeEach
    void setUp() {
        when(properties.getRefreshSkewSeconds()).thenReturn(REFRESH_SKEW_SECONDS);
    }

    @Test
    void testGetValidToken_FetchNewToken() {
        // Given
        AmadeusTokenResponse mockResponse = new AmadeusTokenResponse(
                TEST_TOKEN,
                "Bearer",
                3600
        );
        when(authClient.fetchToken()).thenReturn(mockResponse);

        // When
        String token = tokenManager.getValidToken();

        // Then
        assertNotNull(token);
        assertEquals(TEST_TOKEN, token);
        verify(authClient, times(1)).fetchToken();
    }

    @Test
    void testGetValidToken_ReuseExistingToken() {
        // Given
        AmadeusTokenResponse mockResponse = new AmadeusTokenResponse(
                TEST_TOKEN,
                "Bearer",
                3600
        );
        when(authClient.fetchToken()).thenReturn(mockResponse);

        // When
        // First call to get a token
        tokenManager.getValidToken();
        
        // Second call should reuse the token
        String token = tokenManager.getValidToken();

        // Then
        assertNotNull(token);
        assertEquals(TEST_TOKEN, token);
        // Verify fetchToken was called only once (for the first call)
        verify(authClient, times(1)).fetchToken();
    }

    @Test
    void testGetValidToken_TokenExpired() throws InterruptedException {
        // Given
        AmadeusTokenResponse firstResponse = new AmadeusTokenResponse(
                "first-token",
                "Bearer",
                1  // Very short expiration to simulate expiration
        );
        
        AmadeusTokenResponse secondResponse = new AmadeusTokenResponse(
                "second-token",
                "Bearer",
                3600
        );
        
        when(authClient.fetchToken())
                .thenReturn(firstResponse)
                .thenReturn(secondResponse);
                
        // Set a very short refresh skew to make the token expire quickly
        when(properties.getRefreshSkewSeconds()).thenReturn(0);

        // When
        // First call to get a token
        tokenManager.getValidToken();
        
        // Wait for the token to expire
        Thread.sleep(1100);  // Wait a bit more than the expiration time
        
        // Second call should get a new token
        String token = tokenManager.getValidToken();

        // Then
        assertNotNull(token);
        assertEquals("second-token", token);
        // Verify fetchToken was called twice
        verify(authClient, times(2)).fetchToken();
    }
}
package com.kodilla.tripplanner.amadeus.client;

import com.kodilla.tripplanner.amadeus.dto.AmadeusTokenResponse;
import com.kodilla.tripplanner.amadeus.properties.AmadeusProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AmadeusAuthClientTest {

    @Mock
    private AmadeusProperties properties;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private AmadeusAuthClient amadeusAuthClient;

    private final String TOKEN_URL = "https://test.api.amadeus.com/v1/security/oauth2/token";
    private final String CLIENT_ID = "test-client-id";
    private final String CLIENT_SECRET = "test-client-secret";

    @BeforeEach
    void setUp() {
        when(properties.getTokenUrl()).thenReturn(TOKEN_URL);
        when(properties.getClientId()).thenReturn(CLIENT_ID);
        when(properties.getClientSecret()).thenReturn(CLIENT_SECRET);
    }

    @Test
    void testFetchToken_Success() {
        // Given
        AmadeusTokenResponse mockResponse = new AmadeusTokenResponse(
                "test-access-token",
                "Bearer",
                3600
        );
        
        when(restTemplate.postForEntity(
                eq(TOKEN_URL),
                any(HttpEntity.class),
                eq(AmadeusTokenResponse.class)))
                .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        // When
        AmadeusTokenResponse result = amadeusAuthClient.fetchToken();

        // Then
        assertNotNull(result);
        assertEquals("test-access-token", result.accessToken());
        assertEquals("Bearer", result.tokenType());
        assertEquals(3600, result.expiresInSeconds());
    }

    @Test
    void testFetchToken_NullResponse() {
        // Given
        when(restTemplate.postForEntity(
                eq(TOKEN_URL),
                any(HttpEntity.class),
                eq(AmadeusTokenResponse.class)))
                .thenReturn(new ResponseEntity<>(null, HttpStatus.OK));

        // When & Then
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            amadeusAuthClient.fetchToken();
        });
        
        assertTrue(exception.getMessage().contains("Empty or not complete response"));
    }

    @Test
    void testFetchToken_HttpClientError() {
        // Given
        when(restTemplate.postForEntity(
                eq(TOKEN_URL),
                any(HttpEntity.class),
                eq(AmadeusTokenResponse.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Unauthorized"));

        // When & Then
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            amadeusAuthClient.fetchToken();
        });
        
        assertTrue(exception.getMessage().contains("Error getting Amadeus token"));
    }
}
package com.kodilla.flightapiservice.amadeus.client;


import com.kodilla.flightapiservice.amadeus.dto.AmadeusTokenResponse;
import com.kodilla.flightapiservice.amadeus.properties.AmadeusProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.concurrent.locks.ReentrantLock;

@Component
@Slf4j
public class AmadeusTokenManager {

    private final AmadeusAuthClient authClient;
    private final AmadeusProperties props;

    private final ReentrantLock lock = new ReentrantLock();
    private volatile String accessToken;
    private volatile Instant expiresAt = Instant.EPOCH;

    public AmadeusTokenManager(AmadeusAuthClient authClient, AmadeusProperties props) {
        this.authClient = authClient;
        this.props = props;
    }

    public String getValidToken() {
        if (isValid()) return accessToken;

        lock.lock();
        try {
            if (isValid()) return accessToken;
            AmadeusTokenResponse r = authClient.fetchToken();
            accessToken = r.accessToken();
            long skew = Math.max(props.getRefreshSkewSeconds(), 0);
            expiresAt = Instant.now().plusSeconds(r.expiresInSeconds() - skew);
            log.info("Amadeus token has been refreshed. Expires at: {}", expiresAt);
            return accessToken;
        } finally {
            lock.unlock();
        }
    }

    private boolean isValid() {
        return accessToken != null && Instant.now().isBefore(expiresAt);
    }
}

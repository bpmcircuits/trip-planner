package com.kodilla.flightapiservice.amadeus.client;

import com.kodilla.flightapiservice.amadeus.dto.AmadeusTokenResponse;
import com.kodilla.flightapiservice.amadeus.properties.AmadeusProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class AmadeusAuthClient {

    private final AmadeusProperties props;
    private final RestTemplate restTemplate;

    public AmadeusAuthClient(AmadeusProperties props,
                             @Qualifier("amadeusAuthRestTemplate") RestTemplate restTemplate) {
        this.props = props;
        this.restTemplate = restTemplate;
    }

    public AmadeusTokenResponse fetchToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "client_credentials");
        form.add("client_id", props.getClientId());
        form.add("client_secret", props.getClientSecret());

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(form, headers);

        try {
            ResponseEntity<AmadeusTokenResponse> resp =
                    restTemplate.postForEntity(props.getTokenUrl(), entity, AmadeusTokenResponse.class);

            AmadeusTokenResponse body = resp.getBody();
            if (body == null || body.accessToken() == null) {
                throw new IllegalStateException("Empty or not complete response of token from Amadeus.");
            }
            return body;
        } catch (HttpClientErrorException e) {
            throw new IllegalStateException("Error getting Amadeus token: "
                    + e.getStatusCode() + " " + e.getResponseBodyAsString(), e);
        }
    }
}

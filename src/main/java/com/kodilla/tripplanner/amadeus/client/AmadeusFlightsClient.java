package com.kodilla.tripplanner.amadeus.client;

import com.kodilla.tripplanner.amadeus.config.AmadeusFlightsConfig;
import com.kodilla.tripplanner.amadeus.dto.AmadeusFlightsAirportSearchRequestDTO;
import com.kodilla.tripplanner.amadeus.dto.AmadeusFlightsAirportSearchResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Component
public class AmadeusFlightsClient {

    private final RestTemplate restTemplate;
    private final AmadeusFlightsConfig config;

    public AmadeusFlightsClient(@Qualifier("amadeusApiRestTemplate") RestTemplate restTemplate,
                                AmadeusFlightsConfig config) {
        this.restTemplate = restTemplate;
        this.config = config;
    }

    public AmadeusFlightsAirportSearchResponseDTO getAirportCode(String query) {
        URI uri = UriComponentsBuilder.fromUriString(config.getAmadeusApiEndpoint()
                + "/v1/reference-data/locations/cities")
                .queryParam("keyword", query)
                .build().encode().toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", config.getAmadeusApiKey());
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<AmadeusFlightsAirportSearchResponseDTO> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                entity,
                AmadeusFlightsAirportSearchResponseDTO.class
        );

        return response.getBody();
    }

    public AmadeusFlightsFlightOffersDataDTO getFlightOffer(AmadeusFlightsAirportSearchRequestDTO requestDTO) {
        URI uri = UriComponentsBuilder.fromUriString(config.getAmadeusApiEndpoint()
                        + "/v2/shopping/flight-offers")
                .queryParam("originLocationCode", requestDTO.originLocationCode())
                .queryParam("destinationLocationCode", requestDTO.destinationLocationCode())
                .queryParam("departureDate", requestDTO.departureDate())
                .queryParam("returnDate", requestDTO.returnDate())
                .queryParam("adults", requestDTO.adults())
                .queryParam("children", requestDTO.children())
                .queryParam("infants", requestDTO.infants())
                .queryParam("currencyCode", requestDTO.currencyCode())
                .build().encode().toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", config.getAmadeusApiKey());
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<AmadeusFlightsFlightOffersDataDTO> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                entity,
                AmadeusFlightsFlightOffersDataDTO.class
        );

        return response.getBody();
    }
}

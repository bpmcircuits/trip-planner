package com.kodilla.tripplanner.rapidapi.kiwiflights.client;

import com.kodilla.tripplanner.dto.FlightRequestDTO;
import com.kodilla.tripplanner.rapidapi.kiwiflights.config.KiwiConfig;
import com.kodilla.tripplanner.rapidapi.kiwiflights.dto.KiwiSearchResponseOneWayDTO;
import com.kodilla.tripplanner.rapidapi.kiwiflights.dto.KiwiSearchResponseRoundTripDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Component
@RequiredArgsConstructor
public class KiwiClient {

    private final RestTemplate restTemplate;
    private final KiwiConfig kiwiConfig;

    public KiwiSearchResponseOneWayDTO getOneWayFlights(FlightRequestDTO flightRequestDTO) {
        URI url = getUri(FlightType.ONE_WAY, flightRequestDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-rapidapi-key", kiwiConfig.getKiwiApiKey());
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<KiwiSearchResponseOneWayDTO> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                KiwiSearchResponseOneWayDTO.class
        );

        return response.getBody();
    }

    public KiwiSearchResponseRoundTripDTO getRoundTripFlights(FlightRequestDTO flightRequestDTO) {
        URI url = getUri(FlightType.ROUND_TRIP, flightRequestDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-rapidapi-key", kiwiConfig.getKiwiApiKey());
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<KiwiSearchResponseRoundTripDTO> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                KiwiSearchResponseRoundTripDTO.class
        );

        return response.getBody();
    }

    private URI getUri(FlightType flightType, FlightRequestDTO dto) {
        String endpoint = kiwiConfig.getKiwiApiEndpoint();
        String flightTypePath = flightType.getType();


        return UriComponentsBuilder.fromUriString(endpoint + "/" + flightTypePath)
                .queryParam("source", dto.source())
                .queryParam("destination", dto.destination())
                .queryParam("inboundDepartureDateStart", defaultIfNull(dto.inboundDepartureDateStart(), ""))
                .queryParam("inboundDepartureDateEnd", defaultIfNull(dto.inboundDepartureDateEnd(), ""))
                .queryParam("outboundDepartmentDateStart", defaultIfNull(dto.inboundDepartureDateStart(), ""))
                .queryParam("outboundDepartmentDateEnd", defaultIfNull(dto.inboundDepartureDateEnd(), ""))
                .queryParam("adults", defaultIfNull(dto.adults(), "1"))
                .queryParam("children", defaultIfNull(dto.children(), "0"))
                .queryParam("infants", defaultIfNull(dto.infants(), "0"))
                .queryParam("handbags", defaultIfNull(dto.handBags(), "1"))
                .queryParam("holdbags", defaultIfNull(dto.holdBags(), "0"))
                .build()
                .encode()
                .toUri();
    }

    private String defaultIfNull(Object value, String defaultValue) {
        return value == null ? defaultValue : value.toString();
    }
}

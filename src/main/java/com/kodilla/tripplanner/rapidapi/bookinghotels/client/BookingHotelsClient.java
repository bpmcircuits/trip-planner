package com.kodilla.tripplanner.rapidapi.bookinghotels.client;

import com.kodilla.tripplanner.dto.HotelSearchRequestDTO;
import com.kodilla.tripplanner.rapidapi.bookinghotels.config.BookingHotelsConfig;
import com.kodilla.tripplanner.rapidapi.bookinghotels.dto.BookingHotelsDestinationApiDTO;
import com.kodilla.tripplanner.rapidapi.bookinghotels.dto.BookingHotelsDestinationResponseApiDTO;
import com.kodilla.tripplanner.rapidapi.bookinghotels.dto.BookingHotelsSearchResponseApiDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BookingHotelsClient {

    private final BookingHotelsConfig config;
    private final RestTemplate restTemplate;

    public BookingHotelsDestinationResponseApiDTO getAutoComplete(String query) {
        URI uri = UriComponentsBuilder.fromUriString(config.getBookingHotelsApiEndpoint() + "/auto-complete")
                .queryParam("query", query)
                .build().encode().toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-rapidapi-key", config.getBookingApiKey());
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<BookingHotelsDestinationResponseApiDTO> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                entity,
                BookingHotelsDestinationResponseApiDTO.class
        );

        return response.getBody();
    }

    public BookingHotelsSearchResponseApiDTO getHotelResults(BookingHotelsDestinationApiDTO bookingHotelsDestinationApiDTO, HotelSearchRequestDTO request) {
        URI uri = UriComponentsBuilder.fromUriString(config.getBookingHotelsApiEndpoint() + "/search")
                .queryParam("locationId", bookingHotelsDestinationApiDTO.destId())
                .queryParam("checkinDate", request.checkinDate())
                .queryParam("checkoutDate", request.checkoutDate())
                .queryParam("adults", request.adultsNumber())
                .build().encode().toUri();

        System.out.println(request);

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-rapidapi-key", config.getBookingApiKey());
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<BookingHotelsSearchResponseApiDTO> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                entity,
                BookingHotelsSearchResponseApiDTO.class
        );

        return response.getBody();
    }
}

package com.kodilla.tripplanner.mapper;

import com.kodilla.tripplanner.amadeus.client.AmadeusFlightsFlightOffersDataDTO;
import com.kodilla.tripplanner.amadeus.dto.AmadeusFlightsAirportSearchRequestDTO;
import com.kodilla.tripplanner.amadeus.dto.AmadeusFlightsFlightOfferDTO;
import com.kodilla.tripplanner.amadeus.dto.AmadeusFlightsItinerariesDTO;
import com.kodilla.tripplanner.dto.flights.*;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

@Service
public class FlightOfferMapper {

    public List<FlightSearchResponseDTO> mapAllFromAmadeus(AmadeusFlightsFlightOffersDataDTO offers) {
        if (offers == null || offers.data().isEmpty()) return List.of();
        return offers.data().stream()
                .map(this::mapFromAmadeusToFlightSearch)
                .toList();
    }

    public FlightSearchResponseDTO mapFromAmadeusToFlightSearch(AmadeusFlightsFlightOfferDTO src) {
        List<AmadeusFlightsItinerariesDTO> itineraries = src.itineraries() == null ? Collections.emptyList() : src.itineraries();
        boolean oneWay = src.oneWay() || itineraries.size() < 2;

        FlightBoundDTO outbound = itineraries.isEmpty() ? null : mapBound(itineraries.getFirst());
        FlightBoundDTO inbound  = oneWay || itineraries.size() < 2 ? null : mapBound(itineraries.get(1));

        PriceDTO total = new PriceDTO(
                src.price() != null ? src.price().currency() : null,
                src.price() != null ? src.price().total() : null
        );

        List<TravelerPriceDTO> tps = src.travelerPricings() == null ? List.of()
                : src.travelerPricings().stream()
                .map(tp -> new TravelerPriceDTO(
                        tp.travelerType(),
                        new PriceDTO(
                                tp.price() != null ? tp.price().currency() : null,
                                tp.price() != null ? tp.price().total() : null
                        )
                ))
                .toList();

        return new FlightSearchResponseDTO(oneWay, outbound, inbound, total, tps);
    }

    public AmadeusFlightsAirportSearchRequestDTO mapToAmadeusRequest(FlightSearchRequestDTO request) {
        return new AmadeusFlightsAirportSearchRequestDTO(
                request.departureCity(),
                request.arrivalCity(),
                request.departureDate(),
                request.returnDate(),
                request.adults(),
                request.children(),
                request.infants(),
                request.currencyCode()
        );
    }

    private FlightBoundDTO mapBound(AmadeusFlightsItinerariesDTO it) {
        String durationIso = it.duration();
        int minutes = durationIso != null ? Math.toIntExact(Duration.parse(durationIso).toMinutes()) : 0;

        List<FlightSegmentDTO> segments = it.segments() == null ? List.of()
                : it.segments().stream()
                .map(s -> new FlightSegmentDTO(
                        s.departure() != null ? s.departure().iataCode() : null,
                        s.arrival()   != null ? s.arrival().iataCode()   : null,
                        s.carrierCode(),
                        normalizeTime(s.departure() != null ? s.departure().at() : null),
                        normalizeTime(s.arrival()   != null ? s.arrival().at()   : null)
                ))
                .toList();

        return new FlightBoundDTO(durationIso, minutes, segments);
    }

    private String normalizeTime(String isoLocal) {
        return isoLocal;
    }
}

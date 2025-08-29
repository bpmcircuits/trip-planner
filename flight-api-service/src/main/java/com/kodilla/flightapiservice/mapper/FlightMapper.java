package com.kodilla.flightapiservice.mapper;

import com.kodilla.flightapiservice.domain.Flight;
import com.kodilla.flightapiservice.domain.FlightSegment;
import com.kodilla.flightapiservice.domain.TravelerPrice;
import com.kodilla.flightapiservice.dto.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.IntStream;

@Component
public class FlightMapper {

    public Flight mapToFlight(FlightSearchResponseDTO dto) {
        Flight flight = Flight.builder()
                .oneWay(dto.oneWay())
                .searchId(UUID.randomUUID())
                .currency(dto.totalPrice().currency())
                .totalPrice(dto.totalPrice().total())
                .outboundDurationIso(dto.outbound().durationIso())
                .outboundDurationMinutes(dto.outbound().durationMinutes())
                .lastUpdated(LocalDateTime.now())
                .build();
        
        // Map outbound segments
        if (dto.outbound().segments() != null) {
            IntStream.range(0, dto.outbound().segments().size())
                    .forEach(i -> {
                        FlightSegmentDTO segmentDto = dto.outbound().segments().get(i);
                        FlightSegment segment = mapToFlightSegment(segmentDto, i);
                        flight.addOutboundSegment(segment);
                    });
        }
        
        // Map inbound segments if not one-way
        if (!dto.oneWay() && dto.inbound() != null && dto.inbound().segments() != null) {
            flight.setInboundDurationIso(dto.inbound().durationIso());
            flight.setInboundDurationMinutes(dto.inbound().durationMinutes());
            
            IntStream.range(0, dto.inbound().segments().size())
                    .forEach(i -> {
                        FlightSegmentDTO segmentDto = dto.inbound().segments().get(i);
                        FlightSegment segment = mapToFlightSegment(segmentDto, i);
                        flight.addInboundSegment(segment);
                    });
        }
        
        // Map traveler prices
        if (dto.travelerPrices() != null) {
            dto.travelerPrices().forEach(travelerPriceDto -> {
                TravelerPrice travelerPrice = mapToTravelerPrice(travelerPriceDto);
                flight.addTravelerPrice(travelerPrice);
            });
        }
        
        return flight;
    }
    
    private FlightSegment mapToFlightSegment(FlightSegmentDTO dto, int order) {
        return FlightSegment.builder()
                .fromIata(dto.fromIata())
                .toIata(dto.toIata())
                .carrierCode(dto.carrierCode())
                .departureAt(dto.departureAt())
                .arrivalAt(dto.arrivalAt())
                .segmentOrder(order)
                .build();
    }
    
    private TravelerPrice mapToTravelerPrice(TravelerPriceDTO dto) {
        return TravelerPrice.builder()
                .travelerType(dto.travelerType())
                .currency(dto.price().currency())
                .price(dto.price().total())
                .build();
    }
    
    public FlightSearchResponseDTO mapToDto(Flight flight) {
        // Map outbound segments
        FlightBoundDTO outbound = new FlightBoundDTO(
                flight.getOutboundDurationIso(),
                flight.getOutboundDurationMinutes(),
                flight.getOutboundSegments().stream()
                        .sorted((s1, s2) -> s1.getSegmentOrder().compareTo(s2.getSegmentOrder()))
                        .map(this::mapToFlightSegmentDto)
                        .toList()
        );
        
        // Map inbound segments if not one-way
        FlightBoundDTO inbound = null;
        if (!flight.isOneWay()) {
            inbound = new FlightBoundDTO(
                    flight.getInboundDurationIso(),
                    flight.getInboundDurationMinutes(),
                    flight.getInboundSegments().stream()
                            .sorted((s1, s2) -> s1.getSegmentOrder().compareTo(s2.getSegmentOrder()))
                            .map(this::mapToFlightSegmentDto)
                            .toList()
            );
        }
        
        // Map total price
        PriceDTO totalPrice = new PriceDTO(
                flight.getCurrency(),
                flight.getTotalPrice()
        );
        
        // Map traveler prices
        var travelerPrices = flight.getTravelerPrices().stream()
                .map(this::mapToTravelerPriceDto)
                .toList();
        
        return new FlightSearchResponseDTO(
                flight.isOneWay(),
                outbound,
                inbound,
                totalPrice,
                travelerPrices
        );
    }
    
    private FlightSegmentDTO mapToFlightSegmentDto(FlightSegment segment) {
        return new FlightSegmentDTO(
                segment.getFromIata(),
                segment.getToIata(),
                segment.getCarrierCode(),
                segment.getDepartureAt(),
                segment.getArrivalAt()
        );
    }
    
    private TravelerPriceDTO mapToTravelerPriceDto(TravelerPrice travelerPrice) {
        return new TravelerPriceDTO(
                travelerPrice.getTravelerType(),
                new PriceDTO(
                        travelerPrice.getCurrency(),
                        travelerPrice.getPrice()
                )
        );
    }
}
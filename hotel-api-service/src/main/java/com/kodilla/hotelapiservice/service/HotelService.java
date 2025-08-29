package com.kodilla.hotelapiservice.service;

import com.kodilla.hotelapiservice.domain.Hotel;
import com.kodilla.hotelapiservice.exception.DatabaseOperationException;
import com.kodilla.hotelapiservice.exception.HotelNotFoundException;
import com.kodilla.hotelapiservice.mapper.HotelMapper;
import com.kodilla.hotelapiservice.rapidapi.dto.BookingHotelsResponseDTO;
import com.kodilla.hotelapiservice.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class HotelService {

    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    @Value("${hotel.cleanup.days:30}")
    private int cleanupDays;

    @Transactional
    public List<Hotel> saveHotelOffers(List<BookingHotelsResponseDTO> hotelDtos) throws DatabaseOperationException {
        log.info("Saving {} hotel offers", hotelDtos.size());
        
        try {
            return hotelDtos.stream()
                    .map(dto -> {
                        try {
                            return saveOrUpdateHotel(dto);
                        } catch (DatabaseOperationException e) {
                            log.error("Error saving hotel: {}", dto.name(), e);
                            throw new RuntimeException(e);
                        }
                    })
                    .toList();
        } catch (Exception e) {
            log.error("Error saving hotel offers", e);
            throw new DatabaseOperationException("Error saving hotel offers", e);
        }
    }

    @Transactional
    public Hotel saveOrUpdateHotel(BookingHotelsResponseDTO hotelDto) throws DatabaseOperationException {
        try {
            Optional<Hotel> existingHotel = hotelRepository.findByNameAndCityAndCountryCode(
                    hotelDto.name(), hotelDto.city(), hotelDto.countryCode());
            
            if (existingHotel.isPresent()) {
                Hotel hotel = existingHotel.get();
                hotel.setPrice(hotelDto.price());
                hotel.setCurrency(hotelDto.currency());
                hotel.setCheckInDate(hotelDto.checkInDate());
                hotel.setCheckOutDate(hotelDto.checkOutDate());
                hotel.setReviewScore(hotelDto.reviewScore());
                hotel.setReviewScoreWord(hotelDto.reviewScoreWord());
                hotel.setReviewCount(hotelDto.reviewCount());
                hotel.setLastUpdated(LocalDateTime.now());
                
                log.info("Updating existing hotel: {}", hotel.getName());
                return hotelRepository.save(hotel);
            } else {
                Hotel hotel = hotelMapper.mapToHotel(hotelDto);

                if (hotel.getId() == null) {
                    hotel.setId(generateId());
                }
                
                log.info("Creating new hotel: {}", hotel.getName());
                return hotelRepository.save(hotel);
            }
        } catch (Exception e) {
            log.error("Error saving or updating hotel: {}", hotelDto.name(), e);
            throw new DatabaseOperationException("Error saving or updating hotel: " + hotelDto.name(), e);
        }
    }

    @Transactional
    public int removeOutdatedOffers() throws DatabaseOperationException {
        try {
            LocalDateTime cutoffDate = LocalDateTime.now().minusDays(cleanupDays);
            log.info("Removing hotel offers not updated since {}", cutoffDate);
            
            List<Hotel> outdatedHotels = hotelRepository.findByLastUpdatedBefore(cutoffDate);
            int count = outdatedHotels.size();
            
            if (!outdatedHotels.isEmpty()) {
                hotelRepository.deleteAll(outdatedHotels);
                log.info("Removed {} outdated hotel offers", count);
            } else {
                log.info("No outdated hotel offers found");
            }
            
            return count;
        } catch (Exception e) {
            log.error("Error removing outdated offers", e);
            throw new DatabaseOperationException("Error removing outdated offers", e);
        }
    }

    public List<Hotel> getAllHotels() throws DatabaseOperationException {
        try {
            return hotelRepository.findAll();
        } catch (Exception e) {
            log.error("Error retrieving all hotels", e);
            throw new DatabaseOperationException("Error retrieving all hotels", e);
        }
    }

    public Hotel getHotelById(Long id) throws HotelNotFoundException, DatabaseOperationException {
        try {
            return hotelRepository.findById(id)
                    .orElseThrow(() -> new HotelNotFoundException("Hotel with id " + id + " not found"));
        } catch (HotelNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error retrieving hotel with id: {}", id, e);
            throw new DatabaseOperationException("Error retrieving hotel with id: " + id, e);
        }
    }

    @Transactional
    public void deleteHotel(Long id) throws HotelNotFoundException, DatabaseOperationException {
        try {
            if (!hotelRepository.existsById(id)) {
                throw new HotelNotFoundException("Hotel with id " + id + " not found");
            }
            hotelRepository.deleteById(id);
        } catch (HotelNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error deleting hotel with id: {}", id, e);
            throw new DatabaseOperationException("Error deleting hotel with id: " + id, e);
        }
    }

    private Long generateId() {
        return System.currentTimeMillis();
    }
}
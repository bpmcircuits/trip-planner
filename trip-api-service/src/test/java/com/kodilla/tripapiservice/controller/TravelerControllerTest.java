package com.kodilla.tripapiservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kodilla.tripapiservice.domain.BaggageType;
import com.kodilla.tripapiservice.domain.Gender;
import com.kodilla.tripapiservice.domain.PersonType;
import com.kodilla.tripapiservice.domain.Traveler;
import com.kodilla.tripapiservice.dto.TravelerDTO;
import com.kodilla.tripapiservice.exception.TravelerNotFoundException;
import com.kodilla.tripapiservice.mapper.TravelerMapper;
import com.kodilla.tripapiservice.service.TravelerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class TravelerControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Mock
    private TravelerService travelerService;

    @Mock
    private TravelerMapper travelerMapper;

    @InjectMocks
    private TravelerController travelerController;

    private Traveler traveler;
    private TravelerDTO travelerDTO;

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders.standaloneSetup(travelerController)
                .setControllerAdvice(new com.kodilla.tripapiservice.exception.GlobalHttpErrorHandler())
                .build();

        traveler = Traveler.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .gender(Gender.MALE)
                .personType(PersonType.ADULT)
                .age(30)
                .baggage(BaggageType.CHECKED)
                .build();

        travelerDTO = new TravelerDTO(
                1L,
                "John",
                "Doe",
                Gender.MALE,
                PersonType.ADULT,
                30,
                BaggageType.CHECKED,
                null
        );
    }

    @Test
    void shouldGetAllTravelers() throws Exception {
        // Given
        when(travelerService.getAllTravelers()).thenReturn(List.of(traveler));
        when(travelerMapper.toTravelerDTOList(any())).thenReturn(List.of(travelerDTO));

        // When & Then
        mockMvc.perform(get("/api/v1/trips/travelers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].firstName", is("John")))
                .andExpect(jsonPath("$[0].lastName", is("Doe")))
                .andExpect(jsonPath("$[0].gender", is("MALE")))
                .andExpect(jsonPath("$[0].personType", is("ADULT")))
                .andExpect(jsonPath("$[0].age", is(30)))
                .andExpect(jsonPath("$[0].baggage", is("CHECKED")));

        verify(travelerService, times(1)).getAllTravelers();
        verify(travelerMapper, times(1)).toTravelerDTOList(any());
    }

    @Test
    void shouldAddTraveler() throws Exception {
        // Given
        when(travelerMapper.toTraveler(any(TravelerDTO.class))).thenReturn(traveler);
        when(travelerService.addTraveler(any(Traveler.class))).thenReturn(traveler);
        when(travelerMapper.toTravelerDTO(any(Traveler.class))).thenReturn(travelerDTO);

        // When & Then
        mockMvc.perform(post("/api/v1/trips/travelers/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(travelerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("John")))
                .andExpect(jsonPath("$.lastName", is("Doe")))
                .andExpect(jsonPath("$.gender", is("MALE")))
                .andExpect(jsonPath("$.personType", is("ADULT")))
                .andExpect(jsonPath("$.age", is(30)))
                .andExpect(jsonPath("$.baggage", is("CHECKED")));

        verify(travelerMapper, times(1)).toTraveler(any(TravelerDTO.class));
        verify(travelerService, times(1)).addTraveler(any(Traveler.class));
        verify(travelerMapper, times(1)).toTravelerDTO(any(Traveler.class));
    }

    @Test
    void shouldRemoveTraveler() throws Exception {
        // Given
        Long travelerId = 1L;
        doNothing().when(travelerService).removeTraveler(travelerId);

        // When & Then
        mockMvc.perform(delete("/api/v1/trips/travelers/{travelerId}", travelerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(travelerService, times(1)).removeTraveler(travelerId);
    }

    @Test
    void shouldReturn404WhenRemovingNonExistentTraveler() throws Exception {
        // Given
        Long travelerId = 999L;
        doThrow(new TravelerNotFoundException("Traveler with ID " + travelerId + " does not exist."))
                .when(travelerService).removeTraveler(travelerId);

        // When & Then
        mockMvc.perform(delete("/api/v1/trips/travelers/{travelerId}", travelerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(travelerService, times(1)).removeTraveler(travelerId);
    }
}
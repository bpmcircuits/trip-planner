package com.bpm.frontend.service;

import com.bpm.frontend.dto.FlightInfoDTO;

import java.util.List;

public class FlightInfoService {

    private static FlightInfoService instance;
    private List<FlightInfoDTO> flightInfoDTOList;

    private FlightInfoService() {
        this.flightInfoDTOList = exampleData();
    }
    public static FlightInfoService getInstance() {
        if (instance == null) {
            instance = new FlightInfoService();
        }
        return instance;
    }

    private List<FlightInfoDTO> exampleData() {
        return List.of(
                new FlightInfoDTO(
                        "Warsaw",
                        "London",
                        "Warsaw Chopin Airport",
                        "WAW",
                        "London Heathrow Airport",
                        "LHR",
                        "2024-07-01",
                        "10:00",
                        "2024-07-01",
                        "12:00",
                        "LO123"
                ),
                new FlightInfoDTO(
                        "London",
                        "Warsaw",
                        "London Heathrow Airport",
                        "LHR",
                        "Warsaw Chopin Airport",
                        "WAW",
                        "2024-07-05",
                        "14:00",
                        "2024-07-05",
                        "16:00",
                        "LO321"
                )
        );
    }

    public FlightInfoDTO getFlightDetails(String from, String to) {
        for (FlightInfoDTO flightInfo : flightInfoDTOList) {
            if (flightInfo.from().equalsIgnoreCase(from) &&
                flightInfo.to().equalsIgnoreCase(to)) {
                return flightInfo;
            }
        }
        return null;
    }
}

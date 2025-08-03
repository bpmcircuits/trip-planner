package com.kodilla.tripplanner.rapidapi.kiwiflights.client;

public enum FlightType {
    ONE_WAY("one-way"),
    ROUND_TRIP("round-trip");

    private final String type;

    FlightType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}

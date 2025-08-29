package com.bpm.frontend.service;

import com.bpm.frontend.dto.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderService {

    private static OrderService INSTANCE;
    private List<FlightOrderDTO> order;

    private OrderService() {
        this.order = exampleData();
    }

    public static OrderService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OrderService();
        }
        return INSTANCE;
    }

    private List<FlightOrderDTO> exampleData() {
        FlightOrderDTO order1 = new FlightOrderDTO("Adult", BigDecimal.valueOf(100.00));
        FlightOrderDTO order2 = new FlightOrderDTO("Infant", BigDecimal.valueOf(150.00));
        FlightOrderDTO order3 = new FlightOrderDTO("Checked Baggage", BigDecimal.valueOf(200.00));
        return List.of(order1, order2, order3);
    }

    public List<FlightOrderDTO> getOrder() {
        return new ArrayList<>(order);
    }
}

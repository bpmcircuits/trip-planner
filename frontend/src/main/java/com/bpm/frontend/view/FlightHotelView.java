package com.bpm.frontend.view;

import com.bpm.frontend.dto.FlightOfferDTO;
import com.bpm.frontend.dto.HotelOfferDTO;
import com.bpm.frontend.dto.SearchRequestDTO;
import com.bpm.frontend.mapper.FlightMapper;
import com.bpm.frontend.mapper.HotelMapper;
import com.bpm.frontend.service.FlightOfferService;
import com.bpm.frontend.service.HotelOfferService;
import com.bpm.frontend.session.SearchCriteriaHolder;
import com.bpm.frontend.tripplanerback.dto.hotels.BookingHotelsResponseDTO;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route(value = "results", layout = MainLayout.class)
@PageTitle("Flight and Hotel Offers")
public class FlightHotelView extends VerticalLayout  {

    private final Grid<FlightOfferDTO> flightGrid = new Grid<>(FlightOfferDTO.class, false);
    private final Grid<HotelOfferDTO> hotelGrid = new Grid<>(HotelOfferDTO.class, false);
    private final Tabs tabs;
    private final Div content;

    private final FlightOfferService flightService;
    private final FlightMapper flightMapper;
    private final HotelOfferService hotelService;
    private final HotelMapper hotelMapper;

    public FlightHotelView(SearchCriteriaHolder holder,
                           FlightOfferService flightService,
                           FlightMapper flightMapper,
                           HotelOfferService hotelService,
                           HotelMapper hotelMapper) {
        this.flightService = flightService;
        this.flightMapper = flightMapper;
        this.hotelService = hotelService;
        this.hotelMapper = hotelMapper;

        Tab flightsTab = new Tab("Flights");
        Tab hotelsTab = new Tab("Hotels");
        tabs = new Tabs(flightsTab, hotelsTab);
        tabs.setSelectedTab(flightsTab);

        flightGrid.addColumn(FlightOfferDTO::airline).setHeader("Airline");
        flightGrid.addColumn(FlightOfferDTO::departureTime).setHeader("Departure Time");
        flightGrid.addColumn(FlightOfferDTO::from).setHeader("From");
        flightGrid.addColumn(FlightOfferDTO::arrivalTime).setHeader("Arrival Time");
        flightGrid.addColumn(FlightOfferDTO::to).setHeader("To");
        flightGrid.addColumn(FlightOfferDTO::cost).setHeader("Cost");

        hotelGrid.addColumn(HotelOfferDTO::name).setHeader("Hotel Name");
        hotelGrid.addColumn(HotelOfferDTO::location).setHeader("Location");
        hotelGrid.addColumn(HotelOfferDTO::startDate).setHeader("Start Date");
        hotelGrid.addColumn(HotelOfferDTO::endDate).setHeader("End Date");
        hotelGrid.addColumn(HotelOfferDTO::price).setHeader("Price");
        hotelGrid.addColumn(HotelOfferDTO::reviewScore).setHeader("Review Score");
        hotelGrid.addColumn(HotelOfferDTO::reviewScoreWord).setHeader("Review Score Word");

        content = new Div(flightGrid);
        content.setWidthFull();

        SearchRequestDTO dto = holder.getLastSearch();
        if (dto != null) {
            List<FlightOfferDTO> flightOfferDTO
                    = flightMapper.mapFromResponseToFlightOfferDTOs(flightService.getFlightOffers(dto));
            flightGrid.setItems(flightOfferDTO);

            List<BookingHotelsResponseDTO> response = hotelService.getHotelOffers(hotelMapper.mapToHotelSearchDTO(dto));
            List<HotelOfferDTO> hotelOfferDTOs = hotelMapper.mapToHotelOfferDTOList(response);
            hotelGrid.setItems(hotelOfferDTOs);
        }

        tabs.addSelectedChangeListener(e -> {
            content.removeAll();
            content.add(e.getSelectedTab().getLabel().equals("Flights") ? flightGrid : hotelGrid);
        });

        add(tabs, content);
        setSizeFull();
    }
}

package com.bpm.frontend.view;

import com.bpm.frontend.dto.TripsDTO;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "saved-trips", layout = MainLayout.class)
@PageTitle("Saved Trips")
public class SavedTripsView extends VerticalLayout {

    private final Grid<TripsDTO> tripGrid = new Grid<>(TripsDTO.class);
    private final Button viewTripButton = new Button("View saved trip");

    public SavedTripsView() {
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        H2 header = new H2("Your saved trips");

        configureGrid();

        viewTripButton.addClickListener(e -> {
        });

        add(header, tripGrid, viewTripButton);
        setHorizontalComponentAlignment(Alignment.CENTER, tripGrid);
    }

    private void configureGrid() {
        tripGrid.setColumns("id");
        tripGrid.addColumn(TripsDTO::country).setHeader("COUNTRY");
        tripGrid.addColumn(TripsDTO::city).setHeader("CITY");
        tripGrid.addColumn(TripsDTO::date).setHeader("DATES");
        tripGrid.addColumn(TripsDTO::numberOfTravelers).setHeader("TRAVELERS");
        tripGrid.setWidth("80%");
        tripGrid.setAllRowsVisible(true);
        tripGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
    }
}
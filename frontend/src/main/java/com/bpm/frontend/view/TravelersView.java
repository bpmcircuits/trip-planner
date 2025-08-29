package com.bpm.frontend.view;

import com.bpm.frontend.dto.TravelerDTO;
import com.bpm.frontend.mapper.TravelerMapper;
import com.bpm.frontend.service.TravelerService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route(value = "travelers", layout = MainLayout.class)
@PageTitle("Travelers")
public class TravelersView extends VerticalLayout {

    private Grid<TravelerDTO> travelerGrid = new Grid<>(TravelerDTO.class, false);

    private final TravelerService travelerService;
    private final TravelerMapper travelerMapper;

    public TravelersView(TravelerService travelerService, TravelerMapper travelerMapper) {
        this.travelerService = travelerService;
        this.travelerMapper = travelerMapper;


        setWidthFull();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        H2 title = new H2("Travelers");

        TextField firstNameField = new TextField();
        firstNameField.setPlaceholder("First name");

        TextField lastNameField = new TextField();
        lastNameField.setPlaceholder("Last name");

        ComboBox<String> genderBox = new ComboBox<>();
        genderBox.setPlaceholder("Gender");
        genderBox.setItems("MALE", "FEMALE");

        ComboBox<String> personTypeBox = new ComboBox<>();
        personTypeBox.setPlaceholder("Person type");
        personTypeBox.setItems("ADULT", "CHILD", "INFANT");

        ComboBox<String> baggageBox = new ComboBox<>();
        baggageBox.setPlaceholder("Baggage");
        baggageBox.setItems("CABIN", "CHECKED");

        HorizontalLayout formLayout = new HorizontalLayout(
                firstNameField, lastNameField, genderBox, personTypeBox, baggageBox
        );
        formLayout.setAlignItems(Alignment.BASELINE);

        travelerGrid.addColumn(TravelerDTO::firstName).setHeader("First Name");
        travelerGrid.addColumn(TravelerDTO::lastName).setHeader("Last Name");
        travelerGrid.addColumn(TravelerDTO::gender).setHeader("Gender");
        travelerGrid.addColumn(TravelerDTO::personType).setHeader("Person");
        travelerGrid.addColumn(TravelerDTO::baggageType).setHeader("Baggage");

        travelerGrid.setSelectionMode(Grid.SelectionMode.SINGLE);

        reload();

        Button addButton = new Button("Add traveler");
        addButton.addClickListener(e -> {
            TravelerDTO formTraveler = new TravelerDTO(
                    null,
                    firstNameField.getValue(),
                    lastNameField.getValue(),
                    genderBox.getValue(),
                    personTypeBox.getValue(),
                    baggageBox.getValue()
            );

            travelerService.addTraveler(travelerMapper.mapToRequest(formTraveler));
            reload();

            firstNameField.clear();
            lastNameField.clear();
            genderBox.clear();
            personTypeBox.clear();
            baggageBox.clear();
        });

        Button deleteButton = new Button("Delete traveler", event -> {
            TravelerDTO selected = travelerGrid.asSingleSelect().getValue();
            if (selected != null && selected.id() != null) {
                travelerService.deleteTraveler(selected.id());
                reload();
            } else {
                Notification.show("Please select a traveler first.");
            }
        });

        add(title, formLayout, addButton, travelerGrid, deleteButton);
    }

    private void reload() {
        List<TravelerDTO> latest = travelerMapper.mapFromResponseToList(travelerService.getTravelers());
        travelerGrid.setItems(latest);
    }
}

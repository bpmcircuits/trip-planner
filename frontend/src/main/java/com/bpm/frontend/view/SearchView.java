package com.bpm.frontend.view;

import com.bpm.frontend.dto.SearchRequestDTO;
import com.bpm.frontend.session.SearchCriteriaHolder;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "search", layout = MainLayout.class)
@PageTitle("Flight Search")
public class SearchView extends VerticalLayout {

    public SearchView(SearchCriteriaHolder holder) {
        setSizeFull();
        setAlignItems(Alignment.CENTER);

        DatePicker departDate = new DatePicker("Depart");
        DatePicker returnDate = new DatePicker("Return");
        VerticalLayout datesLayout = new VerticalLayout(departDate, returnDate);
        datesLayout.setPadding(false);
        datesLayout.setSpacing(true);

        TextField fromField = new TextField("From");
        TextField toField = new TextField("To");
        VerticalLayout locationLayout = new VerticalLayout(fromField, toField);
        locationLayout.setPadding(false);
        locationLayout.setSpacing(true);

        ComboBox<String> adults = new ComboBox<>();
        adults.setLabel("Adults");
        adults.setItems("1", "2", "3", "4", "5+");

        ComboBox<String> child = new ComboBox<>();
        child.setLabel("Child");
        child.setItems("0", "1", "2", "3", "4", "5+");

        ComboBox<String> infants = new ComboBox<>();
        infants.setLabel("Infants");
        infants.setItems("0", "1", "2", "3", "4", "5+");

        VerticalLayout filtersLayout = new VerticalLayout(adults, child, infants);
        filtersLayout.setPadding(false);
        filtersLayout.setSpacing(true);

        HorizontalLayout formLayout = new HorizontalLayout(datesLayout, locationLayout, filtersLayout);
        formLayout.setSpacing(true);
        formLayout.setDefaultVerticalComponentAlignment(Alignment.END);

        Button searchButton = new Button("Search");
        H1 heading = new H1("Plan your trip");
        VerticalLayout content = new VerticalLayout(heading, formLayout, searchButton);
        content.setAlignItems(Alignment.CENTER);
        content.setPadding(true);
        content.setSpacing(true);
        content.getStyle().set("border", "1px solid #aaa").set("padding", "20px");

        add(content);

        searchButton.addClickListener(e -> {
            SearchRequestDTO dto = new SearchRequestDTO(
                    fromField.getValue(),
                    toField.getValue(),
                    departDate.getValue().toString(),
                    returnDate.getValue().toString(),
                    Integer.parseInt(adults.getValue()),
                    Integer.parseInt(child.getValue()),
                    Integer.parseInt(infants.getValue()),
                    "EUR"
            );

            holder.setLastSearch(dto);
            UI.getCurrent().navigate("results");
        });
    }
}

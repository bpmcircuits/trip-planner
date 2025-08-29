package com.bpm.frontend.component;

import com.bpm.frontend.dto.FlightOrderDTO;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.math.BigDecimal;
import java.util.List;

public class PriceBoxComponent extends VerticalLayout {

    public PriceBoxComponent(List<FlightOrderDTO> items) {
        this.getStyle().set("border", "1px solid #ccc").set("padding", "1rem");
        this.setWidth("300px");

        for (FlightOrderDTO item : items) {
            HorizontalLayout row = new HorizontalLayout(
                    new Span(item.label()),
                    new Span(String.valueOf(item.value()))
            );
            row.setWidthFull();
            row.setJustifyContentMode(JustifyContentMode.BETWEEN);
            this.add(row);
        }

        BigDecimal totalAmount = items.stream()
                .map(FlightOrderDTO::value)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        HorizontalLayout totalRow = new HorizontalLayout(
                new Span("Total (PLN)"),
                new Span(String.valueOf(totalAmount))
        );
        totalRow.getStyle().set("font-weight", "bold");
        totalRow.setWidthFull();
        totalRow.setJustifyContentMode(JustifyContentMode.BETWEEN);

        this.add(totalRow);
    }
}
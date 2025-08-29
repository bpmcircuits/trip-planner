package com.bpm.frontend.view;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;

@Route("")
public class MainLayout extends AppLayout implements RouterLayout {

    private final Button loginButton = new Button("Log in");
    private final Button signupButton = new Button("Sign up");

    public MainLayout() {
        createHeader();
    }

    private void createHeader() {

        DrawerToggle toggle = new DrawerToggle();

        H1 title = new H1("MyApp");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        SideNav nav = getSideNav();

        Scroller scroller = new Scroller(nav);
        scroller.setClassName(LumoUtility.Padding.SMALL);

        Image logo = new Image("images/TakeMeLogo.png", "Logo");
        logo.setHeight("80px");

        ComboBox<String> currencyBox = new ComboBox<>();
        currencyBox.setLabel("Currency");
        currencyBox.setItems("USD", "EUR", "PLN");
        currencyBox.setValue("USD");

        loginButton.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("login")));
        signupButton.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("signup")));

        HorizontalLayout topRight = new HorizontalLayout(currencyBox, loginButton, signupButton);
        topRight.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        topRight.setWidthFull();

        HorizontalLayout header = new HorizontalLayout(toggle, logo, topRight);
        header.setWidthFull();
        header.setSpacing(true);
        header.setPadding(true);
        header.getStyle().set("background-color", "#f5f5f5");
        header.getStyle().set("align-items", "center");
        header.getStyle().set("justify-content", "space-between");

        addToDrawer(scroller);
        addToNavbar(header);
    }

    public void hideAuthButtons() {
        loginButton.setVisible(false);
        signupButton.setVisible(false);
    }

    public void showAuthButtons() {
        loginButton.setVisible(true);
        signupButton.setVisible(true);
    }

    private SideNav getSideNav() {
        SideNav nav = new SideNav();
        SideNavItem tripPlanning = new SideNavItem("Plan",
                SearchView.class, VaadinIcon.CALENDAR.create());
        SideNavItem yourTrips = new SideNavItem("Your trips", SavedTripsView.class,
                VaadinIcon.LOCATION_ARROW_CIRCLE.create());
        SideNavItem travelers = new SideNavItem("Travelers",
                TravelersView.class, VaadinIcon.USERS.create());
        SideNavItem userSettings = new SideNavItem("User Settings",
                "account-update", VaadinIcon.USER.create());

        nav.addItem(tripPlanning, yourTrips, travelers, userSettings);
        return nav;
    }
}

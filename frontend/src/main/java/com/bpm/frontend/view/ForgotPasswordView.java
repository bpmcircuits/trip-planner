package com.bpm.frontend.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "forgot-password", layout = MainLayout.class)
@PageTitle("Forgot Password")
public class ForgotPasswordView extends VerticalLayout {

    public ForgotPasswordView() {
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setSizeFull();

        H1 title = new H1("Forgot password?");
        title.getStyle().set("font-weight", "bold");

        Paragraph subtitle = new Paragraph("Enter your email and we'll send you a link to reset your password.");

        TextField email = new TextField("Email address");
        email.setPlaceholder("email@address.com");
        email.setPrefixComponent(new Icon(VaadinIcon.ENVELOPE));
        email.setWidthFull();

        Button resetButton = new Button("Reset Password");
        resetButton.setWidthFull();
        resetButton.getStyle()
                .set("background-color", "#333")
                .set("color", "white")
                .set("font-weight", "bold");

        resetButton.addClickListener(e -> {
            Notification.show("Reset link sent to " + email.getValue());
            UI.getCurrent().navigate("/login");
        });

        Anchor backToLogin = new Anchor("/login", "Go back to Sign In.");
        backToLogin.getStyle()
                .set("font-size", "small")
                .set("margin-top", "20px");

        VerticalLayout formLayout = new VerticalLayout(
                title, subtitle,
                email,
                resetButton,
                backToLogin
        );
        formLayout.setWidth("350px");
        formLayout.setPadding(true);
        formLayout.setSpacing(true);
        formLayout.getStyle().set("border-radius", "15px");

        add(formLayout);
    }
}


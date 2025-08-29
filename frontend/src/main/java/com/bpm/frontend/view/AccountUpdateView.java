package com.bpm.frontend.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "account-update", layout = MainLayout.class)
@PageTitle("Account Update")
public class AccountUpdateView extends VerticalLayout {

    public AccountUpdateView() {
        setAlignItems(Alignment.CENTER);
        setSizeFull();

        H1 title = new H1("Account update");
        Paragraph description = new Paragraph("Need to update your information with us? " +
                "Please fill out this form and we will update our records.");
        description.setWidth("350px");
        description.getStyle().set("text-align", "center");

        TextField firstName = new TextField("First name");
        firstName.setPlaceholder("E.g. John");

        TextField lastName = new TextField("Last name");
        lastName.setPlaceholder("E.g. Smith");

        EmailField email = new EmailField("Email address");
        email.setPrefixComponent(new Icon(VaadinIcon.ENVELOPE));

        Button submit = new Button("Submit");

        firstName.setWidth("300px");
        lastName.setWidth("300px");
        email.setWidth("300px");

        VerticalLayout formLayout = new VerticalLayout(firstName, lastName, email, submit);
        formLayout.setWidth("350px");
        formLayout.setPadding(false);
        formLayout.setSpacing(true);
        formLayout.getStyle().set("border-radius", "15px");
        formLayout.setAlignItems(Alignment.CENTER);

        add(title, description, formLayout);
    }
}


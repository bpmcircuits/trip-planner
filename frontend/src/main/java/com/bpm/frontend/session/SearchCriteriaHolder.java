package com.bpm.frontend.session;

import com.bpm.frontend.dto.SearchRequestDTO;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@VaadinSessionScope
@Component
public class SearchCriteriaHolder {

    private SearchRequestDTO lastSearch;

}

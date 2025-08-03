package com.kodilla.tripplanner.nbp.client;

import com.kodilla.tripplanner.nbp.dto.NBPTableDTO;
import com.kodilla.tripplanner.nbp.config.NBPConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class NBPClient {

    private final RestTemplate restTemplate;
    private final NBPConfig nbpConfig;

    public List<NBPTableDTO> getExchangeRates() {
        URI url = getUri();
        try {
            NBPTableDTO[] currencyResponses = restTemplate.getForObject(url, NBPTableDTO[].class);
            return Optional.ofNullable(currencyResponses)
                    .map(Arrays::asList)
                    .orElse(Collections.emptyList());
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    private URI getUri() {
        return UriComponentsBuilder.fromUriString(nbpConfig.getNbpApiEndpoint() + "/exchangerates/tables/A")
                .queryParam("format", "json")
                .build().encode().toUri();
    }
}

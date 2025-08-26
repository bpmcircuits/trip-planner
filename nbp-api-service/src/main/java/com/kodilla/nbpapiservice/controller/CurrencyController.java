package com.kodilla.tripplanner.controller;

import com.kodilla.tripplanner.dto.CurrencyConversionDTO;
import com.kodilla.tripplanner.nbp.dto.NBPTableDTO;
import com.kodilla.tripplanner.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/currency")
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping
    public ResponseEntity<List<NBPTableDTO>> getAvailableCurrencies() {
        return ResponseEntity.ok(currencyService.getAvailableCurrencies());
    }

    @GetMapping("/exchange")
    public ResponseEntity<CurrencyConversionDTO> getExchangeRate(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam BigDecimal amount) {
        return ResponseEntity.ok(currencyService.getExchangedAmount(from, to, amount));
    }
}

package com.kodilla.nbpapiservice.controller;

import com.kodilla.nbpapiservice.dto.CurrencyConversionDTO;
import com.kodilla.nbpapiservice.dto.NBPTableDTO;
import com.kodilla.nbpapiservice.exception.CurrencyNotFoundException;
import com.kodilla.nbpapiservice.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/save")
    public ResponseEntity<NBPTableDTO> getAvailableCurrenciesAndSave() {
        return ResponseEntity.ok(currencyService.updateExchangeRates());
    }

    @GetMapping("/exchange")
    public ResponseEntity<CurrencyConversionDTO> getExchangeRate(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam BigDecimal amount) throws CurrencyNotFoundException {
        return ResponseEntity.ok(currencyService.getExchangedAmount(from, to, amount));
    }
}

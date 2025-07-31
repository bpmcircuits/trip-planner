package com.kodilla.tripplanner.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/currency")
public class CurrencyController {

    @GetMapping
    public String getAvailableCurrencies() {
        return "USD, EUR, GBP, PLN";
    }

    @GetMapping("/convert")
    public String getExchangeRate(@RequestBody String fromCurrency, String toCurrency, String amount) {
        return "Exchange rate from " + fromCurrency + " to " + toCurrency + " is 1.0 (placeholder value)";
    }
}

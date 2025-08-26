package com.kodilla.tripplanner.scheduler;

import com.kodilla.tripplanner.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrencyScheduler {

    private final CurrencyService currencyService;

    @Scheduled(cron = "0 30 12 * * ?") // Runs every day
    //@Scheduled(fixedDelay = 10000)
    public void updateExchangeRates() {
        currencyService.updateExchangeRates();
    }
}

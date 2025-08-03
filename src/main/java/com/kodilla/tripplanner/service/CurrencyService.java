package com.kodilla.tripplanner.service;

import com.kodilla.tripplanner.domain.Currency;
import com.kodilla.tripplanner.dto.CurrencyConversionDTO;
import com.kodilla.tripplanner.dto.NBPTableDTO;
import com.kodilla.tripplanner.mapper.CurrencyMapper;
import com.kodilla.tripplanner.nbp.client.NBPClient;
import com.kodilla.tripplanner.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final NBPClient nbpClient;
    private final CurrencyRepository currencyRepository;
    private final CurrencyMapper currencyMapper;

    public List<NBPTableDTO> getAvailableCurrencies() {
        return nbpClient.getExchangeRates();
    }

    public void updateExchangeRates() {
        NBPTableDTO nbpTableDTO = getAvailableCurrencies().getFirst();
        if (!isExchangeRateActual(nbpTableDTO)) {
            nbpTableDTO.getCurrencies().forEach(nbpRateDTO -> {
                        currencyRepository.save(currencyMapper.mapToCurrency(nbpTableDTO, nbpRateDTO));
            });
            log.info("Exchange rates for table {} have been updated.", nbpTableDTO.getTableName());
        } else {
            log.info("Exchange rates for table {} are already up to date.", nbpTableDTO.getTableName());
        }
    }

    public CurrencyConversionDTO getExchangedAmount(String fromCurrency, String toCurrency, BigDecimal amount) {
        BigDecimal rateA = getRate(fromCurrency);
        BigDecimal rateB = getRate(toCurrency);
        BigDecimal result = amount.multiply(rateA).divide(rateB, 2, RoundingMode.CEILING);
        BigDecimal exchangeRate = rateA.divide(rateB, 4, RoundingMode.CEILING);
        return new CurrencyConversionDTO(fromCurrency, toCurrency, exchangeRate, amount, result, LocalDate.now());
    }

    private BigDecimal getRate(String currencyCode) {
        if (currencyCode.equalsIgnoreCase("PLN")) return BigDecimal.ONE;
        return currencyRepository.findByCurrencyCode(currencyCode).stream()
                .max(Comparator.comparing(Currency::getLastUpdated))
                .map(Currency::getValue)
                .orElseThrow(() -> new IllegalArgumentException("Currency code " + currencyCode + " not found"));
    }

    private boolean isExchangeRateActual(NBPTableDTO nbpTableDTO) {
        LocalDate lastUpdated = nbpTableDTO.getEffectiveDate();
        Optional<Currency> existingCurrency = currencyRepository.findByLastUpdated(lastUpdated).stream().findFirst();
        return existingCurrency.isPresent() && existingCurrency.get().getLastUpdated().equals(lastUpdated);
    }
}

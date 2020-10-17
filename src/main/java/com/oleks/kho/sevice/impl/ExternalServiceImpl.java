package com.oleks.kho.sevice.impl;

import com.oleks.kho.RateAdapter;
import com.oleks.kho.model.ExchangeRatesResponse;
import io.reactivex.Single;
import io.reactivex.functions.BiFunction;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;

@Service
public class ExternalServiceImpl {
    @Inject
    private RateAdapter rateAdapter;

    public Single<Boolean> isStronger(String pastDate, String baseCurrency, String counterCurrency) {
        Single<ExchangeRatesResponse> todaysRates = getRates(baseCurrency);
        Single<ExchangeRatesResponse> historicalRates = rateAdapter.getHistoricalRates(pastDate, baseCurrency);

        return Single.zip(todaysRates, historicalRates, buildBiFunction(counterCurrency, pastDate));
    }

    private BiFunction<ExchangeRatesResponse, ExchangeRatesResponse, Boolean> buildBiFunction(String counterCurrency, String pastDate) {
        return (todaysRatesResponse, historicalRatesResponse) -> {
            BigDecimal todaysNum = todaysRatesResponse.getRates().get(counterCurrency);
            BigDecimal historicalNum = historicalRatesResponse.getRates().get(counterCurrency);

            System.out.println(String.format("today: %s, at %s: %s", todaysNum, pastDate, historicalNum));

            return todaysNum.compareTo(historicalNum) < 0;
        };
    }

    public Single<ExchangeRatesResponse> getRates(String baseCurrency) {
        return rateAdapter.getRateSingle(baseCurrency);
    }
}

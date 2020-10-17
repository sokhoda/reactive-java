package com.oleks.kho;

import com.oleks.kho.deserializator.JacksonConfig;
import com.oleks.kho.model.ExchangeRatesResponse;
import io.reactivex.Single;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

@Slf4j
@Component
public class RateAdapter {
    private static final ClientBuilder CLIENT_BUILDER = ClientBuilder.newBuilder();
    private static final String LATEST_BASE = "http://data.fixer.io/api/latest?base=%s&access_key=0f9147d15c77799c89810ea048612bd5";
    private static final String HISTORICAL_BASE = "http://data.fixer.io/api/%s?base=%s&access_key=0f9147d15c77799c89810ea048612bd5";


    public Single<ExchangeRatesResponse> getRateSingle(String baseCurrency) {
        log.info("About to get rate single  {} ", baseCurrency);
        return Single.create(singleEmitter -> {
            ExchangeRatesResponse rates = CLIENT_BUILDER.build()
                    .register(JacksonConfig.class)
                    .target(String.format(LATEST_BASE, baseCurrency))
                    .request(MediaType.APPLICATION_JSON)
                    .get(ExchangeRatesResponse.class);

            singleEmitter.onSuccess(rates);
        });
    }

    public Single<ExchangeRatesResponse> getHistoricalRates(String pastDate, String baseCurrency) {
        return Single.create(singleEmitter -> {
            ExchangeRatesResponse pastDateRates = CLIENT_BUILDER.build()
                    .register(JacksonConfig.class)
                    .target(String.format(HISTORICAL_BASE, pastDate, baseCurrency))
                    .request(MediaType.APPLICATION_JSON)
                    .get(ExchangeRatesResponse.class);

            singleEmitter.onSuccess(pastDateRates);
        });
    }
}

package com.oleks.kho.controller;

import com.oleks.kho.model.ExchangeRatesResponse;
import com.oleks.kho.sevice.impl.ExternalServiceImpl;
import io.reactivex.Single;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@Slf4j
@RestController
public class RateController {
    @Inject
    private ExternalServiceImpl externalService;

    @GetMapping(path = "/rate")
    public Single<ExchangeRatesResponse> getRates(@RequestParam("baseCurrency") String baseCurrency) {
        log.info("About to get rates {} ", baseCurrency);

        return externalService.getRates(baseCurrency);
    }

    @GetMapping(path = "/todaystrongerthan")
    public Single<Boolean> getRates(
            @RequestParam("date") String pastDate,
            @RequestParam("baseCurrency") String baseCurrency,
            @RequestParam("counterCurrency") String counterCurrency) {
        log.info("About to get isStronger {} ", baseCurrency);

        return externalService.isStronger(pastDate, baseCurrency, counterCurrency);
    }
}

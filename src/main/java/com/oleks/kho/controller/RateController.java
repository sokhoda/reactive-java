package com.oleks.kho.controller;

import com.oleks.kho.model.RateResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateController {

    @GetMapping(path = "/rate")
    public RateResponse getRate(@RequestParam("baseCurrency") String baseCurrency) {
        return RateResponse.builder()
                .base(baseCurrency)
                .rate(0d)
                .build();
    }
}

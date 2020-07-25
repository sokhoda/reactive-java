package com.oleks.kho.sevice.impl;

import com.oleks.kho.model.ExchangeRatesResponse;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ExternalServiceImpl {

    private static final String LATEST_BASE = "http://data.fixer.io/api/latest?base=%s&access_key=0f9147d15c77799c89810ea048612bd5";

    public void makeRequest(String baseCurrency) {
        ClientBuilder clientBuilder = ClientBuilder.newBuilder();

        ExchangeRatesResponse rate = clientBuilder.build()
                .target(String.format(LATEST_BASE, baseCurrency))
                .request(MediaType.APPLICATION_JSON)
                .get(ExchangeRatesResponse.class);
    }
}

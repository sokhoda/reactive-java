package com.oleks.kho.sevice.impl;

import com.google.common.collect.ImmutableMap;
import com.oleks.kho.RateAdapter;
import com.oleks.kho.model.ExchangeRatesResponse;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Map;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExternalServiceImplTest {
    private static final String TEST_BASE_CURRENCY = "EUR";
    private static final String DEFAULT_DATE = "2019-10-10";

    @InjectMocks
    private ExternalServiceImpl sut;

    @Mock
    private RateAdapter rateAdapter;

    @Test
    public void shouldGetRates() {
        ExchangeRatesResponse expectedResponse = createResponse();
        when(rateAdapter.getRateSingle(anyString())).thenReturn(Single.just(expectedResponse));

        TestObserver<ExchangeRatesResponse> testObserver = new TestObserver<>();

        sut.getRates(TEST_BASE_CURRENCY).subscribe(testObserver);

        testObserver.assertValue(expectedResponse);
    }

    private ExchangeRatesResponse createResponse() {
        Map<String, BigDecimal> rates = ImmutableMap.of("USD", BigDecimal.valueOf(1.33));
        return new ExchangeRatesResponse(TEST_BASE_CURRENCY, DEFAULT_DATE, rates);
    }
}

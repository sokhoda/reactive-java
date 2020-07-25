package com.oleks.kho.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;


@Getter
@Builder
public class RateResponse {
	private String base;
	private Double rate;
}

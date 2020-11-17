package com.hum.coin.model;

import java.math.BigDecimal;

public class CoinStatistic {

	private final BigDecimal average;

	private final BigDecimal percentageDiffAtMax;

	public CoinStatistic(BigDecimal average, BigDecimal percentageDiffAtMax) {
		super();
		this.average = average;
		this.percentageDiffAtMax = percentageDiffAtMax;
	}

	public BigDecimal getAverage() {
		return average;
	}

	public BigDecimal getPercentageDiffAtMax() {
		return percentageDiffAtMax;
	}
}

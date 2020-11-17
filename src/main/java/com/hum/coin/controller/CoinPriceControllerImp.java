package com.hum.coin.controller;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.Date;
import java.util.NoSuchElementException;

import com.hum.coin.model.CoinStatistic;

public class CoinPriceControllerImp implements CoinPriceController {

	private final CoinPriceDataController coinPriceData;

	public CoinPriceControllerImp(CoinPriceDataController coinPriceData) {
		this.coinPriceData = coinPriceData;
	}

	@Override
	public BigDecimal lastPrice(final Date timestamp) {

		final long time = timestamp.getTime();

		return this.coinPriceData.getList().stream()
			.filter(p -> p.getTimestanp().compareTo(timestamp) <= 0)
			.min(Comparator.comparing(p -> time - p.getTimestanp().getTime()))
		.orElseThrow(() -> new NoSuchElementException("No value present"))
		.getPrice();
	}

	@Override
	public CoinStatistic statisticRange(final Date from, final Date to) {
		
		final MathContext mathContext = new MathContext(4, RoundingMode.HALF_UP);

		BigDecimal average = new BigDecimal(
				this.coinPriceData.getList().stream()
				.filter(p -> p.getTimestanp().compareTo(from) >= 0 &&  p.getTimestanp().compareTo(to) <= 0)
				.mapToDouble(p -> p.getPrice().doubleValue())
				.average()
			.orElseThrow(() -> new NoSuchElementException("No value present in range"))
		, mathContext);
		
		return new CoinStatistic(average, new BigDecimal(1, mathContext).subtract( average.divide(this.coinPriceData.getMayPrice(), 4, RoundingMode.HALF_UP)));
	}
}
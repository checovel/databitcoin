package com.hum.coin.controller;

import java.math.BigDecimal;
import java.util.List;

import com.hum.coin.model.CoinPrice;

public interface CoinPriceDataController {
	List<CoinPrice> getList();

	BigDecimal getMayPrice();
}

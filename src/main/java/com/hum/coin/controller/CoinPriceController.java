package com.hum.coin.controller;

import java.math.BigDecimal;
import java.util.Date;

import com.hum.coin.model.CoinStatistic;

public interface CoinPriceController {

	/*
	 * Obtener el precio del bitcoin en cierto timestamp.
	 */
	BigDecimal lastPrice(Date timestamp);

	/**
	 * Conocer el promedio de valor entre dos timestamps así como la diferencia porcentual entre ese valor promedio y el valor máximo almacenado para toda la serie temporal disponible.
	 */
	CoinStatistic statisticRange(Date timestampFrom, Date timestampTo);

}

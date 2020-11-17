package com.hum.coin.rest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hum.coin.controller.CoinPriceController;
import com.hum.coin.model.CoinStatistic;
 

@RestController
@RequestMapping("/api/bitcoin")
public class Bitcoin {
	private static final Logger logger = LoggerFactory.getLogger(Bitcoin.class);

	@Autowired
	private CoinPriceController repository;

	@GetMapping("/last_price")
	public ResponseEntity<BigDecimal> lastPrice(
			@RequestParam(value = "timestamp", required = true)
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
			Date timestamp) {

		try {
			return ResponseEntity.ok(repository.lastPrice(timestamp));
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/statistic_range")
	public ResponseEntity<CoinStatistic> statisticRange(
			@RequestParam(value = "from", required = true) 
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
			Date from,
			@RequestParam(value = "to", required = true)
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
			Date to) {

		try {
			return ResponseEntity.ok(repository.statisticRange(from, to));
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
}
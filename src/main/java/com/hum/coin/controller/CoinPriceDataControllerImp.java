package com.hum.coin.controller;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hum.coin.model.CoinPrice;
import com.hum.coin.model.CurrencyToCurrencyPrice;

public class CoinPriceDataControllerImp implements CoinPriceDataController {

	Logger logger = LoggerFactory.getLogger(CoinPriceDataControllerImp.class);
	
	private final String url;

	private final CopyOnWriteArrayList<CoinPrice> list;
	private final int interval;

	private volatile BigDecimal mayPrice;
	private final RestTemplate restTemplate;
	private final String headerValue;

	public CoinPriceDataControllerImp(RestTemplate restTemplate, String url, int interval, String headerValue) {
		this.url = url;
		this.restTemplate = restTemplate;
		this.interval = interval;

		this.list = new CopyOnWriteArrayList<>();
		this.mayPrice = new BigDecimal(0);

		this.headerValue = headerValue;
		this.runGetData();
	}

	private final void runGetData() {

		logger.info("Start runGetData");
		Runnable runnable = () -> {
			try {
				while (true) {
					requestePrice();
					TimeUnit.SECONDS.sleep(this.interval);
				}
			} catch (InterruptedException e) {
				logger.error(e.getMessage());
			}
		};

		Thread thread = new Thread(runnable);
		thread.start();
	}

	public synchronized void requestePrice() {

		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, buildHeader(), String.class);

			if (response.getStatusCode() == HttpStatus.OK) {

				CurrencyToCurrencyPrice readValue = new ObjectMapper().readValue(response.getBody(), CurrencyToCurrencyPrice.class);
				logger.info("new getData {}", readValue.getLprice());
				
				if (this.mayPrice.compareTo(readValue.getLprice()) < 0) {
					this.mayPrice = readValue.getLprice();
				}

				if (this.list.size() == 0 || this.list.get(this.list.size() - 1).getPrice().compareTo(readValue.getLprice()) != 0) {
					this.list.add(new CoinPrice(new Date(), readValue.getLprice()));
					logger.info("new price {}", readValue.getLprice());
				}
			}

		} catch (JsonMappingException e) {
			logger.error(e.getMessage());
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage());
		} catch (ResourceAccessException e) {
			logger.error(e.getMessage());
		}
	}

	private HttpEntity<String> buildHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("user-agent", headerValue);
		return new HttpEntity<String>("parameters", headers);
	}
	public synchronized List<CoinPrice> getList() {
		return list;
	}

	public synchronized BigDecimal getMayPrice() {
		return mayPrice;
	}

}

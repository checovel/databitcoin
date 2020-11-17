package com.hum.coin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.hum.coin.controller.CoinPriceController;
import com.hum.coin.controller.CoinPriceControllerImp;
import com.hum.coin.controller.CoinPriceDataController;
import com.hum.coin.controller.CoinPriceDataControllerImp;

@SpringBootApplication
public class DatabitcoinApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatabitcoinApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}

	@Bean
	public CoinPriceDataController data(@Autowired RestTemplate restTemplate
			, @Value("${bitcoin.url}") String url
			, @Value("${bitcoin.interval}") int interval
			, @Value("${header}") String header) {
		return new CoinPriceDataControllerImp(restTemplate,  url, interval, header);
	}
	
	@Bean
	public CoinPriceController getBitCoinPrice(@Autowired CoinPriceDataController coinPriceData) {
		return new CoinPriceControllerImp(coinPriceData);
	}
	
}

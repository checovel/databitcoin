package com.hum.coin.model;

import java.math.BigDecimal;
import java.util.Date;

public class CoinPrice {

	private Date timestanp;
	
	private BigDecimal price;

	public CoinPrice(Date timestanp, BigDecimal price) {
		super();
		this.timestanp = timestanp;
		this.price = price;
	}

	public Date getTimestanp() {
		return timestanp;
	}

	public BigDecimal getPrice() {
		return price;
	}
}

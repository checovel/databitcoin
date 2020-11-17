package com.hum.coin.model;

import java.math.BigDecimal;

public class CurrencyToCurrencyPrice {
	private BigDecimal lprice;
	private String curr1;
	private String curr2;

	public CurrencyToCurrencyPrice() {
	}

	public CurrencyToCurrencyPrice(BigDecimal lprice, String curr1, String curr2) {
		super();
		this.lprice = lprice;
		this.curr1 = curr1;
		this.curr2 = curr2;
	}

	public BigDecimal getLprice() {
		return lprice;
	}

	public String getCurr1() {
		return curr1;
	}

	public String getCurr2() {
		return curr2;
	}

	public void setLprice(BigDecimal lprice) {
		this.lprice = lprice;
	}

	public void setCurr1(String curr1) {
		this.curr1 = curr1;
	}

	public void setCurr2(String curr2) {
		this.curr2 = curr2;
	}

}

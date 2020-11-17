package com.hum.bitcoin;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.hum.coin.DatabitcoinApplication;
import com.hum.coin.controller.CoinPriceController;
import com.hum.coin.controller.CoinPriceControllerImp;
import com.hum.coin.controller.CoinPriceDataController;
import com.hum.coin.model.CoinPrice;
import com.hum.coin.model.CoinStatistic;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DatabitcoinApplication.class)
class CoinPriceControllerTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private CoinPriceController repository;

	@Test
	public void test() {
		assertNotEquals(null, repository);
	}

	@Test
	public void testLastPrice() {
		final MathContext mathContext = new MathContext(4, RoundingMode.HALF_UP);

		Instant timeStart = Instant.now().plusSeconds(-52);

		CoinPriceDataController data = new CoinPriceDataController() {

			public final BigDecimal mayPrice = new BigDecimal(7, mathContext);

			public final List<CoinPrice> list = Collections.unmodifiableList(new ArrayList<CoinPrice>() {
				{
					add(new CoinPrice(Date.from(timeStart.plusSeconds(10)), new BigDecimal(5, mathContext)));
					add(new CoinPrice(Date.from(timeStart.plusSeconds(20)), new BigDecimal(6, mathContext)));
					add(new CoinPrice(Date.from(timeStart.plusSeconds(30)), new BigDecimal(7, mathContext)));
					add(new CoinPrice(Date.from(timeStart.plusSeconds(40)), new BigDecimal(4, mathContext)));
					add(new CoinPrice(Date.from(timeStart.plusSeconds(50)), new BigDecimal(2, mathContext)));
				}
			});

			@Override
			public List<CoinPrice> getList() {
				return list;
			}

			@Override
			public BigDecimal getMayPrice() {
				return mayPrice;
			}
		};
		CoinPriceController coinPrice = new CoinPriceControllerImp(data);

		assertThrows(NoSuchElementException.class, () ->coinPrice.lastPrice(Date.from(timeStart.plusSeconds(-2))));
		assertTrue(new BigDecimal(7, mathContext).equals(coinPrice.lastPrice(Date.from(timeStart.plusSeconds(30)))));
		assertTrue(new BigDecimal(2, mathContext).equals(coinPrice.lastPrice(Date.from(timeStart.plusSeconds(60)))));
	}

	@Test
	public void statisticRangeTest() {
		final MathContext mathContext = new MathContext(4, RoundingMode.HALF_UP);

		Instant timeStart = Instant.now().plusSeconds(-52);

		CoinPriceDataController data = new CoinPriceDataController() {

			public final BigDecimal mayPrice = new BigDecimal(7, mathContext);

			public final List<CoinPrice> list = Collections.unmodifiableList(new ArrayList<CoinPrice>() {
				{
					add(new CoinPrice(Date.from(timeStart.plusSeconds(10)), new BigDecimal(5, mathContext)));
					add(new CoinPrice(Date.from(timeStart.plusSeconds(20)), new BigDecimal(6, mathContext)));
					add(new CoinPrice(Date.from(timeStart.plusSeconds(30)), new BigDecimal(7, mathContext)));
					add(new CoinPrice(Date.from(timeStart.plusSeconds(40)), new BigDecimal(4, mathContext)));
					add(new CoinPrice(Date.from(timeStart.plusSeconds(50)), new BigDecimal(2, mathContext)));
				}
			});

			@Override
			public List<CoinPrice> getList() {
				return list;
			}

			@Override
			public BigDecimal getMayPrice() {
				return mayPrice;
			}
		};

		CoinPriceController coinPrice = new CoinPriceControllerImp(data);
		
		CoinStatistic statistic = coinPrice.statisticRange(Date.from(timeStart.plusSeconds(11)), Date.from(timeStart.plusSeconds(41)));

		System.out.println(statistic.getAverage().setScale(4, RoundingMode.HALF_UP) + ", " + statistic.getPercentageDiffAtMax());
		assertEquals(new BigDecimal(5.6667, mathContext), statistic.getAverage());
		assertEquals(new BigDecimal(0.1904, mathContext), statistic.getPercentageDiffAtMax());

		assertThrows(NoSuchElementException.class, () -> coinPrice.statisticRange(Date.from(timeStart.plusSeconds(-10)), Date.from(timeStart.plusSeconds(-2))));

		assertThrows(NoSuchElementException.class, () -> coinPrice.statisticRange(Date.from(timeStart.plusSeconds(52)), Date.from(timeStart.plusSeconds(53))));

	}
}

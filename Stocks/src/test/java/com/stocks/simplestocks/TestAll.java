package com.stocks.simplestocks;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.Test;

import com.stocks.simplestocks.model.Stock;
import com.stocks.simplestocks.model.StockTypes;
import com.stocks.simplestocks.utils.GBCECalculator;

/**
 * In a real enterprise application we would cover almost the entire code base
 * with mockup objects and stuff. Because this is just an example I'm only
 * checking if the basic calculations work correctly.
 * 
 * @author datsko.petrunov
 *
 */
public class TestAll {

	/**
	 * ############### TESTS GBCE #############
	 */

	@Test
	public void testGBCECalculator() {

		Map<String, Stock> stocks = new HashMap<String, Stock>();
		stocks.put("TEA", new Stock("TEA", StockTypes.COMMON, 0.0, 0.0, 100.0));
		stocks.put("POP", new Stock("POP", StockTypes.COMMON, 8.0, 0.0, 100.0));
		stocks.put("ALE", new Stock("ALE", StockTypes.COMMON, 23.0, 0.0, 60.0));
		stocks.put("GIN", new Stock("GIN", StockTypes.PREFERRED, 8.0, 0.2, 100.0));
		stocks.put("JOE", new Stock("JOE", StockTypes.COMMON, 13.0, 0.0, 250.0));

		for (Stock stock : stocks.values()) {
			for (int i = 1; i <= 5; i++) {
				stock.buy(i, 5.0);
			}
		}

		Double GBCEallShareIndexBuy = GBCECalculator.globalIndex(stocks);

		assertEquals(1.379729661461215, GBCEallShareIndexBuy, 0.0);

		for (Stock stock : stocks.values()) {
			for (int i = 1; i <= 5; i++) {
				stock.sell(i, 15.0);

			}
		}

		Double GBCEallShareIndexSell = GBCECalculator.globalIndex(stocks);
		assertEquals(1.7187719275874789, GBCEallShareIndexSell, 0.0);

	}

	/**
	 * ############ TESTS STOCKS ##############
	 */

	@Test
	public void testBuying() {

		Stock stockALE = new Stock("TEA", StockTypes.COMMON, 0.0, 0.0, 100.0);
		stockALE.buy(1, 100.0);
		assertEquals(100.0, stockALE.getLastStockPrice(), 0.0);
	}

	@Test
	public void testSelling() {

		Stock stockALE = new Stock("TEA", StockTypes.COMMON, 0.0, 0.0, 100.0);
		stockALE.sell(1, 100.0);
		assertEquals(100.0, stockALE.getLastStockPrice(), 0.0);
	}

	@Test
	public void testDividendYield() {

		Stock tea = new Stock("TEA", StockTypes.COMMON, 0.0, 0.0, 100.0);

		// Test dividend for Common
		Double dividendALE = tea.calculateDividend(10.0);
		Double expectedDividendALE = tea.getLastDividend() / 1.0;

		assertEquals(expectedDividendALE, dividendALE, 0.0);

	}

	@Test
	public void testPERatio() {

		Stock tea = new Stock("TEA", StockTypes.COMMON, 0.0, 0.0, 100.0);
		Double PERatio = tea.PERatio(10.0);
		Double expectedPE = 1.00 / tea.getLastDividend();
		assertEquals(expectedPE, PERatio, 0.0);
	}

	@Test
	public void testPriceLastFifteenMinutes() {
		// TODO: Later
	}

}

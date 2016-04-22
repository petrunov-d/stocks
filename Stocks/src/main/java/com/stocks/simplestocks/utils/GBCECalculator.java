package com.stocks.simplestocks.utils;

import java.util.Map;

import com.stocks.simplestocks.model.Stock;

/**
 * Basic GBCECalculator class.
 * 
 * @author datsko.petrunov
 *
 */
public class GBCECalculator {

	/**
	 * Calculate the GBCE All Share Index for all stocks
	 * 
	 * @param our
	 *            stocks
	 * @return The GBCE All Share Index
	 */
	public static Double globalIndex(Map<String, Stock> stocks) {

		Double allShareIdx = stocks.values().parallelStream().filter(u -> u.getLastStockPrice() > 0)
				.mapToDouble(u -> u.getLastStockPrice()).average().getAsDouble();

		return Math.pow(allShareIdx, 1.0 / stocks.size());
	}

}

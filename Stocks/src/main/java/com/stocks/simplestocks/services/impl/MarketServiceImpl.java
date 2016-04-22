package com.stocks.simplestocks.services.impl;

import java.util.HashMap;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.stocks.simplestocks.model.Stock;
import com.stocks.simplestocks.model.StockTypes;
import com.stocks.simplestocks.services.MarketService;
import com.stocks.simplestocks.utils.GBCECalculator;

@Service
public class MarketServiceImpl implements MarketService {

	private static Log log = LogFactory.getLog(MarketServiceImpl.class);

	/**
	 * Our "database".
	 */
	private static HashMap<String, Stock> database = new HashMap<String, Stock>();

	static {

		database.put("TEA", new Stock("TEA", StockTypes.COMMON, 0.0, 0.0, 100.0));
		database.put("POP", new Stock("POP", StockTypes.COMMON, 8.0, 0.0, 100.0));
		database.put("ALE", new Stock("ALE", StockTypes.COMMON, 23.0, 0.0, 60.0));
		database.put("GIN", new Stock("GIN", StockTypes.PREFERRED, 8.0, 0.2, 100.0));
		database.put("JOE", new Stock("JOE", StockTypes.COMMON, 13.0, 0.0, 250.0));
	}

	@Override
	public HashMap<String, Stock> doTrade() {

		try {

			for (Stock stock : database.values()) {

				if (getRandomInt(0, 100) % 2 == 0) {

					stock.buy(getRandomInt(0, 5), getRandomDouble(1, 20));
				} else {

					stock.sell(getRandomInt(0, 5), getRandomDouble(1, 20));
				}

			}
		} catch (Exception e) {

			log.error("Something went wrong: ", e);
		}

		return database;
	}

	@Override
	public Stock findStockBySymbol(String symbol) {
		
		return database.get(symbol);

	}

	@Override
	public Double getGBCEIndex() {

		log.debug("Calculating GBCE index now... ");
		return GBCECalculator.globalIndex(database);
	}

	private Double getRandomDouble(int min, int max) {

		Random r = new Random();

		return min + (max - min) * r.nextDouble();
	}

	private Integer getRandomInt(int min, int max) {

		return getRandomDouble(min, max).intValue();
	}
}

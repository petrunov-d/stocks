package com.stocks.simplestocks.services;

import java.util.HashMap;

import com.stocks.simplestocks.model.Stock;

/**
 * Market service that manages operations on our Stocks.
 * 
 * @author datsko.petrunov
 *
 */
public interface MarketService {

	/**
	 * Does some trades, manipulating them a little bit to
	 * simulate actual market operations.
	 * 
	 * @return - a list of stocks.
	 */
	public HashMap<String, Stock> doTrade();

	/**
	 * Gets the current value of the GBCE index.
	 * 
	 * @return - the GBCE index.
	 */
	public Double getGBCEIndex();
	
	/**
	 * Gets a stock by it's symbol
	 * @param symbol
	 */
	public Stock findStockBySymbol(String symbol);

}

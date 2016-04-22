package com.stocks.simplestocks.model;

import java.io.Serializable;
import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Stock class, holds information about a stock and some helper methods.
 * 
 * @author datsko.petrunov
 *
 */
public class Stock implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4810905208900216736L;

	private static final Logger logger = LoggerFactory.getLogger(Stock.class);

	private static final int FIFTEEN_MINUTES = 15 * 60 * 1000;

	private String stockSymbol;

	private StockTypes type;

	private Double lastDividend;

	private Double fixedDividend;

	private Double parValue;

	private TreeMap<Date, Trade> trades;

	/**
	 * Default AllArgs constructor.
	 * 
	 * @param symbol
	 *            = the stock symbol.
	 * @param type
	 *            = the stock type.
	 * @param lastDividend
	 *            - the value of the last dividend.
	 * @param fixedDividend
	 *            - the value of the fixed dividend.
	 * @param parValue
	 *            - the par value.
	 */
	public Stock(String symbol, StockTypes type, Double lastDividend, Double fixedDividend, Double parValue) {

		this.setStockSymbol(symbol);

		this.setType(type);

		this.setLastDividend(lastDividend);

		this.setFixedDividend(fixedDividend);

		this.setParValue(parValue);

		this.trades = new TreeMap<Date, Trade>();
	}

	/**
	 * Gets the symbol descriptor for the stock.
	 * 
	 * @return - the symbol.
	 */
	public String getStockSymbol() {

		return stockSymbol;
	}

	/**
	 * Sets the symbol descriptor of a stock.
	 * 
	 * @param the
	 *            symbol.
	 */
	public void setStockSymbol(String symbol) {

		this.stockSymbol = symbol;
	}

	/**
	 * Gets the type of the stock - {@link StockTypes}
	 * 
	 * @return
	 */
	public StockTypes getType() {

		return type;
	}

	/**
	 * Sets the stock type
	 * 
	 * @param type
	 *            - see {@link StockTypes}
	 */
	public void setType(StockTypes type) {
		this.type = type;
	}

	/**
	 * Gets the last dividend yield.
	 * 
	 * @return
	 */
	public Double getLastDividend() {

		return lastDividend;
	}

	/**
	 * Gets the last divident yield.
	 * 
	 * @param lastDividend
	 */
	public void setLastDividend(Double lastDividend) {

		this.lastDividend = lastDividend;
	}

	/**
	 * Gets the fixed dividend yield.
	 * 
	 * @return
	 */
	public Double getFixedDividend() {

		return fixedDividend;
	}

	/**
	 * sets the fixed dividend yield.
	 * 
	 * @param fixedDividend
	 */
	public void setFixedDividend(Double fixedDividend) {

		this.fixedDividend = fixedDividend;
	}

	/**
	 * Gets the par value.
	 * 
	 * @return
	 */
	public Double getParValue() {

		return parValue;
	}

	/**
	 * Sets the par value.
	 * 
	 * @param parValue
	 */
	public void setParValue(Double parValue) {

		this.parValue = parValue;
	}

	/**
	 * Gets all the trade performed on this stock.
	 * 
	 * @return
	 */
	public TreeMap<Date, Trade> getTrades() {

		return trades;
	}

	/**
	 * Sets a list of trade associated with this stock.
	 * 
	 * @param trades
	 */
	public void setTrades(TreeMap<Date, Trade> trades) {
		this.trades = trades;
	}

	/**
	 * Calculate the dividend.
	 * 
	 * @param price
	 *            - price of our stock.
	 * @return The dividend
	 */
	public Double calculateDividend(Double price) {
		switch (this.getType()) {

		case COMMON:

			return this.getLastDividend() / price;

		case PREFERRED:

			return this.getFixedDividend() * this.getParValue() / price;
		}

		// this shouldnt happen.
		return -1.0;
	}

	/**
	 * Calculate P/E Ratio.
	 * 
	 * @param price
	 *            - price of our stock.
	 * @return The P/E Ratio
	 */
	public Double PERatio(Double price) {

		return price / this.getLastDividend();
	}

	/**
	 * Buy stock, specifying quantity and price
	 * 
	 * @param quantity
	 *            The quantity of stock to BUY
	 * @param price
	 *            The price of the stock
	 */
	public void buy(Integer quantity, Double price) {

		Trade trade = new Trade(TradeIndicators.BUY, quantity, price);

		this.trades.put(new Date(), trade);
	}

	/**
	 * Sell stock, specifying quantity and price
	 * 
	 * @param quantity
	 *            TYhe quantity of stock to SELL
	 * @param price
	 *            The price of the stock
	 */
	public void sell(Integer quantity, Double price) {

		Trade trade = new Trade(TradeIndicators.SELL, quantity, price);

		this.trades.put(new Date(), trade);
	}

	/**
	 * Return the current price of the stock.
	 * 
	 * @return The last trade price
	 */
	public Double getLastStockPrice() {

		if (this.trades.size() > 0) {

			return this.trades.lastEntry().getValue().getPrice();
		}

		return 0.0;
	}

	/**
	 * Calculates the price of the stock based on trades in the last 15 mins.
	 * 
	 * @return the 15 minute stock price
	 */
	public Double getStockPriceLastFifteenMinutes() {

		Date startTime = new Date(new Date().getTime() - FIFTEEN_MINUTES);

		SortedMap<Date, Trade> trades = this.trades.tailMap(startTime);

		Double stockPrice = trades.values().parallelStream().mapToDouble(u -> (u.getPrice() * u.getQuantity())).sum();

		Integer totalQuantity = (int) trades.values().parallelStream().mapToDouble(u -> u.getQuantity()).sum();

		return stockPrice / totalQuantity;
	}

	@Override
	public String toString() {

		return "Stock: " + stockSymbol + " type " + type + " last dividend: " + lastDividend + " fixed dividend: "
				+ fixedDividend + " par value : " + parValue;
	}

	@Override
	public boolean equals(Object obj) {

		if (obj == this) {
			return true;
		}

		if ((obj == null) || (obj.getClass() != this.getClass())) {
			return false;
		}

		Stock stock = (Stock) obj;

		return stock.stockSymbol.equals(this.stockSymbol) && stock.type == this.type
				&& stock.lastDividend == this.lastDividend && stock.fixedDividend == this.fixedDividend
				&& stock.parValue == this.parValue;

	}

	@Override
	public int hashCode() {

		final int prime = 31;

		int result = 1;

		result = prime * result + ((stockSymbol == null) ? 0 : stockSymbol.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((lastDividend == null) ? 0 : lastDividend.hashCode());
		result = prime * result + ((fixedDividend == null) ? 0 : fixedDividend.hashCode());
		result = prime * result + ((parValue == null) ? 0 : parValue.hashCode());

		return result;
	}
}

package com.stocks.simplestocks.model;

import java.io.Serializable;

/**
 * Basic Trade Object class.
 * 
 * @author datsko.petrunov
 *
 */
public class Trade implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7611368130849012845L;

	private Integer quantity;

	private Double price;

	private TradeIndicators indicator;

	/**
	 * Gets the trade quantity.
	 * 
	 * @return the quantity.
	 */
	public Integer getQuantity() {

		return quantity;
	}

	/**
	 * Sets the quantity.
	 * 
	 * @param quantity
	 *            - the amount to trade.
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * Gets trade price.
	 * 
	 * @return the price.
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * Sets the price.
	 * 
	 * @throws IllegalArgumentException
	 *             if price is less than 0.
	 * @param price
	 */
	public void setPrice(Double price) {

		if (price < 0) {

			throw new IllegalArgumentException("Price cannot be less than 0.");
		}

		this.price = price;
	}

	/**
	 * Gets the trade indicator.
	 * 
	 * @return {@link TradeIndicators} value.
	 */
	public TradeIndicators getIndicator() {
		return indicator;
	}

	/**
	 * Sets the type of the trade.
	 * 
	 * @param indicator
	 *            {@link TradeIndicators}
	 */
	public void setIndicator(TradeIndicators indicator) {
		this.indicator = indicator;
	}

	/**
	 * Default AllArgs Constructor.
	 * 
	 * @param indicator
	 *            - {@link TradeIndicators} - Buy or Sell.
	 * @param quantity
	 *            - the amount.
	 * @param price
	 *            - the price.
	 */
	public Trade(TradeIndicators indicator, Integer quantity, Double price) {
		this.setIndicator(indicator);
		this.setQuantity(quantity);
		this.setPrice(price);
	}

	@Override
	public String toString() {
		return "Trade price " + price + " quantity: " + quantity + " indicator" + indicator;
	}

	@Override
	public boolean equals(Object obj) {

		if (obj == this) {
			return true;
		}

		if ((obj == null) || (obj.getClass() != this.getClass())) {
			return false;
		}

		Trade trade = (Trade) obj;

		return trade.quantity == this.quantity && trade.price == this.price && trade.indicator == this.indicator;

	}

	@Override
	public int hashCode() {

		final int prime = 31;

		int result = 1;

		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
		result = prime * result + ((indicator == null) ? 0 : indicator.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());

		return result;
	}
}

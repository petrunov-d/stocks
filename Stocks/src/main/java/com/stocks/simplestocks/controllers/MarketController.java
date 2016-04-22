package com.stocks.simplestocks.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stocks.simplestocks.model.Stock;
import com.stocks.simplestocks.services.MarketService;

@Controller
public class MarketController {

	@Inject
	MarketService marketService;

	private static final Logger logger = LoggerFactory.getLogger(MarketController.class);

	/**
	 * Returns a list of stocks simulating trading and buying each time. The
	 * process is randomized.
	 * 
	 * @return A mock-up of stock information.
	 */
	@RequestMapping(value = "/liststocks", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Stock> getStocksInfo() {

		logger.info("Serving list of stocks...");
		return this.marketService.doTrade();
	}

	/**
	 * Returns the value of the GBCE Index/
	 * 
	 * @return the current value of the GBCE index.
	 */
	@RequestMapping(value = "/getGBCEIndex", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Double> getGBCEIndex() {

		Map<String, Double> map = new HashMap<String, Double>();

		logger.info("Serving index value...");
		map.put("Current GBCE index value", this.marketService.getGBCEIndex());
		return map;
	}

	/**
	 * Returns the value of the GBCE Index/
	 * 
	 * @return the current value of the GBCE index.
	 */
	@RequestMapping(value = "/getStockPriceFifteenMinutes", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Stock> getStockFifteenMinutePrice(
			@RequestParam(value = "symbol", required = true) String symbol) {
		
		Map<String, Stock> stockMessage = new HashMap<>();
		
		Stock st = this.marketService.findStockBySymbol(symbol);
		
		if(st == null) {
			
			stockMessage.put("Stock not found.", null);
		} else {
			
			stockMessage.put("15 minute price " + st.getStockPriceLastFifteenMinutes(), st);
		}

		logger.info("Serving stock for symbol: " + symbol);
		return stockMessage;
	}
}

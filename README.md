# stocks
Stocks sample project

Implementation of super simple stocks. 

Users can get various stock information through several publicly exposed methods, all of which return a JSON representation
of our stocks.

Mappings:

 - Mapped "{[/getStockPriceFifteenMinutes?symbol="S"],methods=[GET]}"  - returns a stock price based on the trades in the last 15 minutes.
 - Mapped "{[/liststocks],methods=[GET]}" - lists all stocks and performs market simulations
 - Mapped "{[/getGBCEIndex],methods=[GET]}" - gets the current GBCE index. A call to /liststocks first is required.

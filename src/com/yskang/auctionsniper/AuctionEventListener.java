package com.yskang.auctionsniper;

import java.util.EventListener;

public interface AuctionEventListener extends EventListener{

	public enum PriceSource{
		FromSniper, FromOtherBidder;
	};
	
	public void auctionClosed();

	public void currentPrice(int price, int increment, PriceSource priceSource);
	
}

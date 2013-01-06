package com.yskang.auctionsniper;

public interface AuctionEventListener {

	public enum PriceSource{
		FromSniper, FromOtherBidder;
	};
	
	public void auctionClosed();

	public void currentPrice(int price, int increment, PriceSource priceSource);
	
}

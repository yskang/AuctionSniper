package com.yskang.auctionsniper;

public interface Auction {
	void bid(int amount);
	void join();
	void addAuctionEventListener(AuctionSniper auctionSniper);
}

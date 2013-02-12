package com.yskang.auctionsniper;

public class SniperLauncher implements UserRequestListener{
	private final AuctionHouse auctionHouse;
	private final SniperCollector collector;
	
	public SniperLauncher(AuctionHouse auctionHouse, SniperCollector collector) {
		this.auctionHouse = auctionHouse;
		this.collector = collector;
	}
	
	public void joinAuction(String itemId){
		Auction auction = auctionHouse.auctionFor(itemId);
		AuctionSniper sniper = new AuctionSniper(itemId, auction);
		auction.addAuctionEventListener(sniper);
		collector.addSniper(sniper);
		auction.join();
	}
	
}

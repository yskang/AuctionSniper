package com.yskang.auctionsniper;


public class AuctionSniper implements AuctionEventListener {

	private final SniperListener sniperListener;
	private Auction mAuction;
	
	public AuctionSniper(Auction auction, SniperListener sniperListener) {
		this.mAuction = auction;
		this.sniperListener = sniperListener;
	}

	@Override
	public void auctionClosed() {
		sniperListener.sniperLost();
	}

	@Override
	public void currentPrice(int price, int increment) {
		mAuction.bid(price + increment);
		sniperListener.sniperBidding();
	}
}

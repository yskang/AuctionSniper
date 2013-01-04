package com.yskang.auctionsniper;

import android.util.Log;

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
		Log.d("yskang", "check current price, and start bidding..");
		mAuction.bid(price + increment);
		Log.d("yskang", "and.. change the UI state to bidding.");
		sniperListener.sniperBidding();
	}
}

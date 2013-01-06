package com.yskang.auctionsniper;

public class AuctionSniper implements AuctionEventListener {

	private final SniperListener sniperListener;
	private Auction mAuction;
	private boolean isWinning = false;

	public AuctionSniper(Auction auction, SniperListener sniperListener) {
		this.mAuction = auction;
		this.sniperListener = sniperListener;
	}

	@Override
	public void auctionClosed() {
		if(isWinning){
			sniperListener.sniperWon();
		}else{
			sniperListener.sniperLost();
		}
	}

	@Override
	public void currentPrice(int price, int increment, PriceSource priceSource) {
		isWinning = priceSource == PriceSource.FromSniper;
		if(isWinning){
			sniperListener.sniperWinning();
		}else{
			mAuction.bid(price + increment);
			sniperListener.sniperBidding();
		}
	}
}

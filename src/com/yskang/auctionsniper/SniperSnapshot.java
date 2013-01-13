package com.yskang.auctionsniper;

public class SniperSnapshot {
	public String itemId;
	public int lastPrice;
	public int lastBid;
	public SniperState state;
	
	public SniperSnapshot(String itemId, int lastPrice, int lastBid, SniperState sniperState){
		this.itemId = itemId;
		this.lastPrice = lastPrice;
		this.lastBid = lastBid;
		this.state = sniperState;
	}

	public String getItemId() {
		return itemId;
	}	
	
	public int getLastPrice(){
		return lastPrice;
	}
	
	public int getLastBid(){
		return lastBid;
	}

	public SniperState getStatus() {
		return state;
	}

	public static SniperSnapshot joining(String itemId) {
		return new SniperSnapshot(itemId, 0, 0, SniperState.JOINING);
	}

	public SniperSnapshot winning(int newLastPrice) {
		return new SniperSnapshot(itemId, newLastPrice, lastBid, SniperState.WINNING);
	}

	public SniperSnapshot bidding(int newLastPrice, int newLastBid) {
		return new SniperSnapshot(itemId, newLastPrice, newLastBid, SniperState.BIDDING);
	}

	public SniperSnapshot closed() {
		return new SniperSnapshot(itemId, lastPrice, lastBid, state.whenAuctionClosed());
	}
}

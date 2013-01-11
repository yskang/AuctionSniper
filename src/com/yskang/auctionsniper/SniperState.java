package com.yskang.auctionsniper;

public class SniperState {
	public final String itemId;
	public final int lastPrice;
	public final int lastBid;
	public final int statusId = R.string.status_init;
	
	public SniperState(String itemId, int lastPrice, int lastBid){
		this.itemId = itemId;
		this.lastPrice = lastPrice;
		this.lastBid = lastBid;
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

	public Object getStatus() {
		return statusId;
	}
}

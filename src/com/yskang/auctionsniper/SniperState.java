package com.yskang.auctionsniper;

public class SniperState {
	public final String itemId;
	public final int lastPrice;
	public final int lastBid;
	public final int statusId;
	
	public SniperState(String itemId, int lastPrice, int lastBid, int statusId){
		this.itemId = itemId;
		this.lastPrice = lastPrice;
		this.lastBid = lastBid;
		this.statusId = statusId;
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

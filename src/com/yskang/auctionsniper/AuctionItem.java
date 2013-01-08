package com.yskang.auctionsniper;

public class AuctionItem {
	private String itemName;
	private int lastPrice;
	private int lastBid;
	private String status;
	
	public void setItemName(String name){
		itemName = name;
	}
	
	public void setLastPrice(int price){
		lastPrice = price;
	}
	
	public void setLatBid(int bid){
		lastBid = bid;
	}
	
	public void setStatus(String status){
		this.status = status;
	}

	public String getItemName() {
		return this.itemName;
	}
	
	public int getLastPrice(){
		return this.lastPrice;
	}
	
	public int getLastBid(){
		return this.lastBid;
	}
	
	public String getStatus(){
		return this.status;
	}
}

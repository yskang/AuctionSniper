package com.yskang.auctionsniper.test;

import android.util.Log;

import com.jayway.android.robotium.solo.Solo;

import static junit.framework.Assert.assertTrue;



public class AuctionSniperDriver {

	private Solo solo;

	public AuctionSniperDriver(Solo solo) {
		this.solo = solo;
	}

	public void showsSniperStatus(String status) {
		assertTrue("status does not match.", solo.waitForText(status, 1, 5000));
		Log.d("yskang", "Fake server receive message: " + status);
	}

	public void dispose() {

	}

	public void startBiddingFor(String itemId) {
		solo.enterText(com.yskang.auctionsniper.R.id.editText_ItemId, itemId);
		solo.clickOnButton("Join Auction");
	}

	public void showsSniperStatus(String itemId, int lastPrice, int lastBid,
			String status) {
		assertTrue("status does not match.", solo.waitForText(itemId));;
		assertTrue("status does not match.", solo.searchText(Integer.toString(lastPrice)));;
		assertTrue("status does not match.", solo.searchText(Integer.toString(lastBid)));;
		assertTrue("status does not match.", solo.searchText(status));;
	}
	

}

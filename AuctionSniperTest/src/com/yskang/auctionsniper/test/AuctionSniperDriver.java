package com.yskang.auctionsniper.test;

import com.jayway.android.robotium.solo.Solo;

import static junit.framework.Assert.assertTrue;

public class AuctionSniperDriver {

	private Solo solo;

	public AuctionSniperDriver(Solo solo) {
		this.solo = solo;
	}

	public void showsSniperStatus(String status) {
		assertTrue("status does not match.", solo.searchText(status));;
	}

	public void dispose() {

	}

	public void startJoinToServer() {
		solo.clickOnButton("Join");
	}
}

package com.yskang.auctionsniper.test;

import com.jayway.android.robotium.solo.Solo;

public class ApplicationRunner {
	public static final String SNIPER_PASSWORD = "sniper";
	private static final String STATUS_JOINING = "joining";
	private static final String STATUS_LOST = "lost";
	private static final String STATUS_BIDDING = "bidding";
	private static final String STATUS_WINNING = "winning";
	public static final String SNIPER_XMPP_ID = "sniper";
	private static final String STATUS_WON = "won";
	private AuctionSniperDriver driver;

	public ApplicationRunner(Solo solo) {
		driver = new AuctionSniperDriver(solo);
	}

	public void startBiddingIn(final FakeAuctionServer auction) {
		driver.startJoinToServer();
		driver.showsSniperStatus(STATUS_JOINING);
	}

	public void showsSniperHasLostAuction() {
		driver.showsSniperStatus(STATUS_LOST);
	}

	public void stop() {
		if (driver != null) {
			driver.dispose();
		}
	}

	public void hasShownSniperIsBidding() {
		driver.showsSniperStatus(STATUS_BIDDING);
	}

	public void hasShownSniperIsWinning() {
		driver.showsSniperStatus(STATUS_WINNING);
	}

	public void showsSniperHasWonAuction() {
		driver.showsSniperStatus(STATUS_WON);
	}
}

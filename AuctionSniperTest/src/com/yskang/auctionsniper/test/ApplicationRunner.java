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
	private String itemId;

	public ApplicationRunner(Solo solo) {
		driver = new AuctionSniperDriver(solo);
	}

	public void startBiddingIn(final FakeAuctionServer auction) {
		itemId = auction.getItemId();
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

	public void hasShownSniperIsBidding(int lastPrice, int lastBid) {
		driver.showsSniperStatus(itemId, lastPrice, lastBid,
				STATUS_BIDDING);
	}

	public void hasShownSniperIsWinning(int winningBid) {
		driver.showsSniperStatus(itemId, winningBid, winningBid,
				STATUS_WINNING);
	}

	public void showsSniperHasWonAuction(int lastPrice) {
		driver.showsSniperStatus(itemId, lastPrice, lastPrice,
				STATUS_WON);
	}

}

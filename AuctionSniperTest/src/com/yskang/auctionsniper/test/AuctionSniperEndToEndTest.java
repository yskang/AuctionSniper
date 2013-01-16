package com.yskang.auctionsniper.test;

import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;
import com.yskang.auctionsniper.MainActivity;

public class AuctionSniperEndToEndTest extends
		ActivityInstrumentationTestCase2<MainActivity> {
	private final FakeAuctionServer auction = new FakeAuctionServer(
			"item-54321");
	private final FakeAuctionServer auction2 = new FakeAuctionServer(
			"item-65432");
	private ApplicationRunner application;

	public Solo solo;

	public AuctionSniperEndToEndTest() {
		super(MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
		application = new ApplicationRunner(solo);
	}

	public void test_1_SingleJoinLostWithoutBidding() throws Exception {
		
		auction.startSellingItem();
		application.startBiddingIn(auction);
		auction.hasReceivedJoinRequestFrom(ApplicationRunner.SNIPER_XMPP_ID);
		auction.announceClosed();
		application.showsSniperHasLostAuction();
	}

	public void test_2_SingleJoinBidAndLost() throws Exception {
		auction.startSellingItem();
		application.startBiddingIn(auction);
		auction.hasReceivedJoinRequestFrom(ApplicationRunner.SNIPER_XMPP_ID);
		auction.reportPrice(1000, 98, "other bidder");
		application.hasShownSniperIsBidding(auction, 1000, 1098);
		auction.hasReceivedBid(1098, ApplicationRunner.SNIPER_XMPP_ID);
		auction.announceClosed();
		application.showsSniperHasLostAuction();
	}

	public void test_3_SniperWinsAnAuctionByBiddingHigher() throws Exception {
		auction.startSellingItem();
		application.startBiddingIn(auction);
		auction.hasReceivedJoinRequestFrom(ApplicationRunner.SNIPER_XMPP_ID);
		auction.reportPrice(1000, 98, "other bidder");
		application.hasShownSniperIsBidding(auction, 1000, 1098);
		auction.hasReceivedBid(1098, ApplicationRunner.SNIPER_XMPP_ID);
		auction.reportPrice(1098, 97, ApplicationRunner.SNIPER_XMPP_ID);
		application.hasShownSniperIsWinning(auction, 1098);
		auction.announceClosed();
		application.showsSniperHasWonAuction(auction, 1098);
	}

	public void test_4_SniperBidsForMultipleItems() throws Exception {
		auction.startSellingItem();
		auction2.startSellingItem();
		application.startBiddingIn(auction, auction2);
		auction.hasReceivedJoinRequestFrom(ApplicationRunner.SNIPER_XMPP_ID);
		auction2.hasReceivedJoinRequestFrom(ApplicationRunner.SNIPER_XMPP_ID);
		auction.reportPrice(1000, 98, "other bidder");
		auction.hasReceivedBid(1098, ApplicationRunner.SNIPER_XMPP_ID);
		auction2.reportPrice(500, 21, "other bidder");
		auction2.hasReceivedBid(521, ApplicationRunner.SNIPER_XMPP_ID);
		auction.reportPrice(1098, 97, ApplicationRunner.SNIPER_XMPP_ID);
		auction2.reportPrice(521, 22, ApplicationRunner.SNIPER_XMPP_ID);
		application.hasShownSniperIsWinning(auction, 1098);
		application.hasShownSniperIsWinning(auction2, 521);
		auction.announceClosed();
		auction2.announceClosed();
		application.showsSniperHasWonAuction(auction, 1098);
		application.showsSniperHasWonAuction(auction2, 521);
	}

	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}
}
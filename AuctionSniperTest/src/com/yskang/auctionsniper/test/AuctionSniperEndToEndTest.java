package com.yskang.auctionsniper.test;


import com.jayway.android.robotium.solo.Solo;
import com.yskang.auctionsniper.MainActivity;

import android.test.ActivityInstrumentationTestCase2;

public class AuctionSniperEndToEndTest extends ActivityInstrumentationTestCase2<MainActivity> {
	private final FakeAuctionServer auction = new FakeAuctionServer("item-54321");
	private ApplicationRunner application;
	
	public Solo solo;

	public AuctionSniperEndToEndTest(){
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
	    application.hasShownSniperIsBidding(1000, 1098);
	    auction.hasReceivedBid(1098, ApplicationRunner.SNIPER_XMPP_ID);
	    auction.announceClosed();
	    application.showsSniperHasLostAuction();  
	}
	
	public void test_3_SniperWinsAnAuctionByBiddingHigher() throws Exception{
		auction.startSellingItem();
		application.startBiddingIn(auction);
		auction.hasReceivedJoinRequestFrom(ApplicationRunner.SNIPER_XMPP_ID);
		auction.reportPrice(1000, 98, "other bidder");
		application.hasShownSniperIsBidding(1000, 1098);
		auction.hasReceivedBid(1098, ApplicationRunner.SNIPER_XMPP_ID);
		auction.reportPrice(1098, 97, ApplicationRunner.SNIPER_XMPP_ID);
		application.hasShownSniperIsWinning(1098);
		auction.announceClosed();
		application.showsSniperHasWonAuction(1098);
	}
	

	
	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}
}
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

	public void testSingleJoinLostWithoutBidding() throws Exception {
		auction.startSellingItem();
		application.startBiddingIn(auction);
		auction.hasReceivedJoinRequestFromSniper();
		auction.announceClosed();
		application.showsSniperHasLostAuction();
	}

	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}
}
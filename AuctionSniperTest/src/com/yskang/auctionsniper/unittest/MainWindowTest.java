package com.yskang.auctionsniper.unittest;

import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;
import com.yskang.auctionsniper.MainActivity;
import com.yskang.auctionsniper.test.AuctionSniperDriver;

public class MainWindowTest extends
		ActivityInstrumentationTestCase2<MainActivity> {

	private Solo solo;
	private AuctionSniperDriver driver;

	@Override
	protected void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
		driver = new AuctionSniperDriver(solo);
	}

	public MainWindowTest() {
		super(MainActivity.class);
	}

	public void testMakesUserRequestWhenJoinButtonClicked() {
		driver.startBiddingFor("an item-id");
		solo.waitForText("an item-id");
	}

}

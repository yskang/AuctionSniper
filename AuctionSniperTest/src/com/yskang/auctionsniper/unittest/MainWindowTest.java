package com.yskang.auctionsniper.unittest;

import android.test.ActivityInstrumentationTestCase2;

import static org.hamcrest.

import com.jayway.android.robotium.solo.Solo;
import com.yskang.auctionsniper.MainActivity;
import com.yskang.auctionsniper.SnipersTableAdapter;
import com.yskang.auctionsniper.test.AuctionSniperDriver;

public class MainWindowTest extends
		ActivityInstrumentationTestCase2<MainActivity> {

	private Solo solo;
	private final SnipersTableAdapter tableModel = new SnipersTableAdapter(getContext());
	private final MainActivity mainWindow = new MainActivity(tableModel);
	private final AuctionSniperDriver driver = new AuctionSniperDriver(100);

	public MainWindowTest(Class<MainActivity> activityClass) {
		super(MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		this.solo = new Solo(getInstrumentation(), getActivity());
	}

	public void testMakesUserRequestWhenJoinButtonClicked() {
		final ValueMatcherProbe<String> buttonProbe = new ValueMatcherProbe<String>(
				equalTo("an item-id"), "join request");
		mainWindow.addUserRequestListener(new UserRequestListener() {
			public void joinAuction(String itemId) {
				buttonProbe.setReceivedValue(itemId);
			}
		});
		driver.startBiddingFor("an item-id");
		driver.check(buttonProbe);
	}

}

package com.yskang.auctionsniper.unittest;

import org.jmock.Expectations;
import org.jmock.Mockery;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;

import com.jayway.android.robotium.solo.Solo;
import com.yskang.auctionsniper.MainActivity;
import com.yskang.auctionsniper.R;
import com.yskang.auctionsniper.test.AuctionSniperDriver;

public class MainWindowTest extends
		ActivityInstrumentationTestCase2<MainActivity> {

	private final Mockery context = new Mockery();
	private View.OnClickListener mOnClickListenerJoin = context.mock(View.OnClickListener.class);
	private Button view;
	private Solo solo;
	private AuctionSniperDriver driver;

	@Override
	protected void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
		driver = new AuctionSniperDriver(solo);
		
		view = (Button)solo.getView(R.id.button_join);
		view.setOnClickListener(mOnClickListenerJoin);
	}

	public MainWindowTest() {
		super(MainActivity.class);
	}

	public void testMakesUserRequestWhenJoinButtonClicked() {
		
		context.checking(new Expectations() {
			{
				exactly(1).of(mOnClickListenerJoin).onClick(view);
			}
		});
		
		driver.startBiddingFor("an item-id");
		assertEquals(solo.getEditText(0).getText().toString(), "an item-id");
		
		context.assertIsSatisfied();
	}

}

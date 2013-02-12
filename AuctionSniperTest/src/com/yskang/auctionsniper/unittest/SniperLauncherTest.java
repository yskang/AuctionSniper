package com.yskang.auctionsniper.unittest;

import junit.framework.TestCase;

import static org.hamcrest.Matchers.equalTo;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.States;

import com.yskang.auctionsniper.Auction;
import com.yskang.auctionsniper.AuctionHouse;
import com.yskang.auctionsniper.AuctionSniper;
import com.yskang.auctionsniper.SniperCollector;
import com.yskang.auctionsniper.SniperLauncher;

public class SniperLauncherTest extends TestCase {
	private final Mockery context = new Mockery();
	private final States auctionState = context.states("auction state")
			.startsAs("not joined");
	private AuctionHouse auctionHouse = context.mock(AuctionHouse.class);
	private Auction auction = context.mock(Auction.class);
	private SniperCollector sniperCollector = context.mock(SniperCollector.class);
	private SniperLauncher launcher = new SniperLauncher(auctionHouse, sniperCollector);

	public void testAddNewSniperToCollectorAndThenJoinsAuction() {
		final String itemId = "item 123";
		context.checking(new Expectations() {
			{
				allowing(auctionHouse).auctionFor(itemId); will(returnValue(auction));
				oneOf(auction).addAuctionEventListener(with(sniperForItem(itemId)));
				when(auctionState.is("not joined"));
				oneOf(sniperCollector).addSniper(with(sniperForItem(itemId)));
				when(auctionState.is("not joined"));
				one(auction).join(); then(auctionState.is("joined"));
			}
		});

		launcher.joinAuction(itemId);

		context.assertIsSatisfied();
	}
	
	private Matcher<AuctionSniper> sniperForItem(String itemId) {
		return new FeatureMatcher<AuctionSniper, String>(equalTo(itemId), "sniper itemId", "was"){
			@Override
			protected String featureValueOf(AuctionSniper auctionSniper) {
				return auctionSniper.getSnapshot().getItemId();
			}
		};
	}

}

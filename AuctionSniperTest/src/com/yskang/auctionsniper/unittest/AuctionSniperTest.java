package com.yskang.auctionsniper.unittest;


import static org.hamcrest.Matchers.equalTo;
import junit.framework.TestCase;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.States;

import com.yskang.auctionsniper.Auction;
import com.yskang.auctionsniper.AuctionEventListener.PriceSource;
import com.yskang.auctionsniper.AuctionSniper;
import com.yskang.auctionsniper.SniperListener;
import com.yskang.auctionsniper.SniperSnapshot;
import com.yskang.auctionsniper.SniperState;

public class AuctionSniperTest extends TestCase {
	private final Mockery context = new Mockery();

	private final String auctionItem = "item-54321";
	
	private final Auction auction = context.mock(Auction.class);
	private final SniperListener sniperListener = context
			.mock(SniperListener.class);

	private final AuctionSniper sniper = new AuctionSniper(auctionItem, auction);

	private final States sniperState = context.states("sniper");

	public void testReportsLostIfAuctionClosesImmediately() {
		context.checking(new Expectations() {
			{
				atLeast(1).of(sniperListener).sniperStateChanged(new SniperSnapshot(auctionItem, 0, 0, SniperState.LOST));
			}
		});

		sniper.auctionClosed();
		context.assertIsSatisfied();
	}

	public void testReportsLostIfAuctionClosesWhenBidding() {
		context.checking(new Expectations() {
			{
				ignoring(auction);
				allowing(sniperListener).sniperStateChanged(
						with(aSniperThatIs(SniperState.BIDDING)));
				then(sniperState.is("bidding"));
				
				atLeast(1).of(sniperListener).sniperStateChanged(
						new SniperSnapshot(auctionItem, 135, 135, SniperState.WINNING));
						when(sniperState.is("bidding"));
			}
		});

		sniper.currentPrice(123, 12, PriceSource.FromOtherBidder);
		sniper.currentPrice(135, 45, PriceSource.FromSniper);
		context.assertIsSatisfied();
	}

	private Matcher<SniperSnapshot> aSniperThatIs(final SniperState state) {
		return new FeatureMatcher<SniperSnapshot, SniperState>(equalTo(state),
				"sniper that is ", "was") {
			@Override
			protected SniperState featureValueOf(SniperSnapshot actual) {
				return actual.state;
			}
		};
	}

	public void testReportsLostWhenAuctionCloses() {
		context.checking(new Expectations() {
			{
				one(sniperListener).sniperStateChanged(new SniperSnapshot(auctionItem, 0, 0, SniperState.LOST));
			}
		});

		sniper.auctionClosed();
		context.assertIsSatisfied();
	}

	public void testBidsHigherAndReportsBiddingWhenNewPriceArrives() {
		final int price = 1001;
		final int increment = 25;
		final int bid = price + increment;

		context.checking(new Expectations() {
			{
				one(auction).bid(price + increment);
				atLeast(1).of(sniperListener).sniperStateChanged(
						new SniperSnapshot("item-54321", price, bid, SniperState.BIDDING));
			}
		});

		sniper.currentPrice(price, increment, PriceSource.FromOtherBidder);
		context.assertIsSatisfied();
	}

	public void testReportsIsWinningWhenCurrentPriceComesFromSniper() {
		context.checking(new Expectations() {
			{
				atLeast(1).of(sniperListener).sniperStateChanged(new SniperSnapshot(auctionItem, 123, 0, SniperState.WINNING));
			}
		});
		sniper.currentPrice(123, 45, PriceSource.FromSniper);
		context.assertIsSatisfied();
	}

	public void testReportsWonIfAuctionClosesWhenWinning() {
		context.checking(new Expectations() {
			{
				ignoring(auction);
				allowing(sniperListener).sniperStateChanged(new SniperSnapshot(auctionItem, 123, 0, SniperState.WINNING));

				then(sniperState.is("winning"));
				atLeast(1).of(sniperListener).sniperStateChanged(new SniperSnapshot(auctionItem, 123, 0, SniperState.WON));
				when(sniperState.is("winning"));
			}
		});
		sniper.currentPrice(123, 45, PriceSource.FromSniper);
		sniper.auctionClosed();
		context.assertIsSatisfied();
	}
}

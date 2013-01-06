package com.yskang.auctionsniper.unittest;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.States;

import com.yskang.auctionsniper.Auction;
import com.yskang.auctionsniper.AuctionEventListener.PriceSource;
import com.yskang.auctionsniper.AuctionSniper;
import com.yskang.auctionsniper.SniperListener;

import junit.framework.TestCase;

public class AuctionSniperTest extends TestCase {
	private final Mockery context = new Mockery();

	private final Auction auction = context.mock(Auction.class);
	private final SniperListener sniperListener = context
			.mock(SniperListener.class);

	private final AuctionSniper sniper = new AuctionSniper(auction,
			sniperListener);

	private final States sniperState = context.states("sniper");

	public void testReportsLostIfAuctionClosesImmediately() {
		context.checking(new Expectations() {
			{
				atLeast(1).of(sniperListener).sniperLost();
			}
		});

		sniper.auctionClosed();
		context.assertIsSatisfied();
	}

	public void testReportsLostIfAuctionClosesWhenBidding() {
		context.checking(new Expectations() {
			{
				ignoring(auction);

				allowing(sniperListener).sniperBidding();
				then(sniperState.is("bidding"));

				atLeast(1).of(sniperListener).sniperLost();
				when(sniperState.is("bidding"));
			}
		});

		sniper.currentPrice(123, 45, PriceSource.FromOtherBidder);
		sniper.auctionClosed();
		context.assertIsSatisfied();
	}

	public void testReportsLostWhenAuctionCloses() {
		context.checking(new Expectations() {
			{
				one(sniperListener).sniperLost();
			}
		});

		sniper.auctionClosed();
		context.assertIsSatisfied();
	}

	public void testBidsHigherAndReportsBiddingWhenNewPriceArrives() {
		final int price = 1001;
		final int increment = 25;

		context.checking(new Expectations() {
			{
				one(auction).bid(price + increment);
				atLeast(1).of(sniperListener).sniperBidding();
			}
		});

		sniper.currentPrice(price, increment, PriceSource.FromOtherBidder);
		context.assertIsSatisfied();
	}

	public void testReportsIsWinningWhenCurrentPriceComesFromSniper() {
		context.checking(new Expectations() {
			{
				atLeast(1).of(sniperListener).sniperWinning();
			}
		});
		sniper.currentPrice(123, 45, PriceSource.FromSniper);
		context.assertIsSatisfied();
	}

	public void testReportsWonIfAuctionClosesWhenWinning() {
		context.checking(new Expectations() {
			{
				ignoring(auction);
				allowing(sniperListener).sniperWinning();

				then(sniperState.is("winning"));
				atLeast(1).of(sniperListener).sniperWon();
				when(sniperState.is("winning"));
			}
		});
		sniper.currentPrice(123, 45, PriceSource.FromSniper);
		sniper.auctionClosed();
		context.assertIsSatisfied();
	}
}

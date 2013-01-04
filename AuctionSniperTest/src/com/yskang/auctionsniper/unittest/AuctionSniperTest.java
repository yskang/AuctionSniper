package com.yskang.auctionsniper.unittest;

import org.jmock.Expectations;
import org.jmock.Mockery;

import com.yskang.auctionsniper.Auction;
import com.yskang.auctionsniper.AuctionSniper;
import com.yskang.auctionsniper.SniperListener;

import junit.framework.TestCase;

public class AuctionSniperTest extends TestCase {
	private final Mockery context = new Mockery();

	private final Auction auction = context.mock(Auction.class);
	private final SniperListener sniperListener = context.mock(SniperListener.class);

	private final AuctionSniper sniper = new AuctionSniper(auction,
			sniperListener);

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

		sniper.currentPrice(price, increment);
		context.assertIsSatisfied();
	}
}

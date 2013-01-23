package com.yskang.auctionsniper.unittest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;

import android.test.ActivityInstrumentationTestCase2;

import com.yskang.auctionsniper.Auction;
import com.yskang.auctionsniper.AuctionEventListener;
import com.yskang.auctionsniper.MainActivity;
import com.yskang.auctionsniper.XMPPAuction;
import com.yskang.auctionsniper.test.ApplicationRunner;
import com.yskang.auctionsniper.test.FakeAuctionServer;

public class XMPPAuctionTest extends
		ActivityInstrumentationTestCase2<MainActivity> {
	
	private static final String SNIPER_HOSTNAME = "localhost";
	private static final String SNIPER_USERNAME = "sniper";
	private static final String SNIPER_PASSWORD = "sniper";
	private static final int SNIPER_PORT = 5222;
	public static final String AUCTION_RESOURCE = "Auction";
	
	private final FakeAuctionServer server = new FakeAuctionServer(
			"item-54321");
	private XMPPConnection connection;
	
	public XMPPAuctionTest() {
		super(MainActivity.class);
	}

	protected void setUp() throws Exception {
		server.startSellingItem();
		ConnectionConfiguration connConfig = new ConnectionConfiguration(
				SNIPER_HOSTNAME, SNIPER_PORT, SNIPER_HOSTNAME);
		connection = new XMPPConnection(connConfig);
		connection.connect();
		connection.login(SNIPER_USERNAME, SNIPER_PASSWORD,
				AUCTION_RESOURCE);
	}
	
	public void testReceivesEventsFromAuctionServerAfterJoining()
			throws Exception {
		CountDownLatch auctionWasClosed = new CountDownLatch(1);
		Auction auction = new XMPPAuction(connection, "item-54321");
		auction.addAuctionEventListener(auctionClosedListener(auctionWasClosed));
		auction.join();
		server.hasReceivedJoinRequestFrom(ApplicationRunner.SNIPER_XMPP_ID);
		server.announceClosed();
		assertTrue("should have been closed",
				auctionWasClosed.await(2, TimeUnit.SECONDS));
	}

	private AuctionEventListener auctionClosedListener(
			final CountDownLatch auctionWasClosed) {
		return new AuctionEventListener() {
			public void auctionClosed() {
				auctionWasClosed.countDown();
			}

			public void currentPrice(int price, int increment,
					PriceSource priceSource) {
				// not implemented
			}
		};
	}
}

package com.yskang.auctionsniper;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import android.util.Log;

public class XMPPAuctionHouse implements AuctionHouse {
	private Auction auction;
	public XMPPConnection connection;
	private static final int SNIPER_PORT = 5222;
	public static final String AUCTION_RESOURCE = "Auction";

	@Override
	public Auction auctionFor(String itemId) {
		auction = new XMPPAuction(connection, itemId);
		return auction;
	}

	public static XMPPAuctionHouse connect(final String hostName,
			final String userName, final String password) {
		final XMPPAuctionHouse auctionHouse = new XMPPAuctionHouse();

		ConnectionConfiguration connConfig = new ConnectionConfiguration(
				hostName, SNIPER_PORT, hostName);
		auctionHouse.connection = new XMPPConnection(connConfig);

		Thread commThread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					auctionHouse.connection.connect();
					Log.d("yskang", "sinper connect complete : " + auctionHouse.connection);
					auctionHouse.connection.login(userName, password, AUCTION_RESOURCE);
					Log.d("yskang", "sinper login complete");
				} catch (XMPPException e) {
					e.printStackTrace();
				}
			}

		});
		commThread.start();

		return auctionHouse;
	}

	public void disConnect() {

//		Thread disCommThread = new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				connection.disconnect();
//			}
//
//		});
//		disCommThread.start();
	}
	
}

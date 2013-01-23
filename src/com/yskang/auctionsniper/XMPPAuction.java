package com.yskang.auctionsniper;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import android.util.Log;

public class XMPPAuction implements Auction {

	public static final String JOIN_COMMAND_FORMAT = "SOLVersion: 1.1; Command: JOIN;";
	private static final String BID_COMMAND_FORMAT = "SOLVersion: 1.1; Command: BID; Price: %d;";
	public static final String AUCTION_RESOURCE = "Auction";
	public static final String ITEM_ID_AS_LOGIN = "auction-%s";
	public static final String AUCTION_ID_FORMAT = ITEM_ID_AS_LOGIN + "@%s/" + AUCTION_RESOURCE;
	
	private final Announcer<AuctionEventListener> auctionEventListeners = Announcer
			.to(AuctionEventListener.class);
	private final Chat chat;

	public XMPPAuction(XMPPConnection connection, String itemId) {
		chat = connection.getChatManager().createChat(
				auctionId(itemId, connection),
				new AuctionMessageTranslator(connection.getUser(),
						auctionEventListeners.announce()));
	}

	private static String auctionId(String itemId, XMPPConnection connection) {
		return String.format(AUCTION_ID_FORMAT, itemId,
				connection.getServiceName());
	}

	@Override
	public void bid(int amount) {
		sendMessage(String.format(BID_COMMAND_FORMAT, amount));
	}

	@Override
	public void join() {
		sendMessage(JOIN_COMMAND_FORMAT);
	}

	private void sendMessage(final String message) {
		try {
			Log.d("yskang", " Sniper send message: " + message);
			chat.sendMessage(message);
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}

	
	@Override
	public void addAuctionEventListener(AuctionEventListener auctionSniper) {
		auctionEventListeners.addListener(auctionSniper);
	}

}

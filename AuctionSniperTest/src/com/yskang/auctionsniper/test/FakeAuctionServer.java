package com.yskang.auctionsniper.test;

import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import static org.hamcrest.Matchers.equalTo;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import android.util.Log;

import com.yskang.auctionsniper.MainActivity;

public class FakeAuctionServer {
	private static SingleMessageListener messageListener ;

	public static final String ITEM_ID_AS_LOGIN = "auction-%s";
	public static final String AUCTION_RESOURCE = "Auction";
	public static final String XMPP_HOSTNAME = "localhost";
	private static final String AUCTION_PASSWORD = "auction";
	private final String itemId;
	private final XMPPConnection connection;
	private static Chat currentChat;

	public FakeAuctionServer(String itemId) {
		this.itemId = itemId;
		ConnectionConfiguration connConfig = new ConnectionConfiguration(
				"localhost", 5222, "localhost");
		this.connection = new XMPPConnection(connConfig);
		messageListener = new SingleMessageListener(itemId);
	}

	public void startSellingItem() throws XMPPException {
		connection.connect();
		Log.d("yskang", "Fake server connect complete");
		connection.login(String.format(ITEM_ID_AS_LOGIN, itemId),
				AUCTION_PASSWORD, AUCTION_RESOURCE);
		Log.d("yskang", "Fake server login complete");
		connection.getChatManager().addChatListener(new ChatManagerListener() {
			public void chatCreated(Chat chat, boolean createdLocally) {
				currentChat = chat;
				chat.addMessageListener(messageListener);
			}
		});
	}

	public void announceClosed() throws XMPPException {
		Log.d("yskang", "Fake server send : CLOSE");
		currentChat.sendMessage("SOLVersion: 1.1; Event: CLOSE;");
	}

	public void stop() {
		connection.disconnect();
	}

	public void reportPrice(int price, int increment, String bidder)
			throws XMPPException {
		Log.d("yskang", "Fake server send : " + String.format("ItemId: %s, SOLVersion: 1.1; Event: PRICE; "
				+ "CurrentPrice: %d; Increment: %d; Bidder: %s;", itemId, price,
				increment, bidder));
		currentChat.sendMessage(String.format("SOLVersion: 1.1; Event: PRICE; "
				+ "CurrentPrice: %d; Increment: %d; Bidder: %s;", price,
				increment, bidder));
	}

	public void hasReceivedJoinRequestFrom(String sniperId)
			throws InterruptedException {
		receivesAMessageMatching(sniperId, equalTo(MainActivity.JOIN_COMMAND_FORMAT));
	}

	public void hasReceivedBid(int bid, String sniperId)
			throws InterruptedException {
		receivesAMessageMatching(sniperId, equalTo(String.format(MainActivity.BID_COMMAND_FORMAT, bid)));
	}

	private void receivesAMessageMatching(String sniperId, Matcher<? super String> messagematcher) throws InterruptedException {
		messageListener.receivesAMessage(messagematcher);
		assertThat(currentChat.getParticipant(), Matchers.startsWith(sniperId + "@"));
	}

	public String getItemId() {
		return itemId;
	}

}

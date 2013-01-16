package com.yskang.auctionsniper;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPException;

import android.util.Log;

public class XMPPAuction implements Auction {
	
	public static final String JOIN_COMMAND_FORMAT = "SOLVersion: 1.1; Command: JOIN;";
	private static String BID_COMMAND_FORMAT = "SOLVersion: 1.1; Command: BID; Price: %d;";
	private static Chat mChat;
	private static String itemId;
	
	
	public XMPPAuction(String itemId, Chat chat){
		mChat = chat;
		this.itemId = itemId;
		Log.d("yskang", "Create XMPPAuction : " + itemId);
	}
	
	@Override
	public void bid(int amount) {
		sendMessage(String.format(BID_COMMAND_FORMAT, amount));
	}

	@Override
	public void join() {
		sendMessage(JOIN_COMMAND_FORMAT);
	}
	
	private void sendMessage(final String message){
		try {
			Log.d("yskang", itemId +" Sniper send message: " + message);
			mChat.sendMessage(message);
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}

}

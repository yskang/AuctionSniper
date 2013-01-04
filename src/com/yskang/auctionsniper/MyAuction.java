package com.yskang.auctionsniper;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPException;

import android.util.Log;

public class MyAuction implements Auction {
	
	private static String BID_COMMAND_FORMAT = "SOLVersion: 1.1; Command: BID; Price: %d;";
	private Chat mChat;
	
	
	public MyAuction(Chat chat){
		mChat = chat;
	}
	
	@Override
	public void bid(int amount) {
		Log.d("yskang", "I'm bidding... amount is " + amount);
		try {
			mChat.sendMessage(String.format(BID_COMMAND_FORMAT, amount));
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}

}

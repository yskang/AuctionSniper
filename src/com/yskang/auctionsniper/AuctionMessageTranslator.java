package com.yskang.auctionsniper;

import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

import android.content.Context;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

public class AuctionMessageTranslator implements MessageListener {
	private final ArrayBlockingQueue<Message> messages = new ArrayBlockingQueue<Message>(
			1);
	public Handler mHandler;
	public TextView mStatusView;
	public AuctionEventListener auctionEventListener;

	public AuctionMessageTranslator(AuctionEventListener listener) {
		auctionEventListener = listener;
	}

	@Override
	public void processMessage(Chat chat, Message message) {
		HashMap<String, String> event = unpackEventFrom(message);

		String type = event.get("Event");
		if ("CLOSE".equals(type)) {
			auctionEventListener.auctionClosed();
		} else if ("PRICE".equals(type)) {
			auctionEventListener.currentPrice(
					Integer.parseInt(event.get("CurrentPrice")),
					Integer.parseInt(event.get("Increment")));
		}
	}

	private HashMap<String, String> unpackEventFrom(Message message) {
		HashMap<String, String> event = new HashMap<String, String>();
		for (String element : message.getBody().split(";")) {
			String[] pair = element.split(":");
			event.put(pair[0].trim(), pair[1].trim());
		}
		return event;
	}
}

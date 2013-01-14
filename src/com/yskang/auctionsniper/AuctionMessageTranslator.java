package com.yskang.auctionsniper;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

import android.util.Log;

public class AuctionMessageTranslator implements MessageListener {
	private static final String EVENT_TYPE_PRICE = "PRICE";
	private static final String EVENT_TYPE_CLOSE = "CLOSE";
	private final String sniperId;
	public AuctionEventListener auctionEventListener;

	public AuctionMessageTranslator(String sniperId, AuctionEventListener listener) {
		this.auctionEventListener = listener;
		this.sniperId = sniperId;
	}

	@Override
	public void processMessage(Chat chat, Message message) {
		AuctionEvent event = AuctionEvent.from(message.getBody());
		String eventType = event.type();
		
		Log.d("yskang", "Sniper receive message: " + event);
		
		if (EVENT_TYPE_CLOSE.equals(eventType)) {
			auctionEventListener.auctionClosed();
		} else if (EVENT_TYPE_PRICE.equals(eventType)) {
			auctionEventListener.currentPrice(event.currentPrice(), event.increment(), event.isFrom(sniperId));
		}
	}
}

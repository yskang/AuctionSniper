package com.yskang.auctionsniper;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

public class AuctionMessageTranslator implements MessageListener {
	public AuctionEventListener auctionEventListener;

	public AuctionMessageTranslator(AuctionEventListener listener) {
		auctionEventListener = listener;
	}

	@Override
	public void processMessage(Chat chat, Message message) {
		AuctionEvent event = AuctionEvent.from(message.getBody());
		String eventType = event.type();
		
		if ("CLOSE".equals(eventType)) {
			auctionEventListener.auctionClosed();
		} else if ("PRICE".equals(eventType)) {
			auctionEventListener.currentPrice(event.currentPrice(), event.increment());
		}
	}
}

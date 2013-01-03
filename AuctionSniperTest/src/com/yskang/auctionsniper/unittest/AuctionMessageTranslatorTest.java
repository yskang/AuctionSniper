package com.yskang.auctionsniper.unittest;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;
import org.jmock.Expectations;
import org.jmock.Mockery;

import com.yskang.auctionsniper.AuctionEventListener;
import com.yskang.auctionsniper.AuctionMessageTranslator;

import junit.framework.TestCase;

public class AuctionMessageTranslatorTest extends TestCase {
	public static final Chat UNUSED_CHAT = null;
	private Mockery context = new Mockery();
	private final AuctionEventListener listener = context
			.mock(AuctionEventListener.class);
	private final AuctionMessageTranslator translator = new AuctionMessageTranslator(
			listener);

	public void testNotifiesAuctionClosedWhenCloseMessageReceived() {

		context.checking(new Expectations() {
			{
				oneOf(listener).auctionClosed();
			}
		});

		Message message = new Message();
		message.setBody("SOLVersion: 1.1; Event: CLOSE;");
		translator.processMessage(UNUSED_CHAT, message);

		context.assertIsSatisfied();
	}

	public void testNotifiesBidDetailsWhenCurrentPriceMessageReceived() {
		context.checking(new Expectations() {
			{
				exactly(1).of(listener).currentPrice(192, 7);
			}
		});

		Message message = new Message();
		message.setBody("SOLVersion: 1.1; Event: PRICE; CurrentPrice: 192; Increment: 7; Bidder: Someone else;");
		translator.processMessage(UNUSED_CHAT, message);
	}
}

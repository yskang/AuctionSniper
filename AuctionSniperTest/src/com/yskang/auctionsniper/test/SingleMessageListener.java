package com.yskang.auctionsniper.test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.hamcrest.Matcher;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.is;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

import android.util.Log;

public class SingleMessageListener implements MessageListener {
	private final ArrayBlockingQueue<Message> messages = new ArrayBlockingQueue<Message>(
			1);
	private static String itemId;

	public SingleMessageListener(String itemId){
		this.itemId = itemId;
	}
	
	public void processMessage(Chat chat, Message message) {
		Log.d("yskang", itemId + ", Fake server receive msg: " + message.getBody());
		messages.add(message);
	}

	public void receivesAMessage(Matcher<? super String> messageMatcher)
			throws InterruptedException {
		final Message message = messages.poll(5, TimeUnit.SECONDS);
		assertThat(message, is(notNullValue()));
		assertThat(message.getBody(), messageMatcher);
	}
}
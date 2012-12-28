package com.yskang.auctionsniper.test;

import static junit.framework.Assert.*;

import java.util.concurrent.TimeUnit;

import java.util.concurrent.ArrayBlockingQueue;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;


public class SingleMessageListener implements MessageListener {
	private final ArrayBlockingQueue<Message> messages = new ArrayBlockingQueue<Message>(1);

	public void processMessage(Chat chat, Message message) {
		messages.add(message);
	}

	public void receivesAMessage() throws InterruptedException {
		assertNotNull("Message is not null", messages.poll(5, TimeUnit.SECONDS));
	}
}
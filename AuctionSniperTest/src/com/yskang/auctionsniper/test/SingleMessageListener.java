package com.yskang.auctionsniper.test;

import static junit.framework.Assert.*;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import java.util.concurrent.ArrayBlockingQueue;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

import android.util.Log;


public class SingleMessageListener implements MessageListener {
	private final ArrayBlockingQueue<Message> messages = new ArrayBlockingQueue<Message>(1);

	public void processMessage(Chat chat, Message message) {
		messages.add(message);
	}

	public void receivesAMessage(String messageString)  
			throws InterruptedException {
		Message message;
		message = messages.poll(5, TimeUnit.SECONDS);
		assertNotNull("Message is Null", message);
		assertEquals(message.getBody(), messageString);
	}
}
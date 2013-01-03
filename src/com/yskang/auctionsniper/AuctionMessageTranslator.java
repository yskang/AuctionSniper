package com.yskang.auctionsniper;

import java.util.concurrent.ArrayBlockingQueue;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

import android.content.Context;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

public class AuctionMessageTranslator implements MessageListener {
	private final ArrayBlockingQueue<Message> messages = new ArrayBlockingQueue<Message>(1);
	public Handler mHandler;
	public TextView mStatusView;
	public AuctionEventListener auctionEventListener;
	
	public AuctionMessageTranslator(AuctionEventListener listener){
		auctionEventListener = listener;
	}
	
	@Override
	public void processMessage(Chat chat, Message message) {
			auctionEventListener.auctionClosed();
	}

	public void receivesAMessage() throws InterruptedException {
		//Let's display the received message.
	}
}

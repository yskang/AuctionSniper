package com.yskang.auctionsniper;

import java.util.concurrent.ArrayBlockingQueue;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

public class SingleMessageListener implements MessageListener {
	private final ArrayBlockingQueue<Message> messages = new ArrayBlockingQueue<Message>(1);
	private TextView statusView;
	public Handler mHandler;
	public TextView mStatusView;
	
	public SingleMessageListener(Handler handler, TextView statusText){
		mHandler = handler;
		mStatusView = statusText;
	}
	
	@Override
	public void processMessage(Chat chat, Message message) {
		if(message.getBody().equals("closed")){
			//statusView.setText(R.string.status_lost);
			mHandler.post(new Runnable(){
				public void run(){
					mStatusView.setText(R.string.status_lost);
				}
			});
			
		}
	}

	public void receivesAMessage() throws InterruptedException {
		//Let's display the received message.
	}
}

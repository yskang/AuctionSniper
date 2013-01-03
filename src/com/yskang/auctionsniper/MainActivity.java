package com.yskang.auctionsniper;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements AuctionEventListener {

	private static final String ITEM_ID_AS_LOGIN = "sniper";
	private String itemId;
	private static final String SNIPER_PASSWORD = "sniper";
	public static final String c = "sniper";
	public static final String JOIN_COMMAND_FORMAT = "SOLVersion: 1.1; Command: JOIN;";
	public static final String BID_COMMAND_FORMAT = "SOLVersion: 1.1; Command: BID; Price: %d;";
	private Chat currentChat;

	public Button buttonJoin;
	public TextView statusView;

	private AuctionMessageTranslator messageListener;

	private XMPPConnection connection;

	public Thread commThread = new Thread(new Comm());

	public Handler handler = new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		buttonJoin = (Button) findViewById(R.id.button_join);
		buttonJoin.setOnClickListener(mOnClickListenerJoin);

		statusView = (TextView) findViewById(R.id.textViewStatusCurrent);
		statusView.setText(R.string.status_init);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	View.OnClickListener mOnClickListenerJoin = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			statusView.setText(R.string.status_joining);
			commThread.start();
		}
	};

	class Comm implements Runnable {
		public void run() {
			try {
				joinToServer();
			} catch (XMPPException e) {
				e.printStackTrace();
			}
		}
	}

	public void joinToServer() throws XMPPException {

		ConnectionConfiguration connConfig = new ConnectionConfiguration(
				"localhost", 5222, "localhost");
		connection = new XMPPConnection(connConfig);

		connection.connect();
		connection.login(ITEM_ID_AS_LOGIN, SNIPER_PASSWORD, SNIPER_PASSWORD);
		
		currentChat = connection.getChatManager().createChat(
				"auction-item-54321@localhost", new AuctionMessageTranslator(this));
		currentChat.sendMessage(JOIN_COMMAND_FORMAT);
	}

	@Override
	public void auctionClosed() {
		Log.d("yskang", "closed");
		handler.post(new Runnable(){
			public void run(){
				statusView.setText(R.string.status_lost);
			}
		});	
	};
}

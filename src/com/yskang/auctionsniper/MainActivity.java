package com.yskang.auctionsniper;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final String SNIPER_HOSTNAME = "localhost";
	private static final String SNIPER_USERNAME = "sniper";
	private static final String SNIPER_PASSWORD = "sniper";
	private static final int SNIPER_PORT = 5222;
	private static final String SNIPER_ITEM_ID = "item-54321";

	private XMPPConnection mConnection;

	public static final String AUCTION_RESOURCE = "Auction";
	public static final String ITEM_ID_AS_LOGIN = "auction-%s";
	public static final String AUCTION_ID_FORMAT = ITEM_ID_AS_LOGIN + "@%s/"
			+ AUCTION_RESOURCE;

	public static final String JOIN_COMMAND_FORMAT = "SOLVersion: 1.1; Command: JOIN;";
	public static final String BID_COMMAND_FORMAT = "SOLVersion: 1.1; Command: BID; Price: %d;";

	public Button buttonJoin;
	public TextView statusView;

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

				ConnectionConfiguration connConfig = new ConnectionConfiguration(
						SNIPER_HOSTNAME, SNIPER_PORT, SNIPER_HOSTNAME);
				connection = new XMPPConnection(connConfig);

				connection.connect();
				connection.login(SNIPER_USERNAME, SNIPER_PASSWORD,
						AUCTION_RESOURCE);

				joinAuction(connection, SNIPER_ITEM_ID);
			} catch (XMPPException e) {
				e.printStackTrace();
			}
		}
	}

	class CommDis implements Runnable {
		private XMPPConnection mConnection;

		public CommDis(XMPPConnection connection) {
			mConnection = connection;
		}

		public void run() {
			mConnection.disconnect();
		}
	}

	public void joinAuction(XMPPConnection connection, String itemId)
			throws XMPPException {

		final Chat chat = connection.getChatManager().createChat(
				auctionId(itemId, connection), null);

		this.mConnection = connection;

		Auction auction = new XMPPAuction(chat);

		chat.addMessageListener(new AuctionMessageTranslator(connection
				.getUser(), new AuctionSniper(auction,
				new SniperStateDisplayer(this))));
		auction.join();
	}

	private static String auctionId(String itemId, XMPPConnection connection) {
		return String.format(AUCTION_ID_FORMAT, itemId,
				connection.getServiceName());
	}

	@Override
	protected void onStop() {
		Thread commDisThread = new Thread(new CommDis(this.mConnection));
		commDisThread.start();
		super.onStop();
	}
}

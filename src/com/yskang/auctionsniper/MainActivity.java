package com.yskang.auctionsniper;

import java.util.ArrayList;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final String SNIPER_HOSTNAME = "localhost";
	private static final String SNIPER_USERNAME = "sniper";
	private static final String SNIPER_PASSWORD = "sniper";
	private static final int SNIPER_PORT = 5222;

	public static final String AUCTION_RESOURCE = "Auction";
	public static final String ITEM_ID_AS_LOGIN = "auction-%s";
	public static final String AUCTION_ID_FORMAT = ITEM_ID_AS_LOGIN + "@%s/"
			+ AUCTION_RESOURCE;

	public static final String JOIN_COMMAND_FORMAT = "SOLVersion: 1.1; Command: JOIN;";
	public static final String BID_COMMAND_FORMAT = "SOLVersion: 1.1; Command: BID; Price: %d;";

	private EditText editTextItemId;

	public Button buttonJoin;
	private XMPPConnection connection;
	public Thread commThread = new Thread();
	public Handler handler = new Handler();
	public SnipersTableAdapter snipers;
	private ArrayList<Chat> mChat = new ArrayList<Chat>();;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		buttonJoin = (Button) findViewById(R.id.button_join);
		buttonJoin.setOnClickListener(mOnClickListenerJoin);

		snipers = new SnipersTableAdapter(this);

		ListView list = (ListView) findViewById(R.id.AuctionListView);
		list.setAdapter(snipers);

		editTextItemId = (EditText) findViewById(R.id.editText_ItemId);
	}

	@Override
	protected void onPostResume() {
		super.onResume();
		connectToServer();
	}

	public void connectToServer() {
		commThread = new Thread(new Comm());
		commThread.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	View.OnClickListener mOnClickListenerJoin = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			try {
				if (editTextItemId.getText().toString().compareTo("") != 0) {
					joinAuction(connection, editTextItemId.getText().toString());
					editTextItemId.setText("");
				} else {
					Toast.makeText(getBaseContext(),
							R.string.warning_for_null_input, Toast.LENGTH_SHORT)
							.show();
				}
			} catch (XMPPException e) {
				e.printStackTrace();
			}
		}
	};

	class Comm implements Runnable {
		public void run() {
			try {
				ConnectionConfiguration connConfig = new ConnectionConfiguration(
						SNIPER_HOSTNAME, SNIPER_PORT, SNIPER_HOSTNAME);
				connection = new XMPPConnection(connConfig);
				connection.connect();
				Log.d("yskang", "sinper connect complete : " + connection);
				connection.login(SNIPER_USERNAME, SNIPER_PASSWORD,
						AUCTION_RESOURCE);
				Log.d("yskang", "sinper login complete");
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
			if (mConnection != null) {
				mConnection.disconnect();
				Log.d("yskang", "sniper disconnect : " + mConnection);
			}
		}
	}

	public void joinAuction(XMPPConnection connection, String itemId)
			throws XMPPException {

		safelyAddItemToModel(itemId);
		Chat chat = connection.getChatManager().createChat(
				auctionId(itemId, connection), null);

		mChat.add(chat);

		Auction auction = new XMPPAuction(chat);
		chat.addMessageListener(new AuctionMessageTranslator(itemId, connection
				.getUser(), new AuctionSniper(itemId, auction,
				new UIThreadSniperListener(this, snipers))));

		auction.join();
	}

	private void safelyAddItemToModel(final String itemId) {
		this.runOnUiThread(new Runnable() {
			public void run() {
				snipers.addSniper(SniperSnapshot.joining(itemId));
			}
		});
	}

	private static String auctionId(String itemId, XMPPConnection connection) {
		return String.format(AUCTION_ID_FORMAT, itemId,
				connection.getServiceName());
	}

	public void sniperStateChanged(SniperSnapshot snapshot) {
		snipers.sniperStateChanged(snapshot);
	}

	@Override
	protected void onPause() {
		Thread commDisThread = new Thread(new CommDis(connection));
		commDisThread.start();
		super.onPause();
	}
}

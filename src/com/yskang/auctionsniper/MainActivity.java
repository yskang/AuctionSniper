package com.yskang.auctionsniper;

import java.util.ArrayList;

import android.app.Activity;
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

	public static final String AUCTION_RESOURCE = "Auction";
	public static final String ITEM_ID_AS_LOGIN = "auction-%s";
	public static final String AUCTION_ID_FORMAT = ITEM_ID_AS_LOGIN + "@%s/"
			+ AUCTION_RESOURCE;

	public static final String JOIN_COMMAND_FORMAT = "SOLVersion: 1.1; Command: JOIN;";
	public static final String BID_COMMAND_FORMAT = "SOLVersion: 1.1; Command: BID; Price: %d;";

	private EditText editTextItemId;

	public Button buttonJoin;
	public Thread commThread = new Thread();
	public Handler handler = new Handler();
	public SnipersTableAdapter snipers;
	private ArrayList<Auction> auctions = new ArrayList<Auction>();
	XMPPAuctionHouse auctionHouse;
	
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
		auctionHouse = XMPPAuctionHouse.connect(
						SNIPER_HOSTNAME, SNIPER_USERNAME, SNIPER_PASSWORD);
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
					joinAuction(editTextItemId.getText().toString());
					editTextItemId.setText("");
				} else {
					Toast.makeText(getBaseContext(),
							R.string.warning_for_null_input, Toast.LENGTH_SHORT)
							.show();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};


	public void joinAuction(String itemId)
			throws Exception {
		snipers.addSniper(SniperSnapshot.joining(itemId));
		Auction auction = auctionHouse.auctionFor(itemId);
		this.auctions.add(auction);
		auction.addAuctionEventListener(new AuctionSniper(itemId, auction, new UIThreadSniperListener(this, snipers)));
		auction.join();
	}

	public void sniperStateChanged(SniperSnapshot snapshot) {
		snipers.sniperStateChanged(snapshot);
	}

	@Override
	protected void onPause() {
		auctionHouse.disConnect();
		super.onPause();
	}
}

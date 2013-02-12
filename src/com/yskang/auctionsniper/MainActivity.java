package com.yskang.auctionsniper;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
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
	private final Announcer<UserRequestListener> userRequests = Announcer.to(UserRequestListener.class);
	
	XMPPAuctionHouse auctionHouse;
	private final SniperPortfolio portfolio = new SniperPortfolio();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		buttonJoin = (Button) findViewById(R.id.button_join);
		buttonJoin.setOnClickListener(mOnClickListenerJoin);

		SnipersTableAdapter adapter = new SnipersTableAdapter(this);
		portfolio.addPortfolioListener(adapter);
		
		ListView list = (ListView) findViewById(R.id.AuctionListView);
		list.setAdapter(adapter);

		editTextItemId = (EditText) findViewById(R.id.editText_ItemId);
	}

	@Override
	protected void onPostResume() {
		super.onResume();
		connectToServer();
		addUserRequestListenerFor(auctionHouse);
	}
	
	public void addUserRequestListener(UserRequestListener userRequestListener) {
		userRequests.addListener(userRequestListener);
	}
	
	private void addUserRequestListenerFor(final XMPPAuctionHouse auctionHouse) {
		this.addUserRequestListener(new SniperLauncher(auctionHouse, portfolio));
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
					userRequests.announce().joinAuction(editTextItemId.getText().toString());
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

	@Override
	protected void onPause() {
		auctionHouse.disConnect();
		super.onPause();
	}
}

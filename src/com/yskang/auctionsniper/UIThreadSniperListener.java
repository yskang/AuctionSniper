package com.yskang.auctionsniper;

import android.app.Activity;

public class UIThreadSniperListener{

	private SnipersTableAdapter sniperTableAdapter;
	private Activity activity;
	
	public UIThreadSniperListener(SnipersTableAdapter sniperTableAdapter, Activity activity) {
		this.sniperTableAdapter = sniperTableAdapter;
		this.activity = activity;
	}
	
	public void sniperStateChanged(final SniperSnapshot snapshot) {
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				sniperTableAdapter.sniperStateChanged(snapshot);
			}
		});
	}
}

package com.yskang.auctionsniper;

import android.app.Activity;
import android.util.Log;

public class UIThreadSniperListener implements SniperListener {

	private Activity mActivity;
	private SnipersTableAdapter sniperTableAdapter;
	
	public UIThreadSniperListener(Activity activity, SnipersTableAdapter sniperTableAdapter) {
		this.mActivity = activity;
		this.sniperTableAdapter = sniperTableAdapter;
	}

	@Override
	public void sniperLost(final SniperSnapshot snapshot) {
		sniperStateChanged(snapshot);
	}

	@Override
	public void sniperBidding(final SniperSnapshot snapshot) {
		sniperStateChanged(snapshot);
	};	
	
	@Override
	public void sniperWinning(final SniperSnapshot snapshot) {
		sniperStateChanged(snapshot);
	};	
	
	@Override
	public void sniperWon(final SniperSnapshot snapshot) {
		sniperStateChanged(snapshot);
	}

	@Override
	public void sniperJoining(SniperSnapshot snapshot) {
		sniperStateChanged(snapshot);
	}

	@Override
	public void sniperStateChanged(final SniperSnapshot snapshot) {
		Log.d("yskang", String.format("sniperStateChanged %s, %d, %d, %s", snapshot.itemId, snapshot.lastPrice, snapshot.lastBid, snapshot.state));
		mActivity.runOnUiThread(new Runnable() {
			public void run() {
				sniperTableAdapter.sniperStateChanged(snapshot);
			}
		});
	}

}

package com.yskang.auctionsniper;

import android.app.Activity;
import android.widget.TextView;

public class SniperStateDisplayer implements SniperListener {

	private Activity mActivity;
	private SnipersTableAdapter sniperTableAdapter;
	
	public SniperStateDisplayer(SnipersTableAdapter sniperTableAdapter) {
		this.sniperTableAdapter = sniperTableAdapter;
	}

	@Override
	public void sniperLost() {
		showStatus(R.string.status_lost);
	}

	@Override
	public void sniperBidding(final SniperState state) {
		sniperTableAdapter.sniperStatusChanged(state, R.string.status_bidding);
	};	
	
	@Override
	public void sniperWinning() {
		showStatus(R.string.status_winning);
	};	
	
	@Override
	public void sniperWon() {
		showStatus(R.string.status_won);
	}

	public void showStatus(final int statusID){
		mActivity.runOnUiThread(new Runnable() {
			public void run() {

			}
		});
	}
}

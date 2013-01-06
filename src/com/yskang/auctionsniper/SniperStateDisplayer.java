package com.yskang.auctionsniper;

import android.app.Activity;
import android.widget.TextView;

public class SniperStateDisplayer implements SniperListener {

	private Activity mActivity;
	private TextView statusView;
	
	public SniperStateDisplayer(Activity activity) {
		this.mActivity = activity;
		this.statusView = (TextView) activity.findViewById(R.id.textViewStatusCurrent);
	}

	@Override
	public void sniperLost() {
		showStatus(R.string.status_lost);
	}

	@Override
	public void sniperBidding() {
		showStatus(R.string.status_bidding);
	};	
	
	@Override
	public void sniperWinning() {
		showStatus(R.string.status_winning);
	};	
	
	public void showStatus(final int statusID){
		mActivity.runOnUiThread(new Runnable() {
			public void run() {
				statusView.setText(statusID);
			}
		});
	}
	
}

package com.yskang.auctionsniper.unittest;

import android.database.DataSetObserver;
import android.util.Log;

public class AuctionSnipersDataSetObserver extends DataSetObserver implements
		AuctionSnipersObserver {
	private final static String LOGTAG = "AuctionSniperLog";

	@Override
	public void onChanged() {
		dataChanged();
		super.onChanged();
	}

	@Override
	public void dataChanged() {
		Log.d(LOGTAG, "data has been changed.");
	}

}

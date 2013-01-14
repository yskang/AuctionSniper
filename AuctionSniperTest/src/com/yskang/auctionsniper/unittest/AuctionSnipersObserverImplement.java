package com.yskang.auctionsniper.unittest;

import android.util.Log;

public class AuctionSnipersObserverImplement implements AuctionSnipersObserver {

	@Override
	public void dataChanged() {
		Log.d("yskang", "data has been changed.");
	}

}

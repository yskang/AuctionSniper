package com.yskang.auctionsniper.unittest;

import android.database.DataSetObserver;
import android.util.Log;

public class AuctionSnipersDataSetObserver extends DataSetObserver{
	private AuctionSnipersObserver observer;

	public AuctionSnipersDataSetObserver(AuctionSnipersObserver observer) {
		this.observer = observer;
	}

	@Override
	public void onChanged() {
		observer.dataChanged();
		super.onChanged();
	}
}

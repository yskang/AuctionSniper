package com.yskang.auctionsniper.unittest;

import java.util.ArrayList;

import junit.framework.TestCase;

import com.yskang.auctionsniper.R;
import com.yskang.auctionsniper.SniperState;
import com.yskang.auctionsniper.SnipersTableAdapter;


public class SnipersTableAdapterTest extends TestCase {

	private ArrayList<SniperState> sniperStatelist = new ArrayList<SniperState>();
	private SnipersTableAdapter snipersTableAdpater = new SnipersTableAdapter(null, R.string.status_init,
			sniperStatelist);
	
	public void testSetsSniperValuesInColumns() {
		snipersTableAdpater.sniperStatusChanged(new SniperState("item id", 555, 666),
				R.string.status_bidding);

		assertEquals(snipersTableAdpater.getItem(0).getItemId(), "item id");
		assertEquals(snipersTableAdpater.getItem(0).getLastPrice(), 555);
		assertEquals(snipersTableAdpater.getItem(0).getLastBid(), 666);
		assertEquals(snipersTableAdpater.getItem(0).getStatus(), R.string.status_bidding);
	}
}

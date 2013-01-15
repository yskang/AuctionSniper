package com.yskang.auctionsniper.unittest;

import org.jmock.Expectations;
import org.jmock.Mockery;

import android.test.AndroidTestCase;

import com.yskang.auctionsniper.R;
import com.yskang.auctionsniper.SniperSnapshot;
import com.yskang.auctionsniper.SniperState;
import com.yskang.auctionsniper.SnipersTableAdapter;

public class SnipersTableAdapterTest extends AndroidTestCase {
	private final Mockery context = new Mockery();
	private AuctionSnipersObserver observer = context
			.mock(AuctionSnipersObserver.class);
	private AuctionSnipersDataSetObserver auctionSnipersObserver = new AuctionSnipersDataSetObserver(
			observer);
	private SnipersTableAdapter snipersTableAdapter;

	public void testSetsSniperValuesInColumns() {

		context.checking(new Expectations() {
			{
				atLeast(1).of(observer).dataChanged();
			}
		});

		snipersTableAdapter = new SnipersTableAdapter(getContext());
		snipersTableAdapter.registerDataSetObserver(auctionSnipersObserver);

		snipersTableAdapter.sniperStateChanged(new SniperSnapshot("item id",
				555, 666, SniperState.BIDDING));

		assertEquals("item id", snipersTableAdapter.getItem(0).getItemId());
		assertEquals(555, snipersTableAdapter.getItem(0).getLastPrice());
		assertEquals(666, snipersTableAdapter.getItem(0).getLastBid());
		assertEquals(SniperState.BIDDING, snipersTableAdapter.getItem(0)
				.getStatus());

		context.assertIsSatisfied();
	}

	public void testNotifiesListenersWhenAddingASniper() {
		SniperSnapshot joining = SniperSnapshot.joining("item123");
		context.checking(new Expectations() {
			{
				one(observer).dataChanged();
			}
		});
		
		assertEquals(0, snipersTableAdapter.getRowCount());
		snipersTableAdapter.addSniper(joining);
		assertEquals(1, snipersTableAdapter.getRowCount());
		assertEquals(snipersTableAdapter.getItem(0), joining);
	}
}

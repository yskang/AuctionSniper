package com.yskang.auctionsniper.unittest;

import org.jmock.Expectations;
import org.jmock.Mockery;

import android.test.AndroidTestCase;

import com.yskang.auctionsniper.Column;
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

		SniperSnapshot joining = SniperSnapshot.joining("item id");
		SniperSnapshot bidding = SniperSnapshot.bidding(555, 666);

		snipersTableAdapter.addSniper(joining);
		snipersTableAdapter.sniperStateChanged(bidding);

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

		snipersTableAdapter = new SnipersTableAdapter(getContext());
		snipersTableAdapter.registerDataSetObserver(auctionSnipersObserver);

		assertEquals(0, snipersTableAdapter.getRowCount());
		snipersTableAdapter.addSniper(joining);
		assertEquals(1, snipersTableAdapter.getRowCount());
		assertEquals(snipersTableAdapter.getItem(0), joining);

		context.assertIsSatisfied();
	}

	public void testHoldsSnipersInAdditionOrder() {
		context.checking(new Expectations() {
			{
				ignoring(observer);
			}
		});
		
		snipersTableAdapter = new SnipersTableAdapter(getContext());
		
		snipersTableAdapter.addSniper(SniperSnapshot.joining("item 0"));
		snipersTableAdapter.addSniper(SniperSnapshot.joining("item 1"));
		
		assertEquals("item 0", snipersTableAdapter.getItem(0).getItemId());
		assertEquals("item 1", snipersTableAdapter.getItem(1).getItemId());
		
		context.assertIsSatisfied();
	}
}

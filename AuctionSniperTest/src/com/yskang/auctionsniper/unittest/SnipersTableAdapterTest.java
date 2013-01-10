package com.yskang.auctionsniper.unittest;

import java.util.ArrayList;

import org.jmock.Expectations;
import org.jmock.Mockery;

import android.content.Context;

import com.yskang.auctionsniper.R;
import com.yskang.auctionsniper.SniperState;
import com.yskang.auctionsniper.SnipersTableAdapter;
import com.yskang.auctionsniper.Column;

import junit.framework.TestCase;

public class SnipersTableAdapterTest extends TestCase {
	private Context mContext = null;
	private ArrayList<SniperState> sniperStateList = new ArrayList<SniperState>();
	
	private final Mockery context = new Mockery();
	private SnipersTableAdapter listener = context
			.mock(SnipersTableAdapter.class);
	private final SnipersTableAdapter model = new SnipersTableAdapter(mContext, R.id.AuctionListView,
			sniperStateList);

	public void TestHasEnoughColumns() {
		assertEquals(model.getColumnCount(), Column.values().length);
	}

	public void TestSetsSniperValuesInColumns() {
		context.checking(new Expectations() {
			{
				one(listener).sniperStatusChanged(newSniperState, newStatusTextId);
			}
		});

		model.sniperStatusChanged(new SniperState("item id", 555, 666, R.string.status_init),
				R.string.status_bidding);

		assertEquals(model.getItem(model.getCount()-1).getItemId(), "item id");
		assertEquals(model.getItem(model.getCount()-1).getLastPrice(), 555);
		assertEquals(model.getItem(model.getCount()-1).getLastBid(), 666);
		assertEquals(model.getItem(model.getCount()-1).getStatus(), R.string.status_bidding);
	}
}

package com.yskang.auctionsniper;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SnipersTableAdapter extends ArrayAdapter<SniperSnapshot> {
	private final static int[] STATUS_TEXT_ID = { R.string.status_joining,
			R.string.status_bidding, R.string.status_winning,
			R.string.status_lost, R.string.status_won };

	private final static SniperSnapshot STARTING_UP = new SniperSnapshot("", 0,
			0, SniperState.WON);
	private SniperSnapshot snapshot = STARTING_UP;
	private static ArrayList<SniperSnapshot> snapshotsList = new ArrayList<SniperSnapshot>();
	private Context context;

	public SnipersTableAdapter(Context context) {
		super(context, R.id.AuctionListView, snapshotsList);
		this.context = context;
		if(this.isEmpty()){
			this.add(STARTING_UP);
		}else{
			SnipersTableAdapter.snapshotsList.set(0, STARTING_UP);
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;

		if (view == null) {
			LayoutInflater vi = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = vi.inflate(R.layout.auction_list_item, null);
		}

		SniperSnapshot item = snapshotsList.get(position);
		if (item != null) {
			TextView itemNameView = (TextView) view.findViewById(R.id.itemName);
			TextView lastPriceView = (TextView) view
					.findViewById(R.id.lastPrice);
			TextView lastBidView = (TextView) view.findViewById(R.id.lastBid);
			TextView statusView = (TextView) view.findViewById(R.id.itemStatus);

			if (itemNameView != null) {
				itemNameView.setText(item.getItemId());
			}
			if (lastPriceView != null) {
				lastPriceView.setText(Integer.toString(item.getLastPrice()));
			}
			if (lastBidView != null) {
				lastBidView.setText(Integer.toString(item.getLastBid()));
			}
			if (statusView != null) {
				statusView.setText(textFor(item.getStatus()));
			}
		}
		return view;
	}

	public int getColumnCount() {
		return snapshotsList.size();
	}

	public int getRowCount() {
		return 1;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return Column.at(columnIndex).valueIn(snapshot);
	}

	public void sniperStateChanged(SniperSnapshot newSnapshot) {
		Log.d("yskang", String.format("Sniper State changed: (ID: %s, State: %s)", newSnapshot.getItemId(), newSnapshot.getStatus()));
		snapshotsList.set(0, newSnapshot);
		this.snapshot = newSnapshot;
		notifyDataSetChanged();
	}

	public static int textFor(SniperState state) {
		int stateStringId = 0;
		switch (state) {
		case JOINING:
			stateStringId = STATUS_TEXT_ID[0];
			break;
		case BIDDING:
			stateStringId = STATUS_TEXT_ID[1];
			break;
		case WINNING:
			stateStringId = STATUS_TEXT_ID[2];
			break;
		case LOST:
			stateStringId = STATUS_TEXT_ID[3];
			break;
		case WON:
			stateStringId = STATUS_TEXT_ID[4];
			break;
		default:
			Log.d("yskang", "return state is not proper");
			break;
		}
		return stateStringId;
	}

	public void addSniper(SniperSnapshot snapshot) {
		this.add(snapshot);
	}
}

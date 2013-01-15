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
	private ArrayList<SniperSnapshot> snapshotsList = new ArrayList<SniperSnapshot>();
	private Context context;

	public SnipersTableAdapter(Context context) {
		super(context, R.id.AuctionListView);
		this.context = context;
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
		return 4;
	}

	public int getRowCount() {
		return snapshotsList.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return Column.at(columnIndex).valueIn(snapshot);
	}

	public void sniperStateChanged(SniperSnapshot newSnapshot) {
		Log.d("yskang", String.format("Sniper State changed: (ID: %s, State: %s)", newSnapshot.getItemId(), newSnapshot.getStatus()));
		int i;
		String itemId = newSnapshot.getItemId();
		for(i = 0 ; i < snapshotsList.size() ; i++){
			if(snapshotsList.get(i).getItemId().compareTo(itemId) == 0)
			{
				snapshotsList.set(i, newSnapshot);
				notifyDataSetChanged();
				return;
			}
		}
		throw new Defect("Cannot find match for " + newSnapshot);
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
		snapshotsList.add(snapshot);
		notifyDataSetChanged();
	}

	@Override
	public SniperSnapshot getItem(int position) {
		return snapshotsList.get(position);
	}
	
	
}

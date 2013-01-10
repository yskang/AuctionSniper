package com.yskang.auctionsniper;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SnipersTableAdapter extends ArrayAdapter<SniperState> {
	private final static SniperState STARTING_UP = new SniperState("", 0, 0);
	private int statusTextId = R.string.status_joining;
	private SniperState sniperState = STARTING_UP;
	private ArrayList<SniperState> sniperStateList;
	private Context context;
	private TextView mStatusView;
	
	public SnipersTableAdapter(Context context, int textViewResourceId,
			ArrayList<SniperState> sinperStateList) {
		super(context, textViewResourceId, sinperStateList);
		this.sniperStateList = sinperStateList;
		this.context = context;
		
		this.sniperStateList.add(sniperState);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		View view = convertView;
		
		if(view == null){
			LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = vi.inflate(R.layout.auction_list_item, null);
		}
		
		SniperState item = sniperStateList.get(position);
		if(item != null){
			TextView itemNameView = (TextView)view.findViewById(R.id.itemName);
			TextView lastPriceView = (TextView)view.findViewById(R.id.lastPrice);
			TextView lastBidView = (TextView)view.findViewById(R.id.lastBid);
			mStatusView	= (TextView)view.findViewById(R.id.itemStatus);
			
			if(itemNameView != null){
				itemNameView.setText(item.getItemId());
			}
			if(lastPriceView != null){
				lastPriceView.setText(Integer.toString(item.getLastPrice()));
			}
			if(lastBidView != null){
				lastBidView.setText(Integer.toString(item.getLastBid()));
			}
		}
		return view;
	}

	public int getColumnCount(){
		return sniperStateList.size();
	}
	
	public int getRowCount(){
		return 1;
	}
	
	public Object getValueAt(int rowIndex, int columnIndex){
		switch(Column.at(columnIndex)){
		case ITEM_IDENTIFIER:
			return sniperState.itemId;
		case LAST_PRICE:
			return sniperState.lastPrice;
		case LAST_BID:
			return sniperState.lastBid;
		case SNIPER_STATUS:
			return statusTextId;
		default:
			throw new IllegalArgumentException("No column at" + columnIndex);
		}
	}
	
	public void setStatusText(int newStatusTextId) {
		statusTextId = newStatusTextId;
		mStatusView.setText(newStatusTextId);
	}

	public void sniperStatusChanged(SniperState newSniperState, int newStatusTextId) {
		sniperState = newSniperState;
		statusTextId = newStatusTextId;
		this.notifyDataSetChanged();
	}
}

package com.yskang.auctionsniper;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AuctionItemListAdapter extends ArrayAdapter<AuctionItem> {

	private ArrayList<AuctionItem> auctionItemList;
	private Context context;
	
	public AuctionItemListAdapter(Context context, int textViewResourceId,
			ArrayList<AuctionItem> auctionItemList) {
		super(context, textViewResourceId, auctionItemList);
		this.auctionItemList = auctionItemList;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		View view = convertView;
		
		if(view == null){
			LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = vi.inflate(R.layout.auction_list_item, null);
		}
		
		AuctionItem item = auctionItemList.get(position);
		if(item != null){
			TextView itemNameView = (TextView)view.findViewById(R.id.itemName);
			TextView lastPriceView = (TextView)view.findViewById(R.id.lastPrice);
			TextView lastBidView = (TextView)view.findViewById(R.id.lastBid);
			TextView statusView	= (TextView)view.findViewById(R.id.itemStatus);
			
			if(itemNameView != null){
				itemNameView.setText(item.getItemName());
			}
			if(lastPriceView != null){
				lastPriceView.setText(Integer.toString(item.getLastPrice()));
			}
			if(lastBidView != null){
				lastBidView.setText(Integer.toString(item.getLastBid()));
			}
			if(statusView != null){
				statusView.setText(item.getStatus());
			}
		}
		return view;
	}
}

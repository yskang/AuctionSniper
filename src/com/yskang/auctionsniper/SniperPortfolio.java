package com.yskang.auctionsniper;

import java.util.ArrayList;

public class SniperPortfolio implements SniperCollector {
	private final ArrayList<AuctionSniper> snipers = new ArrayList<AuctionSniper>();
	SnipersTableAdapter adapter;
	private final Announcer<PortfolioListener> listeners = Announcer.to(PortfolioListener.class);
	
	public void addPortfolioListener(PortfolioListener listener) {
		for (AuctionSniper sniper : snipers) {
			listener.sniperAdded(sniper);
		}
		listeners.addListener(listener);
	}

	@Override
	public void addSniper(AuctionSniper sniper) {
		snipers.add(sniper);
		listeners.announce().sniperAdded(sniper);
	}
}

package com.yskang.auctionsniper;

import java.util.EventListener;

public interface SniperListener extends EventListener {
	void sniperLost();

	void sniperBidding();
}

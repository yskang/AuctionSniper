package com.yskang.auctionsniper;

import java.util.EventListener;

public interface SniperListener extends EventListener {
	void sniperJoining(SniperSnapshot sniperSnapshot);
	void sniperLost(SniperSnapshot sniperSnapshot);
	void sniperBidding(SniperSnapshot sniperSnapshot);
	void sniperWinning(SniperSnapshot sniperSnapshot);
	void sniperWon(SniperSnapshot sniperSnapshot);
	void sniperStateChanged(SniperSnapshot sniperSnapshot);
}

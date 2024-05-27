package com.gmail.val59000mc.utils;

import com.gmail.val59000mc.players.UhcPlayer;
import java.util.Comparator;

public class KillTopUtil implements Comparator<UhcPlayer> {


	@Override
	public int compare(UhcPlayer a, UhcPlayer b) {
		return Integer.compare(b.getKills(), a.getKills());
	}


}

package com.availablers.colour;

import java.text.SimpleDateFormat;

public class ColourQuest {
	public final int r;
	public final int g;
	public final int b;
	public final SimpleDateFormat start;
	
	public ColourQuest(int r, int g, int b, SimpleDateFormat start) {
		this.r = (r < 0 ? 0 : r);
		this.g = (g < 0 ? 0 : g);
		this.b = (b < 0 ? 0 : b);
		this.start = start;
	}
}

package com.availablers.colour;

import java.util.Date;
import java.util.Random;

import android.graphics.Color;

public class ColourQuest {
	public final int r;
	public final int g;
	public final int b;
	public final Date start;
	
	public ColourQuest(int r, int g, int b, Date start) {
		this.r = (r < 0 ? 0 : r);
		this.g = (g < 0 ? 0 : g);
		this.b = (b < 0 ? 0 : b);
		this.start = start;
	}
	
	public static ColourQuest genColourQuest() {
		Random r = new Random();
		Date now = new Date();
		return new ColourQuest(r.nextInt(256-0), r.nextInt(256-0), r.nextInt(256-0), now);
	}
	
	public int getColor() {
		return Color.rgb(r, g, b);
	}
	
	public float[] getHSV() {
		float[] HSV = new float[3];
		Color.RGBToHSV(r, g, b, HSV);
		return HSV;
	}
	
	@Override
	public String toString() {
		return "(" + getHSV()[0] + ", " + getHSV()[1] + ", " + getHSV()[2] + ")";
	}
	
	
}

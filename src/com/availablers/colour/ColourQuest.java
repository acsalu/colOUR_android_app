package com.availablers.colour;

import java.util.Date;
import java.util.Random;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;

public class ColourQuest implements Parcelable {
	public final int r;
	public final int g;
	public final int b;
	public final Date start;
	
	public static final int QUEST_SPRING_SYMPHONY = 1;
	public static final int QUEST_SPECTRAL = 2;
	public static final int QUEST_AUTUMN_APPROACH = 3;
	public static final int QUEST_WINTER_WONDERLAND = 4;
	public static final int QUEST_NIGHT_AT_THE_BEACH = 5;
	
	public ColourQuest(int r, int g, int b) {
		this(r, g, b, new Date());
	}
	
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
	
	public static ColourQuest[] getColourQuests(int index) {
		ColourQuest quest0 = null;
		ColourQuest quest1 = null;
		ColourQuest quest2 = null;
		ColourQuest quest3 = null;
		ColourQuest quest4 = null;
		
		switch (index) {
		case QUEST_SPRING_SYMPHONY:
			quest0 = new ColourQuest(89, 132, 50);
			quest1 = new ColourQuest(148, 174, 22);
			quest2 = new ColourQuest(207, 202, 25);
			quest3 = new ColourQuest(246, 232, 44);
			quest4 = new ColourQuest(250, 236, 147);
			break;
		
		case QUEST_SPECTRAL:
			quest0 = new ColourQuest(255, 77, 77);
			quest1 = new ColourQuest(255, 108, 77);
			quest2 = new ColourQuest(255, 139, 77);
			quest3 = new ColourQuest(255, 170, 77);
			quest4 = new ColourQuest(255, 195, 77);
			break;
			
		case QUEST_AUTUMN_APPROACH:
			quest0 = new ColourQuest(35, 3, 5);
			quest1 = new ColourQuest(159, 49, 27);
			quest2 = new ColourQuest(255, 120, 24);
			quest3 = new ColourQuest(106, 29, 24);
			quest4 = new ColourQuest(213, 64, 26);
			break;
			
		case QUEST_WINTER_WONDERLAND:
			quest0 = new ColourQuest(16, 79, 103);
			quest1 = new ColourQuest(47, 131, 163);
			quest2 = new ColourQuest(78, 155, 185);
			quest3 = new ColourQuest(104, 165, 188);
			quest4 = new ColourQuest(150, 199, 218);
			break;
		
		case QUEST_NIGHT_AT_THE_BEACH:
			quest0 = new ColourQuest(38, 36, 54);
			quest1 = new ColourQuest(80, 60, 83);
			quest2 = new ColourQuest(159, 108, 102);
			quest3 = new ColourQuest(212, 137, 106);
			quest4 = new ColourQuest(255, 187, 108);
			break;
			
		default:
			return null;
		}
		ColourQuest[] quests = {quest0, quest1, quest2, quest3, quest4};
		return quests;
	}
	
	
	@Override
	public String toString() {
		return "(" + getHSV()[0] + ", " + getHSV()[	1] + ", " + getHSV()[2] + ")";
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}

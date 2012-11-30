package com.availablers.colour;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ColoursFragment extends Fragment {
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
		Log.d("colOUR.UI", "QuestFragment onCreate");
        return inflater.inflate(R.layout.fragment_colours, container, false);   
    }

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ColoursPagerAdapter adapter = new ColoursPagerAdapter();
		ViewPager mPager = (ViewPager) getView().findViewById(R.id.colours_panelpager);
		mPager.setAdapter(adapter);
		mPager.setCurrentItem(0);
		
	}
	
	
	
	
}

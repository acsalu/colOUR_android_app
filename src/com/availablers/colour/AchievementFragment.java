package com.availablers.colour;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AchievementFragment extends Fragment {
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
		Log.d("colOUR.UI", "QuestFragment onCreate");
        return inflater.inflate(R.layout.fragment_achievement, container, false);
    }
}

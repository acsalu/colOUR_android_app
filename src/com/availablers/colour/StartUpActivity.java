package com.availablers.colour;

import com.parse.Parse;
import com.parse.ParseFacebookUtils;

import android.app.Activity;
import android.os.Bundle;

public class StartUpActivity extends Activity {

	// application IDs, keys
	private static final String PARSE_APP_ID = "zWVIbuBEOCQmQEP8YGdpu9Fag3FdDowmpnogl41x";
	private static final String PARSE_CLIENT_KEY = "RS2i8WSFWnIgrN3NNFIApvymSEIeddGzyjzpAoMJ";
	private static final String FACEBOOK_APP_ID = "509468125744251";
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initParse();
		findViews();
		setListeners();
	}
	
	private void initParse() {
		Parse.initialize(this, PARSE_APP_ID, PARSE_CLIENT_KEY);
		ParseFacebookUtils.initialize(FACEBOOK_APP_ID);
	}
	
	
	private void findViews() {
		
	}
	
	private void setListeners() {
		
	}
	
}

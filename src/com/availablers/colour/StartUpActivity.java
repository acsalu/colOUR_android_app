package com.availablers.colour;

import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseFacebookUtils.Permissions;
import com.parse.ParseUser;

public class StartUpActivity extends Activity {

	// application IDs, keys
	private static final String PARSE_APP_ID = "zWVIbuBEOCQmQEP8YGdpu9Fag3FdDowmpnogl41x";
	private static final String PARSE_CLIENT_KEY = "RS2i8WSFWnIgrN3NNFIApvymSEIeddGzyjzpAoMJ";
	private static final String FACEBOOK_APP_ID = "509468125744251";
	
	// UI Components
	private Button buttonLogin;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_startup);
		initParse();
		findViews();
		setListeners();
		
		if (ParseUser.getCurrentUser() != null) {
			Intent intent = new Intent(getBaseContext(), MainActivity.class);
		    startActivity(intent);
		    finish();
		}
	}
	
	private void initParse() {
		Parse.initialize(this, PARSE_APP_ID, PARSE_CLIENT_KEY);
		ParseFacebookUtils.initialize(FACEBOOK_APP_ID);
	}
	
	
	private void findViews() {
		this.buttonLogin = (Button) findViewById(R.id.button_login);
	}
	
	private void setListeners() {
		this.buttonLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ParseFacebookUtils.logIn(Arrays.asList("publish_stream", "user_photos"), StartUpActivity.this, new LogInCallback() {
					  @Override
					  public void done(ParseUser user, ParseException err) {
					    if (user == null) {
					      Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
					      return;
					    } else if (user.isNew()) {
					      Log.d("MyApp", "User signed up and logged in through Facebook!");
					    } else {
					      Log.d("MyApp", "User logged in through Facebook!");
					    } 
					    
					    Intent intent = new Intent(getBaseContext(), MainActivity.class);
					    startActivity(intent);
					    finish();
					    
					  }
				});
				
				/*
				
				ParseFacebookUtils.logIn(StartUpActivity.this, new LogInCallback() {
					  @Override
					  public void done(ParseUser user, ParseException err) {
					    if (user == null) {
					      Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
					      return;
					    } else if (user.isNew()) {
					      Log.d("MyApp", "User signed up and logged in through Facebook!");
					    } else {
					      Log.d("MyApp", "User logged in through Facebook!");
					    } 
					    
					    Intent intent = new Intent(getBaseContext(), MainActivity.class);
					    startActivity(intent);
					    finish();
					    
					  }
				});
				*/
			}
		});
	}
	
}

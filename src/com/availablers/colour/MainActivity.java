package com.availablers.colour;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.parse.facebook.AsyncFacebookRunner;
import com.parse.facebook.Facebook;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {
	
	private static final String PARSE_APP_ID = "zWVIbuBEOCQmQEP8YGdpu9Fag3FdDowmpnogl41x";
	private static final String PARSE_CLIENT_KEY = "RS2i8WSFWnIgrN3NNFIApvymSEIeddGzyjzpAoMJ";
	private static final String FACEBOOK_APP_ID = "509468125744251";
	
    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
    private static final String KEY_QUESTS_INDEX = "colour.key.quests_index";
    private static final String KEY_SELECTED_QUEST_INDEX = "colour.key.selected_quest_index";
    
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private static final int CROP_IMAGE_ACTIVITY_REQUEST_CODE = 200;
    
    
    public AsyncFacebookRunner asyncRunner;
    
    private ColoursFragment mColoursFragment;
    private QuestFragment mQuestFragment;
    private DiscoverFragment mDiscoverFragment;
    
    public int mSelectedColourQuestIndex;
    public int mColourQuestsIndex;
    
    public Uri fileUri;
    
    
    public boolean isDiscovered;
    
    
    public void setColourQuestsIndex(int i) { 
    	mColourQuestsIndex = i; 
    	Log.d("colOUR.MainActivity", "mColourQuestsIndex is set to " + i);
    }
    public int getColourQuestsIndex() { return mColourQuestsIndex; }
    
    public void setSelectedColourQuestIndex(int i) { 
    	mSelectedColourQuestIndex = i; 
    	Log.d("colOUR.MainActivity", "mSelectedColourQuestIndex is set to " + i);
    }
    public int getSelectedColourQuestIndex() { return mSelectedColourQuestIndex; }
    
    
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	Log.d("colOUR.MainActivity.lifecycle", "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initParse();
        
        mSelectedColourQuestIndex = -1;
        mColourQuestsIndex = -1;
        
        this.isDiscovered = false;
        
        Facebook facebook = ParseFacebookUtils.getFacebook();
        asyncRunner = new AsyncFacebookRunner(facebook);
        /*
    	asyncRunner.request("me", new RequestListener() {

			@Override
			public void onComplete(String arg0, Object arg1) {

				try {
					final JSONObject json = Util.parseJson(arg0);
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							try {
								textUserName.setText("Hi, " + json.getString("name"));
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
					URL img_url = new URL("http://graph.facebook.com/" + json.getString("id") + "/picture?type=small");
					Bitmap bmp = BitmapFactory.decodeStream(img_url.openConnection().getInputStream());
					
				} catch (FacebookError e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}

			@Override
			public void onFacebookError(FacebookError arg0, Object arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onFileNotFoundException(FileNotFoundException arg0,
					Object arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onIOException(IOException arg0, Object arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onMalformedURLException(MalformedURLException arg0,
					Object arg1) {
			}
    		
    	});
        */
    	
        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // For each of the sections in the app, add a tab to the action bar.
        actionBar.addTab(actionBar.newTab().setText(R.string.title_section1).setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(R.string.title_section2).setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(R.string.title_section3).setTabListener(this));
        // By default, select tab1
        actionBar.selectTab(actionBar.getTabAt(1));
    }	
	
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        menu.add(0, 0, 0, "Logout");
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	
    	switch (item.getItemId()) {
    	case 0:
    		ParseUser.logOut();
    		Intent intent = new Intent(MainActivity.this, StartUpActivity.class);
    		startActivity(intent);
    		finish();
    	}
    	return super.onOptionsItemSelected(item);
    }
   
	@Override
	public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
		// When the given tab is selected, show the tab contents in the container
        /*
		Fragment fragment = new DummySectionFragment();
        Bundle args = new Bundle();
        args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, tab.getPosition() + 1);
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
        */
		Log.d("colOUR.UI", "Tab" + tab.getPosition() + " selected");
		Fragment fragment = null;
		switch (tab.getPosition()) {
		case 0:
			if (mColoursFragment == null) mColoursFragment = new ColoursFragment();
			fragment = mColoursFragment;
			break;
		case 1:
			if (mQuestFragment == null) {
				Log.d("colOUR.MainActivity.lifecycle", "create new QuestFragment");
				mQuestFragment = new QuestFragment();
			}
			fragment = mQuestFragment;
			break;
		case 2:
			if (mDiscoverFragment == null) mDiscoverFragment = new DiscoverFragment();
			fragment = mDiscoverFragment;
			break;
		default:
			return;
		}
		
		getSupportFragmentManager().beginTransaction()
        .replace(R.id.container, fragment)
        .commit();
	}


	@Override
	public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void onSaveInstanceState(Bundle outState) {
		Log.d("colOUR.MainActivity.lifecycle", "onSaveInstanceState");
		super.onSaveInstanceState(outState);
		outState.putInt(KEY_QUESTS_INDEX, this.mColourQuestsIndex);
		outState.putInt(KEY_SELECTED_QUEST_INDEX, this.mSelectedColourQuestIndex);
		
	}
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		Log.d("colOUR.MainActivity.lifecycle", "onRestoreInstanceState");
		super.onRestoreInstanceState(savedInstanceState);
		
		if (savedInstanceState != null) {
			mColourQuestsIndex = savedInstanceState.getInt(KEY_QUESTS_INDEX);
			mSelectedColourQuestIndex = savedInstanceState.getInt(KEY_SELECTED_QUEST_INDEX);
		}
	}
	
	private void initParse() {
		Parse.initialize(this, PARSE_APP_ID, PARSE_CLIENT_KEY);
		ParseFacebookUtils.initialize(FACEBOOK_APP_ID);
	}
	
	
	
}

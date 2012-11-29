package com.availablers.colour;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.ParseUser;
import com.parse.facebook.AsyncFacebookRunner;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {
	
	
    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
    
    private AsyncFacebookRunner asyncRunner;
    
    private ColoursFragment mColoursFragment;
    private QuestFragment mQuestFragment;
    private AchievementFragment mAchievementFragment;
    
    public int mColourQuestSelectedIndex;
    public int mColourQuestsIndex;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	Log.d("colOUR.MainActivity.lifecycle", "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mColourQuestSelectedIndex = -1;
        mColourQuestsIndex = -1;
        /*
        Facebook facebook = ParseFacebookUtils.getFacebook();
        asyncRunner = new AsyncFacebookRunner(facebook);
        
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
        return true;
    	//menu.add(0, 0, 0, "Logout");
    	//return true;
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
			if (mAchievementFragment == null) mAchievementFragment = new AchievementFragment();
			fragment = mAchievementFragment;
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
	}
	
}

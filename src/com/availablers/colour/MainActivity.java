package com.availablers.colour;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.parse.facebook.AsyncFacebookRunner;
import com.parse.facebook.AsyncFacebookRunner.RequestListener;
import com.parse.facebook.Facebook;
import com.parse.facebook.FacebookError;
import com.parse.facebook.Util;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {
	
	
    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
    
    
    
	
	
    
    private AsyncFacebookRunner asyncRunner;
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        findViews();
        setListeners();
        
       
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
    	
        actionBar.selectTab(actionBar.getTabAt(1));
    }
    
    
    
    
    


	
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d("colOUR.activity", "back to MainActivity");
		/*
		if (data == null) {
			Log.d("colOUR.activity", "data is null");
		}
		
		switch (requestCode) {
		case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE: 
			if (resultCode == RESULT_OK) {
				Toast.makeText(this, "Image saved to:\n" + data.getData(), Toast.LENGTH_LONG).show();
				fileUri = data.getData();
				//performCrop();
				Bundle extras = data.getExtras();
				Bitmap pic = extras.getParcelable("data");
				capturedImage.setImageBitmap(pic);
			} else if (resultCode == RESULT_CANCELED) {
				// User cancelled the image capture
				// nothing should happen
			}
			break;
		case CROP_IMAGE_ACTIVITY_REQUEST_CODE:
			if (resultCode == RESULT_OK) {
				Bundle extras = data.getExtras();
				Bitmap pic = extras.getParcelable("data");
				capturedImage.setImageBitmap(pic);
			}
			
		}
		*/
		super.onActivityResult(requestCode, resultCode, data);
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
    
    /*
	
    
    public static class DummySectionFragment extends Fragment {
        public DummySectionFragment() {
        }

        public static final String ARG_SECTION_NUMBER = "section_number";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
        	
            TextView textView = new TextView(getActivity());
            textView.setGravity(Gravity.CENTER);
            Bundle args = getArguments();
            textView.setText(Integer.toString(args.getInt(ARG_SECTION_NUMBER)));
            return textView;
        }
    }

	*/
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
			fragment = new ColoursFragment();
			break;
		case 1:
			fragment = new QuestFragment();
			break;
		case 2:
			fragment = new AchievementFragment();
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
    
    
}

package com.availablers.colour;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

public class MainActivity extends Activity { //implements ActionBar.TabListener {

	// application IDs, keys
	private static final String PARSE_APP_ID = "zWVIbuBEOCQmQEP8YGdpu9Fag3FdDowmpnogl41x";
	private static final String PARSE_CLIENT_KEY = "RS2i8WSFWnIgrN3NNFIApvymSEIeddGzyjzpAoMJ";
	private static final String FACEBOOK_APP_ID = "509468125744251";
	
	
    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private static final int CROP_IMAGE_ACTIVITY_REQUEST_CODE = 200;
    
	// UI elements
    private ImageView capturedImage;
    private Button capturePhoto;
    private Button cropImage;
    private Button buttonLogin;
    
    public static final int MEDIA_TYPE_IMAGE = 1;
    private Uri fileUri;
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialParse();
        findViews();
        setListeners();
        
        
       
        
        /*
        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // For each of the sections in the app, add a tab to the action bar.
        actionBar.addTab(actionBar.newTab().setText(R.string.title_section1).setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(R.string.title_section2).setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(R.string.title_section3).setTabListener(this));
    	*/
    }
    
    private void initialParse() {
    	Parse.initialize(this, PARSE_APP_ID, PARSE_CLIENT_KEY);
    	// enable SSO
        ParseFacebookUtils.initialize(FACEBOOK_APP_ID);	
        
    }
    
    
    private void findViews() {
    	this.capturedImage = (ImageView) findViewById(R.id.captured_image);
    	this.capturePhoto = (Button) findViewById(R.id.capture_photo);
    	this.cropImage = (Button) findViewById(R.id.crop_image);
    	this.buttonLogin = (Button) findViewById(R.id.login_fb);
    }
    
    private void setListeners() {
    	this.capturePhoto.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("colOUR.UI", "Attempt to capture image");
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				//fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
				//intent.putExtra(MediaStore.EXTRA_OUTPUT, MyFileContentProvider.CONTENT_URI);
				startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
			}
		});
    	
    	this.cropImage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				performCrop();
			}
		});
    	
    	this.buttonLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ParseFacebookUtils.logIn(MainActivity.this, new LogInCallback() {
					  @Override
					  public void done(ParseUser user, ParseException err) {
					    if (user == null) {
					      Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
					    } else if (user.isNew()) {
					      Log.d("MyApp", "User signed up and logged in through Facebook!");
					    } else {
					      Log.d("MyApp", "User logged in through Facebook!");
					    }
					  }
				});
			}
		});
    }


	/**
	 * code for saving the image
	 */
	
    private static Uri getOutputMediaFileUri(int type) {
    	// Log.d("colOUR.IO", fileUri.toString());
    	return Uri.fromFile(getOutputMediaFile(type));
    }
    
    private static File getOutputMediaFile(int type) {
    	// To be safe, you should check that the SDCard is mounted
    	// using Environment.getExternalStorageState() before doing this.
    	Log.d("colOUR.IO", Environment.getExternalStorageState());
    	
    	File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "colOUR");
    	if (!mediaStorageDir.exists()) {
    		if (!mediaStorageDir.mkdirs()) {
    			Log.d("colOUR.IO", "failed to create directory");
    			return null;
    		}
    	}
    	
    	// Create a media file name
    	String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    	File mediaFile = null;
    	if (type == MEDIA_TYPE_IMAGE) {
    		String path = mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg";
        	mediaFile = new File(path);
        	Bundle bundle = new Bundle();
    		Log.d("colOUR.IO", "media file " + path + " saved");
    	}
    	return mediaFile;
    }
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d("colOUR.activity", "back to MainActivity");
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
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	
	private void performCrop() {
		try {
			// a crop Intent
			Intent intent = new Intent("com.android.camera.action.CROP");
			intent.setDataAndType(fileUri, "image/*");
			intent.putExtra("crop", true);
			//intent.putExtra("aspectX", 1);
			//intent.putExtra("aspectY", 1);
			//intent.putExtra("outputX", 256);
			//intent.putExtra("outputY", 256);
			intent.putExtra("return-data", true);
			    //start the activity - we handle returning in onActivityResult
			startActivityForResult(intent, CROP_IMAGE_ACTIVITY_REQUEST_CODE);
		} catch (ActivityNotFoundException anfe) {
			String errorMessage = "Whoops - your device doesn't support the crop action";
		}
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    
    /*
    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, show the tab contents in the container
        Fragment fragment = new DummySectionFragment();
        Bundle args = new Bundle();
        args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, tab.getPosition() + 1);
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }
	*/
    
    /**
     * A dummy fragment representing a section of the app, but that simply displays dummy text.
     */
    
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
    
}

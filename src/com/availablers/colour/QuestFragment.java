package com.availablers.colour;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class QuestFragment extends Fragment {
	
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private static final int CROP_IMAGE_ACTIVITY_REQUEST_CODE = 200;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private Uri fileUri;
	
	// UI elements
    private Button buttonCaptureImage;
    private Button buttonCropImage;
    private TextView textUserName;
    private ImageView imageResult;
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
		Log.d("colOUR.UI", "QuestFragment onCreate");
		View view = inflater.inflate(R.layout.fragment_quest, container, false);
        return view;
    }
	
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		findViews();
		setListeners();
	}
	
	



	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		Log.d("colOUR.activity", "it's back");
		super.onActivityResult(requestCode, resultCode, data);
	}



	private void findViews() {
    	this.buttonCaptureImage = (Button) getView().findViewById(R.id.button_captureimage);
    	this.buttonCropImage = (Button) getView().findViewById(R.id.button_cropimage);
    	this.textUserName = (TextView) getView().findViewById(R.id.text_username);
    	this.imageResult = (ImageView) getView().findViewById(R.id.image_result);
    }
	
	private void setListeners() {
    	this.buttonCaptureImage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("colOUR.UI", "Attempt to capture image");
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				//fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
				//intent.putExtra(MediaStore.EXTRA_OUTPUT, MyFileContentProvider.CONTENT_URI);
				QuestFragment.this.startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
			}
		});
    	
    	this.buttonCropImage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				performCrop();
			}
		});
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
	
	// code for saving the image
	/* 
	
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
    */
	
	
}

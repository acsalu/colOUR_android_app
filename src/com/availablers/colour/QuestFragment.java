package com.availablers.colour;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class QuestFragment extends Fragment {
	
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private static final int CROP_IMAGE_ACTIVITY_REQUEST_CODE = 200;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private Uri fileUri;
	
	// UI elements
    //private Button buttonCaptureImage;
    private TextView textUserName;
    private ImageView imageResult;
    
    private View.OnClickListener imageCaptureListener;
    
    private ColourQuest selectedColourQuest;
    private ColourQuest[] colourQuests;
	private Canvas mCanvas;
    private Paint mPaint;
	
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
		mCanvas = new Canvas();
		
		imageCaptureListener= new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("colOUR.UI", "Attempt to capture image");
				Button b = (Button) v;
				Log.d("colOUR.colour", "quest: " + colourQuests[b.getId()] + " is selected");
				selectedColourQuest = colourQuests[b.getId()];
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				QuestFragment.this.startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
				
			}
		};
		
		/*
		Log.d("Colour.test", "hi");
		Log.d("Colour.quest", "quest: " + colourQuest + " is assigned");
		mPaint = new Paint();
		mPaint.setColor(Color.rgb(colourQuest.r, colourQuest.g, colourQuest.b));
		mPaint.setStyle(Paint.Style.FILL);
		mCanvas.drawRect(new Rect(150, 300, 260, 400), mPaint);
		*/
		
		LinearLayout layout = (LinearLayout) getView().findViewById(R.id.fragment_quest_layout);
		
		colourQuests = new ColourQuest[10];
		
		for (int i = 0; i < 10; ++i) {
			colourQuests[i] = ColourQuest.genColourQuest();
			Button questButton = new Button(getActivity());
			questButton.setHeight(100);
			questButton.setBackgroundColor(colourQuests[i] .getColor());
			layout.addView(questButton);
			questButton.setOnClickListener(imageCaptureListener);
			questButton.setId(i);
		}
		
		
		
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d("colOUR.activity", "it's back to QuestFragment");
		
		if (data == null) {
			Log.d("colOUR.activity", "data is null");
		}
		
		switch (requestCode) {
		case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE: 
			if (resultCode == Activity.RESULT_OK) {
				Toast.makeText(getActivity(), "Image saved to:\n" + data.getData(), Toast.LENGTH_LONG).show();
				fileUri = data.getData();
				//performCrop();
				Bundle extras = data.getExtras();
				//File imgFile = new File(fileUri.toString());
				//Bitmap pic = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
				//Bitmap pic = extras.getParcelable("data");
				//imageResult.setImageURI(null);
				//imageResult.setImageURI(fileUri);
				performCrop();
			} else if (resultCode == Activity.RESULT_CANCELED) {
				// User cancelled the image capture
				// nothing should happen
			}
			break;
		case CROP_IMAGE_ACTIVITY_REQUEST_CODE:
			if (resultCode == Activity.RESULT_OK) {
				Bundle extras = data.getExtras();
				Bitmap pic = extras.getParcelable("data");
				
				int[] averageARGB = averageARGB(pic);
				float[] resultHSV = new float[3];
				Color.RGBToHSV(averageARGB[1], averageARGB[2], averageARGB[3], resultHSV);
				Log.d("colOUR.colour", "result: (" + resultHSV[0] + ", " + resultHSV[1] + ", " + resultHSV[2] + ")");
				//imageResult.setImageBitmap(pic);
			}
			
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}



	private void findViews() {
    	this.textUserName = (TextView) getView().findViewById(R.id.text_username);
    	//this.imageResult = (ImageView) getView().findViewById(R.id.image_result);
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
	
	private int[] averageARGB(Bitmap pic) {
		int A, R, G, B;
		A = R = G = B = 0;
		int pixelColor;
		int width = pic.getWidth();
		int height = pic.getHeight();
		int size = width * height;
		
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				pixelColor = pic.getPixel(x, y);
				A += Color.alpha(pixelColor);
				R += Color.red(pixelColor);
				G += Color.green(pixelColor);
				B += Color.blue(pixelColor);
			}
		}
		
		A /= size;
		R /= size;
		G /= size;
		B /= size;
		
		Log.d("Colour.color", "average ARGB = (" + A + ", " + R + ", " + G + ", " + B + ")");
		
		int[] average = {A, R, G, B};
		return average;
		
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

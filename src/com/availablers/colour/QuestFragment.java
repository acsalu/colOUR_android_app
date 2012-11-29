package com.availablers.colour;

import java.util.Random;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class QuestFragment extends Fragment {
	
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private static final int CROP_IMAGE_ACTIVITY_REQUEST_CODE = 200;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private static final String QUEST_KEY_SELECTED_QUEST_INDEX = "selectedQuest.index";
    private static final String QUEST_KEY_QUESTS_INDEX = "quests.index";
    private Uri fileUri;
    
    private static final int[] QUESTS_DRAWABLE_ID = {R.drawable.quests_0, R.drawable.quests_1, 
    												 R.drawable.quests_2, R.drawable.quests_3, R.drawable.quests_4};
	
	// UI elements
    //private Button buttonCaptureImage;
    private ImageView imageShowcase;
    
    private View.OnClickListener imageCaptureListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Log.d("colOUR.UI", "Attempt to capture image");
			Button b = (Button) v;
			Log.d("colOUR.colour", "quest: (H, S, V) = " + colourQuests[b.getId()] + " is selected");
			selectedColourQuestIndex = b.getId();	
			selectedColourQuest = colourQuests[b.getId()];
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			QuestFragment.this.startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
		}
	};
    
    private int selectedColourQuestIndex;
    private ColourQuest selectedColourQuest;
    private int colourQuestsIndex;
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
		

		
		if (savedInstanceState != null) {
			Log.d("colOUR.activity", "onActivityCreated w/ savedInstanceState");
			
			selectedColourQuestIndex = savedInstanceState.getInt(QUEST_KEY_SELECTED_QUEST_INDEX);
			colourQuestsIndex = savedInstanceState.getInt(QUEST_KEY_QUESTS_INDEX);
			
			Log.d("colOUR.activity", "selectedColourQuestIndex = " + selectedColourQuestIndex);
			Log.d("colOUR.activity", "colourQuestsIndex = " + colourQuestsIndex);
			
			colourQuests = ColourQuest.getColourQuests(colourQuestsIndex);
			selectedColourQuest = colourQuests[selectedColourQuestIndex];
		} else {
			Log.d("colOUR.activity", "onActivityCreated w/o savedInstanceState");
			
			Random r = new Random();
			colourQuestsIndex = r.nextInt(5 - 0);
			
			Log.d("colOUR.activity", "colourQuestsIndex = " + colourQuestsIndex);
			colourQuests = ColourQuest.getColourQuests(colourQuestsIndex);
		}
		

		imageShowcase.setImageResource(QUESTS_DRAWABLE_ID[colourQuestsIndex]);
		
		LinearLayout layout = (LinearLayout) getView().findViewById(R.id.fragment_quest_quests_layout);
		
		Display display = getActivity().getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		
		Log.d("colOUR.UI", "display size = (" + size.x + ", " + size.y + ")");
		if (colourQuests == null) {
			Log.d("colOUR.QuestFragment", "gonna gg");
		}
		for (int i = 0; i < colourQuests.length; ++i) {
			Button questButton = new Button(getActivity());
			questButton.setLayoutParams(new LayoutParams(size.x / colourQuests.length, 500));
			questButton.setBackgroundColor(colourQuests[i] .getColor());
			layout.addView(questButton);
			questButton.setOnClickListener(imageCaptureListener);
			questButton.setId(i);
		}
		
		//imageShowcase.setImageDrawable(getView().getResources().getDrawable(R.drawable.quests_1));
		
		
		
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
				if (selectedColourQuest == null) {
					Log.d("colOUR.activity", "GG");
				} else {
					Log.d("colOUR.colour", "quest:  (H, S, V) = " + selectedColourQuest.toString());
				}
				Log.d("colOUR.colour", "result: (H, S, V) = (" + resultHSV[0] + ", " + resultHSV[1] + ", " + resultHSV[2] + ")");
				
				
				/*
				if (isHsvMatch(selectedColourQuest.getHSV(), resultHSV)) {
					Toast.makeText(getActivity(), "Pass!!!", Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(getActivity(), "Failed...", Toast.LENGTH_LONG).show();
				}
				*/
				//imageResult.setImageBitmap(pic);
			}
			
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}



	private void findViews() {
    	this.imageShowcase = (ImageView) getView().findViewById(R.id.image_showcase);
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
	
	private static boolean isHsvMatch(float[] quest, float[] result) {
		return ((result[0] <= quest[0] + 5 || result[0] >= quest[0] - 5) &&
				(result[1] <= quest[1] + 0.1 || result[1] >= quest[1] - 0.1) &&
				(result[2] <= quest[2] + 0.1 || result[2] >= quest[2] - 0.1));
	}

	


	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.d("colOUR.lifecycle", "QuestFragment.onPause()");
	}



	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.d("colOUR.lifecycle", "QuestFragment.onStop()");
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		Log.d("colOUR.lifecycle", "QuestFragment.onDestroyView()");
	}

	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d("colOUR.lifecycle", "QuestFragment.onDestroy()");
		
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		Log.d("colOUR.lifecycle", "QuestFragment.onSaveInstanceState");
		super.onSaveInstanceState(outState);
		outState.putInt(QUEST_KEY_SELECTED_QUEST_INDEX, selectedColourQuestIndex);
		outState.putInt(QUEST_KEY_QUESTS_INDEX, colourQuestsIndex);
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

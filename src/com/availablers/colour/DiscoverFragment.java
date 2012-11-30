package com.availablers.colour;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import com.parse.ParseFacebookUtils;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.facebook.AsyncFacebookRunner;
import com.parse.facebook.FacebookError;
import com.parse.facebook.AsyncFacebookRunner.RequestListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class DiscoverFragment extends Fragment {

	private ImageView imageDiscovered;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 300;
	private static final int CROP_IMAGE_ACTIVITY_REQUEST_CODE = 400;
	private Uri fileUri;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
		Log.d("colOUR.UI", "QuestFragment onCreate");
		View view = inflater.inflate(R.layout.fragment_discover, container, false);
        return view;
    }
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		this.imageDiscovered = (ImageView) getView().findViewById(R.id.image_discovered);
		MainActivity activity = (MainActivity) getActivity();
		Log.d("colOUR.DiscoverFragment.lifecycle", "onActivityCreated()");
		if (!activity.isDiscovered) {
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			DiscoverFragment.this.startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d("colOUR.activity", "it's back to DiscoverFragment");
		switch (requestCode) {
		case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE: 
			if (resultCode == Activity.RESULT_OK) {
				Log.d("colOUR.DiscoverFragment.onActivityResult", "back from Camera");
				fileUri = data.getData();
				//performCrop();
				Bundle extras = data.getExtras();
				//File imgFile = new File(fileUri.toString());
				//Bitmap pic = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
				//Bitmap pic = extras.getParcelable("data");
				//imageResult.setImageURI(null);
				//imageResult.setImageURI(fileUri);
				performCrop();
			}
			break;
		case CROP_IMAGE_ACTIVITY_REQUEST_CODE:
			if (resultCode == Activity.RESULT_OK) {
				Log.d("colOUR.QuestFragment.onActivityResult", "back from Crop");
				Bundle extras = data.getExtras();
				Bitmap pic = null;
				if (extras != null) {
					pic = extras.getParcelable("data");
				} else {
					Uri uri = data.getData();
					if (uri != null) pic = BitmapFactory.decodeFile(uri.getPath());
				}
				
				int[] averageARGB = averageARGB(pic);
				
				//imageDiscovered.setImageURI(fileUri);
				//imageResult.setImageBitmap(pic);
				MainActivity activity = (MainActivity) getActivity();
				activity.isDiscovered = true;
				
			}
			break;
			
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	
	
	
	
	private void performCrop() {
		try {
			// a crop Intent
			Intent intent = new Intent("com.android.camera.action.CROP");
			intent.setDataAndType(fileUri, "image/*");
			intent.putExtra("crop", true);
			intent.putExtra("return-data", true);
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
	
	


}

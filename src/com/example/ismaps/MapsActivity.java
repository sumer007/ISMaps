package com.example.ismaps;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MapsActivity extends Activity implements OnMapReadyCallback{
	
	private static final String TAG = "MapsActivity";
	GoogleMap mGoogleMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps_layout);
	}

	@Override
	public void onMapReady(GoogleMap googleMap) {
		// TODO Auto-generated method stub
		Log.i(TAG, "map has been loaded");
		mGoogleMap = googleMap;
		mGoogleMap.setMyLocationEnabled(true);
	}
}

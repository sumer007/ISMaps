package com.example.ismaps;

import android.location.Location;
import android.widget.TextView;

import com.google.android.gms.location.LocationListener;

public class LocListener implements LocationListener {
	
	Location mLocation;
	TextView lat_textview, long_textview;

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		mLocation = location;
	}

}

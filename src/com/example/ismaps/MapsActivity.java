package com.example.ismaps;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import android.app.Activity;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MapsActivity extends Activity implements OnMapReadyCallback, LocationListener,
GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{
	
	private static final String TAG = "MapsActivity";
	GoogleApiClient mGoogleApiClient;
	GoogleMap mGoogleMap;
	LocationRequest mLocationRequest;
	LatLng mLocation;
	TextView lat_textview, long_textview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps_layout);
		mGoogleApiClient = new GoogleApiClient.Builder(this)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.addApi(LocationServices.API)
				.build();
		mLocation = new LatLng(0, 0);
		MapFragment mapFragment_port = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
		MapFragment mapFragment_land = (MapFragment) getFragmentManager().findFragmentById(R.id.map_frag);
		
		if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
			mapFragment_port.getMapAsync(this);
		else
			mapFragment_land.getMapAsync(this);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		mGoogleApiClient.connect();
	}
	
	@Override
	protected void onStop() {
		if(mGoogleApiClient.isConnected())
			mGoogleApiClient.disconnect();
		super.onStop();
	}

	@Override
	public void onMapReady(GoogleMap googleMap) {
		// TODO Auto-generated method stub
		Log.i(TAG, "map has been loaded");
		mGoogleMap = googleMap;
		mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		mGoogleMap.setMyLocationEnabled(true);
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "location connection failed", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		// TODO Auto-generated method stub
		mLocationRequest = LocationRequest.create();
		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		mLocationRequest.setInterval(1000);
		
		LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
	}

	@Override
	public void onConnectionSuspended(int cause) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			lat_textview = (TextView) findViewById(R.id.lat_textview);
			long_textview = (TextView) findViewById(R.id.long_textview);
			
			lat_textview.setText(Double.toString(location.getLatitude()));
			long_textview.setText(Double.toString(location.getLongitude()));
		}
		else {
			lat_textview = (TextView) findViewById(R.id.lat_textview_land);
			long_textview = (TextView) findViewById(R.id.long_textview_land);
			
			lat_textview.setText(Double.toString(location.getLatitude()));
			long_textview.setText(Double.toString(location.getLongitude()));
		}
		show_location(location);
	}

	private void show_location(Location location) {
		// TODO Auto-generated method stub
		mLocation = new LatLng(location.getLatitude(), location.getLongitude());
		CameraPosition target = CameraPosition.builder().target(mLocation).zoom(14).build();
		mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(target));
	}
}

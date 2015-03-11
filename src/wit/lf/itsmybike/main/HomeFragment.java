package wit.lf.itsmybike.main;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.itsmybike.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

public class HomeFragment extends  Fragment implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener, OnMapReadyCallback, OnMapLongClickListener {
	


	

	private GlobalState gs;
	private LocationManager manager;
	private GoogleApiClient client;
	private GoogleMap map;
	private MapFragment mapFr;
	private View rootView;
	
	
	@Override
	public void onAttach(Activity activity) {
		
		super.onAttach(activity);
		
		
		Log.v("test", "activityreference " + activity);
	}
	



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		
		

		if(rootView!=null){
			
			ViewGroup parent = (ViewGroup) rootView.getParent();
			if(parent != null){
				
				parent.removeView(rootView);
			}
		}
		try{
			rootView = inflater.inflate(R.layout.fragment_home, container, false);
		}catch(InflateException e){
			
			
			
		}
		
		return rootView;
	}



	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		
		
		super.onViewCreated(view, savedInstanceState);
		
		
		gs = (GlobalState) getActivity().getApplication();
		mapFr = (MapFragment)getActivity().getFragmentManager().findFragmentById(R.id.map);
		map = mapFr.getMap();

		

		
		client = new GoogleApiClient.Builder(getActivity())
		.addApi(LocationServices.API)
		.addConnectionCallbacks(this)
		.addOnConnectionFailedListener(this)
		.build();
		client.connect();

		checkGPStatus();
		
			
			manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
			
			
			Criteria criteria = new Criteria();
			String  mBestProvider = manager.getBestProvider(criteria, true);
		manager.requestLocationUpdates(mBestProvider, 50000, 10000, this);
		
		
	
	/*	if(location==null){

			Toast.makeText(getActivity().getApplicationContext(), "no location", Toast.LENGTH_SHORT).show();
			
		}*/
	
		//manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 50000, 10, this);
		
		
		
		for (int i = 0; i < gs.getStolenBikes().size(); i++){
			
			
			LatLng templl = new LatLng(gs.getStolenBikes().get(i).getLat(), gs.getStolenBikes().get(i).getLng());
			/*map.addMarker( new MarkerOptions()
			  .position( templl));
			  //.title("Fence " + fence.getId())
			  //.snippet("Radius: " + fence.getRadius()) ).showInfoWindow();
			*/
			
			Geofence.Builder fence = new Geofence.Builder();
			fence.setCircularRegion( templl.latitude, templl.latitude, 10);
			
		
			CircleOptions circleOptions = new CircleOptions()
			  .center( templl )
			  .radius( 10 )
			  .fillColor(getResources().getColor(R.color.locationcolor))
			  .strokeColor(Color.TRANSPARENT)
			  .strokeWidth(2);

			
			map.addCircle(circleOptions);
			
		}
	
	
		
		
	}
	

	



	private void checkGPStatus() {
		boolean gpsEnabled = false;
		boolean networkEnabled = false;
		try{
			gpsEnabled = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		}catch(Exception ex){}
		try{
			networkEnabled = manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		}catch(Exception ex){}
		
		if(!gpsEnabled && !networkEnabled){
			AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
			dialog.setMessage(getActivity().getResources().getString(R.string.gps_network_not_enabled));
			dialog.setPositiveButton(getActivity().getResources().getString(R.string.open_location_settings), new DialogInterface.OnClickListener() {
				

				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
					startActivity(intent);
					
				}
			});
		}
		
		
	}





	@Override
	public void onMapReady(GoogleMap map) {
		
	/*    map.addMarker(new MarkerOptions()
	        .position()
	        .title("Hello world"));*/
	}
	
	@Override
	  public void onMapLongClick(LatLng point) {
	    /* Toast toast = new Toast(this.getActivity());
	     toast.show();*/
	  }



	@Override
	public void onLocationChanged(Location location) {
		LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());
		gs.setCurrentLat(location.getLatitude());
		gs.setCurrentLng(location.getLongitude());
		
		map.moveCamera(CameraUpdateFactory.newLatLng(latlng));
		map.animateCamera(CameraUpdateFactory.zoomTo(16));
		
		
		
	}



	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onConnectionFailed(ConnectionResult result) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onConnected(Bundle connectionHint) {
		
		checkGPStatus();
		Location lastKnownLocation = LocationServices.FusedLocationApi.getLastLocation(client);
		
		gs.setCurrentLat(lastKnownLocation.getLatitude());
		gs.setCurrentLng(lastKnownLocation.getLongitude());
		map = mapFr.getMap();
		map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng( lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude())));
		
		map.animateCamera(CameraUpdateFactory.zoomTo(16));
		
	}



	@Override
	public void onConnectionSuspended(int cause) {
		// TODO Auto-generated method stub
		
	}


	



}


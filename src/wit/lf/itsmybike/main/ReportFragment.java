package wit.lf.itsmybike.main;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.itsmybike.R;
import com.parse.ParseObject;

import wit.lf.itsmybike.data.Bike;
import wit.lf.itsmybike.data.StolenBike;

public class ReportFragment extends Fragment{
	
	
	private Spinner spinner;
	private GlobalState gs;
	private View rootView;
	private RadioGroup radioGroup;
	private GridLayout gl;
	private EditText editlat;
	private EditText editlng;
	private RadioButton input;
	private RadioButton useCurrent;
	private Button report;
	
	
	@Override
	public void onAttach(Activity activity) {
		//((Base) activity) .
		super.onAttach(activity);
		
		
		Log.v("test", "activityreference " + activity);
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		
		if(rootView!=null){
			
			ViewGroup parent = (ViewGroup) rootView.getParent();
			if(parent != null){
				
				parent.removeView(rootView);
			}
		}
		try{
			rootView = inflater.inflate(R.layout.fragment_report, container, false);
		}catch(InflateException e){
			
			
			
		}
		
		return rootView;
		
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		
	
		gs = (GlobalState) getActivity().getApplication();
				
			
			
			spinner  = (Spinner) getActivity().findViewById(R.id.bikes_spinner);
			
			Resources res = getResources();
			BikeAdapter adapter = new BikeAdapter(getActivity(), R.layout.bike_spinner_item, gs.getListOfBikes(), res);
			spinner.setAdapter(adapter);
			
			gl = (GridLayout) getActivity().findViewById(R.id.lat_lng_grid);
			
			
			editlat = (EditText) getActivity().findViewById(R.id.latitudeInput);
			editlng = (EditText) getActivity().findViewById(R.id.longitudeInput);
			
			input = (RadioButton) getActivity().findViewById(R.id.radioInputLocation);
			useCurrent = (RadioButton) getActivity().findViewById(R.id.radioCurrentLocation);
			

			gl.setEnabled(false);
			editlat.setEnabled(false);
			editlng.setEnabled(false);
			
			radioGroup = (RadioGroup) getActivity().findViewById(R.id.lat_lng_radiogroup);
			radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener(){

				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					
					if(input.isChecked()){
						
						Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps"));
						startActivity(intent);
						
						
						gl.setEnabled(true);
						editlat.setEnabled(true);
						editlat.setText("");
						editlng.setEnabled(true);
						editlng.setText("");
						
					}else if(useCurrent.isChecked()){
						
						if(gs.getCurrentLat()==null||gs.getCurrentLng()==null){
							
							Toast.makeText(getActivity().getApplicationContext(), "There was a problem getting your current location, please check your location settings", Toast.LENGTH_SHORT).show();
							
							
						}else{
						
						gl.setEnabled(false);
						editlat.setEnabled(false);
						editlat.setText(gs.getCurrentLat().toString());
						editlng.setEnabled(false);
						editlng.setText(gs.getCurrentLng().toString());
						}
					}
					
				}
				
				
				
				
				
			});
			
			
			report = (Button) getActivity().findViewById(R.id.button_report);
			report.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					
					if(reportBike()){
						
						Toast.makeText(getActivity().getApplicationContext(), "Bike Registered as Stolen.", Toast.LENGTH_SHORT).show();
					}else{
						
						Toast.makeText(getActivity().getApplicationContext(), "This bike is already Registered as Stolen .", Toast.LENGTH_SHORT).show();
					}
					
				}
				
				
				
				
			});
	}
	

	
	public boolean reportBike(){
		
		
	
/*	
		int selected = spinner.getSelectedItemPosition();
		
		
		if(selected==0){
			Toast.makeText(getActivity().getApplicationContext(), "Please select a bike first", Toast.LENGTH_SHORT).show();
			
		}
		Bike testBike = gs.getProfile().getListOfBikes().get(selected-1);
		if(testBike.isStolen()){
			
			return false;
		}*/
	
	
		
/*		Time now = new Time();
		now.setToNow();
		String date = now.monthDay + "/" + now.month + "/" + now.year;*/
		
		//gs.getProfile().getListOfBikes().get(selected-1).setStolen(true);
	
		StolenBike stolenBike = new StolenBike();
		stolenBike.put("lat", gs.getCurrentLat());
		stolenBike.put("lng", gs.getCurrentLng());
		stolenBike.put("serialNumber", "testSerial - need to fix");
		stolenBike.saveInBackground();
		
		//gs.getStolenBikes().add(new StolenBike(gs.getCurrentLat(), gs.getCurrentLng(), testBike.getSerialNo(), date));
		
		
		
		return true;
		
		
	}


	

	public void inputLocationClicked(View view){
		
		gl.setEnabled(true);
		editlat.setEnabled(true);
		editlng.setEnabled(true);
		
		
	}
	
	public void currentLocationClicked(View view){
		
		gl.setEnabled(false);
		editlat.setEnabled(false);
		editlng.setEnabled(false);
		
		
	}
}

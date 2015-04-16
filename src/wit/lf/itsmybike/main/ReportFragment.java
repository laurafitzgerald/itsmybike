package wit.lf.itsmybike.main;

import java.util.ArrayList;
import java.util.List;

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
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

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
			
			
			
			
			
			
			
			Bike.findInBackground(ParseUser.getCurrentUser(), new FindCallback<Bike>(){

				@Override
				public void done(List<Bike> bikes, ParseException e) {
					
					List<Bike> finalBikes = new ArrayList<Bike>();
					
					for(Bike b: bikes){
						
						if (!b.getStolen()){
							
							finalBikes.add(b);
							
						}
					}
					
					Resources res = getResources();
					BikeAdapter adapter = new BikeAdapter(getActivity(), R.layout.bike_spinner_item, finalBikes, res);
					spinner.setAdapter(adapter);
					
					
					
					
				}
				
				
				
				
				
				
			});
			
			
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
						
						Intent intent = new Intent(getActivity(), SearchMap.class );
						startActivity(intent);
						
						
						gl.setEnabled(true);
						editlat.setText("");
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
		
		


		
		if(input.isChecked()){
			
			
			StolenBike stolenBike = new StolenBike();
			stolenBike.put("lat", gs.selectedLat);
			stolenBike.put("lng", gs.selectedLng);
			//stolenBike.put("bikeId", );
			stolenBike.saveInBackground();
			
			
		}else{
	
		StolenBike stolenBike = new StolenBike();
		stolenBike.put("lat", gs.getCurrentLat());
		stolenBike.put("lng", gs.getCurrentLng());
		//stolenBike.put("bikeId", );
		stolenBike.saveInBackground();
	
		
		}
		
		
		
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

	@Override
	public void onResume() {
		
		super.onResume();
		
		
		if(gs.selectedLat!=null && gs.selectedLng!=null){
		
			editlat.setText((gs.selectedLat).toString());
			editlng.setText((gs.selectedLng).toString());
		
		
		}
		
		
		
		
	}
}

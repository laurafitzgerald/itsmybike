package wit.lf.itsmybike.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.itsmybike.R;

public class ProfileFragment extends Fragment {

	

	public static final String ARG_SECTION_NUMBER = "section_number";
	private TextView username;
	private TextView location;
	private ImageView profile;
	private GlobalState gs;
	private ListView bikeListView;
	private View rootView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		
		
		 

		if(rootView!=null){
			
			ViewGroup parent = (ViewGroup) rootView.getParent();
			if(parent != null){
				
				parent.removeView(rootView);
			}
		}
		try{
			rootView = inflater.inflate(R.layout.activity_profile_one, container, false);
		}catch(InflateException e){
			
			
			
		}
		
		return rootView;
	}
	
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
	
		super.onViewCreated(view, savedInstanceState);
		
		gs = (GlobalState) getActivity().getApplication();
		
		
		bikeListView = (ListView) getActivity().findViewById(R.id.bikeList);
		ListBikesAdapter adapter = new ListBikesAdapter(getActivity(), gs.getBikes());
		bikeListView.setAdapter(adapter);

		
		username = (TextView) getActivity().findViewById(R.id.usernameView);
		location = (TextView) getActivity().findViewById(R.id.locView);	
		profile = (ImageView) getActivity().findViewById(R.id.profileImageView);
		
		Log.v("usernamebox: ", username.toString());
		
		Log.v("profile: ", gs.getProfile().toString());
		Log.v("profile first name: ", gs.getProfile().getFirstName());
		Log.v("profile first name: ", gs.getProfile().getSecondName());
		Log.v("profile first name: ", gs.getProfile().getLocation());
		Log.v("nothing", location.toString());
		
		
		username.setText(gs.getProfile().getFirstName() + " " + gs.getProfile().getSecondName());
		
		location.setText(gs.getProfile().getLocation());
		
		
		profile.setBackgroundResource(gs.getProfile().getDrawableId());
		
		
		
	}

}

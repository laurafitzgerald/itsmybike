package wit.lf.itsmybike.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.itsmybike.R;

import java.util.List;

import wit.lf.itsmybike.data.Bike;

public class ProfileFragment extends Fragment {

	

	public static final String ARG_SECTION_NUMBER = "section_number";
	private TextView username;
	private TextView location;
	private ImageView profile;
	private GlobalState gs;
	private ListView bikeListView;
	private View rootView;
    private ImageView profileEditIcon;
    private ImageView plusIcon;
	
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
        List <Bike>listOfBikes;
        listOfBikes=gs.getProfile().getListOfBikes();
		bikeListView = (ListView) getActivity().findViewById(R.id.bikeList);
		ListBikesAdapterWithEdit adapter = new ListBikesAdapterWithEdit(getActivity(),listOfBikes);
		bikeListView.setAdapter(adapter);
        profileEditIcon =(ImageView)getActivity().findViewById(R.id.profileEditIcon);
        profileEditIcon.setBackgroundResource(R.drawable.edit);

		username = (TextView) getActivity().findViewById(R.id.usernameView);
		location = (TextView) getActivity().findViewById(R.id.locView);
		profile = (ImageView) getActivity().findViewById(R.id.profileImageView);
        plusIcon=(ImageView)getActivity().findViewById(R.id.plusIcon);
        plusIcon.setBackgroundResource(R.drawable.add);
		

		
		
		username.setText(gs.getProfile().getFirstName() + " " + gs.getProfile().getSecondName());
		
		location.setText(gs.getProfile().getLocation());
		

        if(gs.getProfile().getSelectedProfilePic()!=null)
        {
            profile.setBackgroundResource(0);
            profile.setImageBitmap(gs.getProfile().getSelectedProfilePic());
        }
        else {
            profile.setBackgroundResource(gs.getProfile().getDrawableId());
        }
		
		
		
	}



}

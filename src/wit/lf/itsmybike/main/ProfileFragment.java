package wit.lf.itsmybike.main;

import android.content.Intent;
import android.graphics.BitmapFactory;
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
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import wit.lf.itsmybike.data.Bike;
import wit.lf.itsmybike.data.Profile;

public class ProfileFragment extends Fragment {

	

	public static final String ARG_SECTION_NUMBER = "section_number";
	private TextView username;
	private TextView location;
	private ImageView profilePic;
	private GlobalState gs;
	private ListView bikeListView;
	private View rootView;
    private ImageView profileEditIcon;
    private ImageView plusIcon;
    private String serialNumber;



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
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

        profileEditIcon = (ImageView) getActivity().findViewById(R.id.profileEditIcon);
        profileEditIcon.setBackgroundResource(R.drawable.edit);
        username = (TextView) getActivity().findViewById(R.id.usernameView);
        location = (TextView) getActivity().findViewById(R.id.locView);
        profilePic = (ImageView) getActivity().findViewById(R.id.profileImageView);
        plusIcon = (ImageView) getActivity().findViewById(R.id.plusIcon);
        plusIcon.setBackgroundResource(R.drawable.add);
        gs = (GlobalState) getActivity().getApplication();
        
        Bike.findInBackground(ParseUser.getCurrentUser(), new FindCallback<Bike>() {
            @Override
            public void done(List<Bike> bikes, ParseException e) {

                if (e==null)
                {
                    ListBikesAdapterWithEdit adapter = new ListBikesAdapterWithEdit(getActivity(), bikes,gs);
                    for (Bike b:bikes)
                    {
                        try {
                            ParseFile pf = (ParseFile) b.get("bikePic");
                            serialNumber = b.getSerialNo();
                            pf.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] bytes, ParseException e) {

                                    gs.saveBikePicLocally(bytes, serialNumber);

                                }
                            });
                        }
                        catch
                        (NullPointerException ex)
                        {
                            ex.printStackTrace();
                        }

                    }
                    bikeListView = (ListView) getActivity().findViewById(R.id.bikeList);
                    gs.setListOfBikes(bikes);

                    bikeListView.setAdapter(adapter);


                }

                else
                {

                }


            }
        });
        Profile userProfile = (Profile) ParseUser.getCurrentUser();
        username.setText(userProfile.getFirstName() + " " + userProfile.getSecondName());
        location.setText(userProfile.getLocation());
        getProfilePicAsBitmap();


    }

    public void getProfilePicAsBitmap() {


        byte[] data=gs.readLocalProfilePic();
        profilePic.setImageBitmap(BitmapFactory.decodeByteArray(data, 0, data.length));

    }

	public void editProfile(View view)
    {
        startActivity(new Intent(getActivity(),EditProfile.class));

    }


}

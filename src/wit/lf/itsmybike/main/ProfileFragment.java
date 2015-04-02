package wit.lf.itsmybike.main;

import java.io.File;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.text.SimpleDateFormat;




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
	
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
	private static final int RESULT_CANCELED = 0;
	private static final int RESULT_OK = 1;
	private Uri fileUri;
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;
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
		

	
		profile.setOnLongClickListener(new OnLongClickListener(){

			@Override
			public boolean onLongClick(View v) {
				   	Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				   
				   	//fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
				    //intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

				    // start the image capture Intent
				    startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
				   
				return true;
			}
			
			
			
			
			
		});
		
		Log.v("usernamebox: ", username.toString());
		
		Log.v("profile: ", gs.getProfile().toString());
		Log.v("profile first name: ", gs.getProfile().getFirstName());
		Log.v("profile first name: ", gs.getProfile().getSecondName());
		Log.v("profile first name: ", gs.getProfile().getLocation());
		Log.v("nothing", location.toString());

		
		
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
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
	        if (resultCode == RESULT_OK) {
	            // Image captured and saved to fileUri specified in the Intent
	            Toast.makeText(getActivity(), "Image saved to:\n" +
	                     data.getData(), Toast.LENGTH_LONG).show();
	            //profile.setBackgroundResource(data.getData());
	        } else if (resultCode == RESULT_CANCELED) {
	            // User cancelled the image capture
	        } else {
	            // Image capture failed, advise user
	        }
	    }

	    if (requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE) {
	        if (resultCode == RESULT_OK) {
	            // Video captured and saved to fileUri specified in the Intent
	            Toast.makeText(getActivity(), "Can only accept a picture", Toast.LENGTH_LONG).show();
	        } else if (resultCode == RESULT_CANCELED) {
	            // User cancelled the video capture
	        } else {
	            // Video capture failed, advise user
	        }
	    }
		
		
	}
	
	private static File getOutputMediaFile(int type){
	    // To be safe, you should check that the SDCard is mounted
	    // using Environment.getExternalStorageState() before doing this.

	    File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
	              Environment.DIRECTORY_PICTURES), "MyCameraApp");
	    // This location works best if you want the created images to be shared
	    // between applications and persist after your app has been uninstalled.

	    // Create the storage directory if it does not exist
	    if (! mediaStorageDir.exists()){
	        if (! mediaStorageDir.mkdirs()){
	            Log.d("MyCameraApp", "failed to create directory");
	            return null;
	        }
	    }
	    
	 // Create a media file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    File mediaFile;
	    if (type == MEDIA_TYPE_IMAGE){
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	        "IMG_"+ timeStamp + ".jpg");
	    } else if(type == MEDIA_TYPE_VIDEO) {
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	        "VID_"+ timeStamp + ".mp4");
	    } else {
	        return null;
	    }

	    return mediaFile;
	    
	    
	}
	    
	    private static Uri getOutputMediaFileUri(int type){
	        return Uri.fromFile(getOutputMediaFile(type));
	  }



}

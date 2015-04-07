package wit.lf.itsmybike.main;



import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import com.example.itsmybike.R;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;

import wit.lf.itsmybike.data.Bike;
import wit.lf.itsmybike.data.Profile;
import wit.lf.itsmybike.data.StolenBike;

public class GlobalState extends Application{

	
	
	public void onCreate(){
		
		
		Parse.enableLocalDatastore(this);
		 
		Parse.initialize(this, getString(com.example.itsmybike.R.string.parse_application_id) , getString(R.string.parse_client_key));
		
		ParseObject.registerSubclass(StolenBike.class);
		ParseObject.registerSubclass(Profile.class);
		ParseObject.registerSubclass(Bike.class);
		
		ParseInstallation.getCurrentInstallation().saveInBackground();
		

		
		
	}
	
	
	private List <Bike> listOfBikes = new ArrayList<Bike>();
	

	private ArrayList <StolenBike> stolenBikes = new ArrayList<StolenBike>();
	private Profile profile;
	//private ArrayList<Bike> bikes = new ArrayList<Bike>();
	private boolean loggedIn;
	private Double currentLat;
	private Double currentLng;
    private List<Profile> listOfProfiles=new ArrayList<Profile>();
    private Bike bikeToEdit;
    public List<Profile> getListOfProfiles() {
        return listOfProfiles;
    }
	public Double getCurrentLat() {
		return currentLat;
	}
	public void setCurrentLat(Double currentLat) {
		this.currentLat = currentLat;
	}
	public Double getCurrentLng() {
		return currentLng;
	}
	public void setCurrentLng(Double currentLng) {
		this.currentLng = currentLng;
	}
	public boolean isLoggedIn() {
		return loggedIn;
	}
	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public List<Bike> getListOfBikes() {
		return listOfBikes;
	}
	public void setListOfBikes(List<Bike> listOfBikes) {
		this.listOfBikes = listOfBikes;
	}

	/*public ArrayList<Bike> getBikes() {
		return bikes;
	}*/

	/*public void setBikes(ArrayList<Bike> bikes) {
		this.bikes = bikes;
	}*/

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public ArrayList<StolenBike> getStolenBikes() {
		return stolenBikes;
	}

	public void setStolenBikes(ArrayList<StolenBike> stolenBikes) {
		this.stolenBikes = stolenBikes;
	}


    public Bike getBikeToEdit() {
        return bikeToEdit;
    }

    public void setBikeToEdit(Bike bikeToEdit) {
        this.bikeToEdit = bikeToEdit;
    }


	
	
	
}

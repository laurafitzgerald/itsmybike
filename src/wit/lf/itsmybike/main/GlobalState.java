package wit.lf.itsmybike.main;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import wit.lf.itsmybike.data.Bike;
import wit.lf.itsmybike.data.Profile;
import wit.lf.itsmybike.data.StolenBike;

public class GlobalState extends Application{

	
	
	
	
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

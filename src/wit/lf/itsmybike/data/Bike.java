package wit.lf.itsmybike.data;

//here are some changes

import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import wit.lf.itsmybike.main.GlobalState;


@ParseClassName("Bike")
public class Bike extends ParseObject{

	private int drawableId;

    
    

	
	private static ParseQuery<Bike> createQuery() {
		ParseQuery<Bike> query = new ParseQuery<Bike>(Bike.class);
	/*	query.include("userId");
		query.include("lng");
		query.include("serialNumber");
		query.include("createdAt");*/
		//query.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);
		return query;
	}
	
	public static void findInBackground(ParseUser user, final FindCallback<Bike> callback){
		
		ParseQuery<Bike> query = Bike.createQuery();
        
		
		
		//query.fromLocalDatastore();
		
		if(user != null){
			
				query.whereEqualTo("userId", user);
			
		}
		
		query.findInBackground(new FindCallback<Bike>(){
			
			@Override
			public void done(List<Bike> bikes, ParseException e) {
				
				callback.done(bikes, e);
			}
			
		});
		
		
	}
	
	


	

    public Bike(){}



    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }public int getDrawableId() {
		return drawableId;
	}



    public Boolean getStolen(){
    	
    	return getBoolean("registeredStolen");
    	
    }

	public String getMake() {
		return getString("make");
	}


	public String getSerialNo() {
		return getString("serialNumber");
	}










	public String getNickname() {
		return getString("nickname");
	}




}

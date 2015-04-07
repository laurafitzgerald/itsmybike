package wit.lf.itsmybike.data;

//here are some changes

import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.graphics.Bitmap;


@ParseClassName("Bike")
public class Bike extends ParseObject{

	private int drawableId;
/*
	private String serialNo;
	
	private String nickname;
	private String make;
	private boolean stolen;
*/


    private Bitmap selectedBikePic;
	
    
    
    

	
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
	
	public static void findInBackgroudBySerial(String serialNumber, final FindCallback<Bike> callback){
		
		ParseQuery<Bike> query = Bike.createQuery();
		
		query.whereEqualTo("serialNumber", serialNumber);
		
		query.findInBackground(new FindCallback<Bike>()  {

			@Override
			public void done(List<Bike> bikes, ParseException e) {
				callback.done(bikes, e);;
				
			}

		});
		
		
	}
	

    public Bike(){}
	
	

	public int getDrawableId() {
		return drawableId;
	}


	public void setDrawableId(int drawableId) {
		this.drawableId = drawableId;
	}


	public String getMake() {
		return getString("make");
	}


	public String getSerialNo() {
		return getString("serialNumber");
	}


	


    public Bitmap getSelectedBikePic() {
        return selectedBikePic;
    }

    public void setSelectedBikePic(Bitmap selectedBikePic) {
        this.selectedBikePic = selectedBikePic;
    }


	public String getNickname() {
		return getString("nickname");
	}




}

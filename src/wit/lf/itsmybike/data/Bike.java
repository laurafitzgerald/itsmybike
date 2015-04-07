package wit.lf.itsmybike.data;

//here are some changes

import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.graphics.Bitmap;


@ParseClassName("Bike")
public class Bike extends ParseObject{

	private int drawableId;

	private String serialNo;
	
	private String nickname;
	private String make;
	private boolean stolen;



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
	
	public static void findInBackground(Profile profile, final FindCallback<Bike> callback){
		
		ParseQuery<Bike> query = Bike.createQuery();
		
		if(profile != null){
			
				query.whereEqualTo("userId", profile.getObjectId());
			
		}
		
		query.findInBackground(new FindCallback<Bike>(){
			
			@Override
			public void done(List<Bike> bikes, ParseException e) {
				
				callback.done(bikes, e);
			}});
		
		
	}
	


/*	public Bike(int drawableId, String nickname, String serialNo, String make ){
		
		this.drawableId = drawableId;
		this.serialNo = serialNo;
		
		this.nickname = nickname;
		this.make = make;
		this.stolen = false;
		
		
	}*/

    public Bike(){}
	
	
	public boolean isStolen() {
		return stolen;
	}

	public void setStolen(boolean stolen) {
		this.stolen = stolen;
	}

	public int getDrawableId() {
		return drawableId;
	}


	public void setDrawableId(int drawableId) {
		this.drawableId = drawableId;
	}


	public String getMake() {
		return make;
	}


	public void setMake(String make) {
		this.make = make;
	}


	public String getSerialNo() {
		return serialNo;
	}


	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}


    public Bitmap getSelectedBikePic() {
        return selectedBikePic;
    }

    public void setSelectedBikePic(Bitmap selectedBikePic) {
        this.selectedBikePic = selectedBikePic;
    }


	public String getNickname() {
		return nickname;
	}


	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}

package wit.lf.itsmybike.data;

import java.util.Date;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQuery.CachePolicy;




@ParseClassName("StolenBike")
public class StolenBike extends ParseObject {

	
	
	public StolenBike(){

	}
	
	
	private static ParseQuery<StolenBike> createQuery() {
		ParseQuery<StolenBike> query = new ParseQuery<StolenBike>(StolenBike.class);
	/*	query.include("lat");
		query.include("lng");
		query.include("serialNumber");
		query.include("createdAt");*/
		//query.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);
		return query;
	}
	
	
	//getallStolenBikeRecords
	public static void findInBackground(final FindCallback<StolenBike> callback){
		
		ParseQuery<StolenBike> query = StolenBike.createQuery();
		
		query.findInBackground(new FindCallback<StolenBike>(){
			
			@Override
			public void done(List<StolenBike> objects, ParseException e) {
				
				callback.done(objects, e);
			}});
		
		
	}
	
	

	public String getSerialNumber() {
		return getString("serialNumber");
	}


	public Date getDate() {
		return getDate("date");
	}



	public double getLat(){
		
		
		return getDouble("lat");
	}



	public double getLng() {
		return getDouble("lng");
	}

	
}

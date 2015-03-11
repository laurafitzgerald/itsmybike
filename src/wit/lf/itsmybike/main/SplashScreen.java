/**
 * 
 */
package wit.lf.itsmybike.main;

import wit.lf.itsmybike.data.Profile;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.itsmybike.R;
import com.parse.Parse;
import com.parse.ParseObject;


	public class SplashScreen extends Activity {


		private GlobalState gs;
		

		private final int SPLASH_DISPLAY_LENGTH = 1000;
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.acitivity_splashscreen);
			
			Parse.enableLocalDatastore(this);
			 
			Parse.initialize(this, "o6Yb47a9oYQCRb2Lv1MrdFA0E8Op4gIOIR6cKK37", "3I3oXpk4U5O8dfhNvlXwblr0lDJppuk0lVxCINIA");

			ParseObject testObject = new ParseObject("TestObject");
			testObject.put("foo", "bar");
			testObject.saveInBackground();
			
			gs = (GlobalState) getApplication();
			gs.setProfile(new Profile("Laura", "Fitzgerald", "Waterford", 2, R.drawable.bikeprofile, "laura@laura.com", "pass"));
			gs.setLoggedIn(true);
			
		
		
			new Handler().postDelayed(new Runnable(){

				@Override
				public void run() {
					
					Intent mainIntent;
					if(gs.isLoggedIn()){
						
						Toast.makeText(getApplicationContext(), "Logging In...", Toast.LENGTH_SHORT).show();
						mainIntent = new Intent(SplashScreen.this, Base.class);
						
					}else{
						 mainIntent= new Intent(SplashScreen.this, LogInScreen.class);
						
					}
					
					startActivity(mainIntent);
					finish();
					
				}
				}, SPLASH_DISPLAY_LENGTH);
				
				
				
				
				
			
		}

		
	
	
	
}

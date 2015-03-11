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


	public class SplashScreen extends Activity {


		private GlobalState gs;
		

		private final int SPLASH_DISPLAY_LENGTH = 1000;
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.acitivity_splashscreen);
			
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

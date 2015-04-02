package wit.lf.itsmybike.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.example.itsmybike.R;

import java.util.ArrayList;

import wit.lf.itsmybike.data.Bike;
import wit.lf.itsmybike.data.Profile;


public class LogInScreen extends Activity {



	private Button registerButton;
	private Button login;
	private GlobalState gs;

	private EditText usernameInput = null;

	private EditText passwordInput = null;
	
	private TextView attempts;
	int counter = 3;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log_in);

		gs = (GlobalState) getApplication();
        gs.getListOfProfiles().add(new Profile("John","Hodmon", "johnhodmon@gmail.com","pass","Waterford",R.drawable.profile_pic_john,new ArrayList<Bike>()));
		Log.v("profiles", "Count: " + gs.getListOfProfiles().size());

		
		registerButton = (Button) findViewById(R.id.editProfilePassword);
		login = (Button) findViewById(R.id.logInButton);
		attempts = (TextView) findViewById(R.id.attempts);
		attempts.setText("3 attempts left");
		attempts.setTextColor(getResources().getColor(R.color.textbody));

		usernameInput = (EditText) findViewById(R.id.usernameInput);
	
		passwordInput = (EditText) findViewById(R.id.passwordInput);
				passwordInput.setOnEditorActionListener(new OnEditorActionListener() {
				    @Override
				    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				        boolean handled = false;
				        if (actionId == EditorInfo.IME_ACTION_SEND) {
				       
				        	login.performClick();
				          
				        }
				        return handled;
				    }

				});



        usernameInput.setText("johnhodmon@gmail.com");
        passwordInput.setText("pass");
        login.performClick();




	}

	

	public void logInButtonPressed(View view){
		
		Boolean profileFound=false;
        Profile profileToUse=new Profile();

        for (Profile p: gs.getListOfProfiles() )
        {


            if(p.getEmail().equals(usernameInput.getText().toString())&&p.getPassword().equals(passwordInput.getText().toString()))
            {
                profileFound=true;
                profileToUse=p;

            }
        }

        Log.v("profiles","true or not:" +profileFound);

        if (profileFound)

			
        {

            Toast.makeText(getApplicationContext(), "Logging In...", Toast.LENGTH_SHORT).show();
			gs.setProfile(profileToUse);
            gs.setLoggedIn(true);
            Bike bikeOne = new Bike( R.drawable.bike_one, "Big Bertha", "HN5879556B", "High Nelly");
            Bike bikeTwo = new Bike(R.drawable.bike_two, "Mountain Goat", "MB8874113A", "Raleigh");
            gs.getProfile().getListOfBikes().add(bikeOne);
            gs.getProfile().getListOfBikes().add(bikeTwo);
			Intent intent = new Intent(this, Base.class);
			startActivity(intent);
			
		}
		
		else{
			
			Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
			attempts.setBackgroundColor(getResources().getColor(R.color.locationcolor));
			counter--;
			attempts.setText( Integer.toString(counter) +" attempts left");
			attempts.setTextColor(getResources().getColor(R.color.textbody));
			if(counter==0){
				
				
				login.setEnabled(false);
			}
			
			
		}
		
		
	}

    public void registerButtonPressed(View view)
    {
        Log.v("Reg","Button Pressed");
        startActivity(new Intent(this, Register.class));
    }


	
	
	

}

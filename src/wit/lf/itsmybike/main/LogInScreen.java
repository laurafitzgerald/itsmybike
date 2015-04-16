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
import com.parse.GetDataCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;

import wit.lf.itsmybike.data.Profile;


public class LogInScreen extends Activity {



	private Button registerButton;
	private Button login;
	private GlobalState gs;
	private Boolean found = false;
	private EditText usernameInput = null;
	private EditText passwordInput = null;
	private TextView attempts;
    private Profile user;
	int counter = 3;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_log_in);

            gs = (GlobalState) getApplication();

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

            user = new Profile();

        }

        catch (Exception ex)
        {
            ex.printStackTrace();
        }

	}

	

	public void logInButtonPressed(View view){
		
		
try {

    if (gs.connectedToInternet(this) == false) {
        Toast.makeText(this, "Check your internet connection and try again", Toast.LENGTH_LONG).show();
    } else {


        Profile.logInInBackground(usernameInput.getText().toString(), passwordInput.getText().toString(), new LogInCallback() {

            @Override
            public void done(ParseUser user, ParseException e) {
                if (user != null) {


                    ParseFile parseFileWithProfilePic = (ParseFile) user.get("profilePic");
                    parseFileWithProfilePic.getDataInBackground(new GetDataCallback() {
                        @Override
                        public void done(byte[] bytes, ParseException e) {
                            if (e == null) {

                                gs.saveProfilePicLocally(bytes);
                                gs.populateLocalBikeList();
                                Toast.makeText(getApplicationContext(), "Logging In...", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LogInScreen.this, Base.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(LogInScreen.this, "oops", Toast.LENGTH_LONG).show();
                            }

                        }
                    });


                } else {

                    failedAttempt();

                }

            }


            private void failedAttempt() {


                Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
                attempts.setBackgroundColor(getResources().getColor(R.color.locationcolor));
                counter--;
                attempts.setText(Integer.toString(counter) + " attempts left");
                attempts.setTextColor(getResources().getColor(R.color.textbody));
                found = false;
                if (counter == 0) {


                    login.setEnabled(false);
                }
                return;

            }


        });


    }

}

catch (Exception ex)
{
    ex.printStackTrace();
}

		
	}

    public void registerButtonPressed(View view)
    {
        Log.v("Reg","Button Pressed");
        startActivity(new Intent(this, Register.class));
    }


	
	
	

}

package wit.lf.itsmybike.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.example.itsmybike.R;


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

		
		
		registerButton = (Button) findViewById(R.id.registerButton);
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
				
				
				
				
	}

	

	public void logInButtonPressed(View view){
		
		
				if(usernameInput.getText().toString().equals(gs.getProfile().getEmail())
				&& passwordInput.getText().toString().equals(gs.getProfile().getPassword())){
			
			Toast.makeText(getApplicationContext(), "Logging In...", Toast.LENGTH_SHORT).show();
			
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

	
	
	
	

}

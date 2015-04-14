package wit.lf.itsmybike.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.itsmybike.R;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.io.ByteArrayOutputStream;

import wit.lf.itsmybike.data.Profile;

/**
 * Created by john on 31/03/2015.
 */
public class Register extends Activity {



    private EditText firstName;
    private EditText surname;
    private EditText location;
    private String galleryFilePath;
    private Bitmap scaledBitmap;
    private EditText email;
    private EditText password;
    private EditText retypePassword;
    private Button registerButton;
    private ImageView addProfilePic;
    private ImageView addIconProfilePic;
    private GlobalState gs;
    private ParseFile fileContainingProfilePic;
    private Profile user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
<<<<<<< HEAD
        try {
            super.onCreate(savedInstanceState);
            scaledBitmap = BitmapFactory.decodeResource(getResources(),
                    R.drawable.no_profile_pic);
            setContentView(R.layout.activity_register);
            firstName = (EditText) findViewById(R.id.registerFirstName);
            surname = (EditText) findViewById(R.id.registerSurname);
            location = (EditText) findViewById(R.id.registerLocation);
            addProfilePic = (ImageView) findViewById(R.id.addProfilePic);
            addIconProfilePic = (ImageView) findViewById(R.id.addIconAddProfilePic);
            addProfilePic.setImageBitmap(scaledBitmap);
            addIconProfilePic.setBackgroundResource(R.drawable.add);
            email = (EditText) findViewById(R.id.registerEmail);
            password = (EditText) findViewById(R.id.registerPassword);
            retypePassword = (EditText) findViewById(R.id.retypePassword);
            registerButton = (Button) findViewById(R.id.registerButton);
            gs = (GlobalState) getApplication();
            firstName.requestFocus();
            user = new Profile();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
=======
        super.onCreate(savedInstanceState);
        scaledBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.no_profile_pic);
        setContentView(R.layout.activity_register);

        gs= (GlobalState) getApplication();
        firstName=(EditText)findViewById(R.id.registerFirstName);
        surname=(EditText)findViewById(R.id.registerSurname);
        location=(EditText)findViewById(R.id.registerLocation);
        addProfilePic=(ImageView)findViewById(R.id.addProfilePic);
        addIconProfilePic=(ImageView)findViewById(R.id.addIconAddProfilePic);
        addProfilePic.setBackgroundResource(R.drawable.no_profile_pic);
        addIconProfilePic.setBackgroundResource(R.drawable.add);
        email=(EditText)findViewById(R.id.registerEmail);
        password=(EditText)findViewById(R.id.registerPassword);
        retypePassword=(EditText)findViewById(R.id.retypePassword);
        registerButton =(Button)findViewById(R.id.registerButton);
      

        firstName.requestFocus();
        //prepareProfilePicForSaving();
        user = new Profile();
>>>>>>> 61f8e2fc324ba6be535550f16874ba39dfc03d84
    }


    public void addProfilePic(View view) {

        try {


            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getResources().getString(R.string.selectPhotoMethod));
            builder.setPositiveButton(getResources().getString(R.string.browse), new DialogInterface.OnClickListener() {


                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Intent openGallery = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(openGallery, 1);
                }
            });

            builder.setNegativeButton(getResources().getString(R.string.takePhoto), new DialogInterface.OnClickListener() {


                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, 2);
                    }
                }
            });


            AlertDialog dialog = builder.create();
            dialog.show();
        }

            catch (Exception ex)
            {
                ex.printStackTrace();

            }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == 1 && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                galleryFilePath = cursor.getString(columnIndex);

                Bitmap unscaledBitmap = BitmapFactory.decodeFile(galleryFilePath);

                int widthBeforeScale = unscaledBitmap.getWidth();
                int heightBeforeScale = unscaledBitmap.getHeight();
                int profilePicWidth = addProfilePic.getWidth();
                int profilePicHeight = addProfilePic.getHeight();
                int new_width = profilePicWidth;
                int new_height = profilePicHeight;

                scaledBitmap = Bitmap.createScaledBitmap(unscaledBitmap, new_width, new_height, true);

                addProfilePic.setBackgroundResource(0);
                addProfilePic.setImageBitmap(scaledBitmap);

               // prepareProfilePicForSaving();


            } else if ((requestCode == 2 && resultCode == RESULT_OK
                    && null != data)) {
                Bundle extras = data.getExtras();
                Bitmap unscaledBitmap = (Bitmap) extras.get("data");
                int widthBeforeScale = unscaledBitmap.getWidth();
                int heightBeforeScale = unscaledBitmap.getHeight();
                int profilePicWidth = addProfilePic.getWidth();
                int profilePicHeight = addProfilePic.getHeight();
                int new_width = profilePicWidth;
                int new_height = profilePicHeight;
                scaledBitmap = Bitmap.createScaledBitmap(unscaledBitmap, new_width, new_height, true);
                addProfilePic.setBackgroundResource(0);
                addProfilePic.setImageBitmap(scaledBitmap);
               // prepareProfilePicForSaving();
            } else {
                Toast.makeText(this, "You haven't picked an image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }

    public void registerUser(View view) {

        try {


<<<<<<< HEAD
            if (!password.getText().toString().equals(retypePassword.getText().toString())) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show();
                password.setText("");
                retypePassword.setText("");
                password.requestFocus();
            }

           else if (gs.connectedToInternet(Register.this)==false)
            {
                Toast.makeText(Register.this,"Check your internet connection and try again",Toast.LENGTH_LONG).show();
            }
            else {


                user.setPassword(password.getText().toString());
                user.setEmail(email.getText().toString());
                user.put("firstName", firstName.getText().toString());
                user.put("surName", surname.getText().toString());
                user.put("location", location.getText().toString());
                user.setUsername(email.getText().toString());






                user.pinInBackground();

                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (!(e == null))
                        {

                          Toast.makeText(Register.this,"Unable to login at this time",Toast.LENGTH_LONG);
                        }

                        else {
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                            byte[] byteArray = stream.toByteArray();
                            gs.saveProfilePicLocally(byteArray);
                            gs.saveProfilePicToParse(byteArray);
                            Toast.makeText(Register.this, "Welcome " + firstName.getText().toString(), Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(Register.this, Base.class));


                        }
                    }
                });
            }
        }

        catch (Exception ex)
        {
            ex.printStackTrace();
=======
        //Profile newUser=new Profile();
        Pattern p2 = Pattern.compile("^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$");
        Matcher m2 = p2.matcher(email.getText().toString());
   
      
        if(!password.getText().toString().equals(retypePassword.getText().toString()))
        {
            Toast.makeText(this,"Passwords do not match",Toast.LENGTH_LONG).show();
            password.setText("");
            retypePassword.setText("");
            password.requestFocus();
>>>>>>> 61f8e2fc324ba6be535550f16874ba39dfc03d84
        }
        
    	
		
        
		
        ParseUser user = (Profile) new Profile();
        user.setPassword(password.getText().toString());
        user.setEmail(email.getText().toString());
        user.put("firstName", firstName.getText().toString());
        user.put("surName", surname.getText().toString());
        user.put("location", location.getText().toString());
        user.setUsername(email.getText().toString());
        user.pinInBackground();
        
        user.signUpInBackground(new SignUpCallback() {

			@Override
			public void done(ParseException e) {
				if(!(e==null)){
					
					Log.v("Something went wrong!!" + e, e.toString());
					
					
				}else{
					
					
					  Toast.makeText(getApplicationContext(),"Welcome "+firstName.getText().toString(),Toast.LENGTH_SHORT).show();
				        startActivity(new Intent(Register.this, Base.class));
				}
				
			}
        	
        	
        	
        	
        	
        });
        
      
        
    }
<<<<<<< HEAD

=======
    
>>>>>>> 61f8e2fc324ba6be535550f16874ba39dfc03d84
}



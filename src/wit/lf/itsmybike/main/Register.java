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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.itsmybike.R;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import wit.lf.itsmybike.data.Bike;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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
        gs=(GlobalState)getApplication();
        firstName.requestFocus();
    }


  public void addProfilePic(View view)
  {



      AlertDialog.Builder builder = new AlertDialog.Builder(this);
      builder.setMessage(getResources().getString(R.string.selectPhotoMethod));
      builder.setPositiveButton(getResources().getString(R.string.browse), new DialogInterface.OnClickListener() {


          @Override
          public void onClick(DialogInterface dialog, int which) {

              Intent openGallery = new Intent(Intent.ACTION_PICK,
                      android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
              startActivityForResult(openGallery,1);
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



      AlertDialog dialog=builder.create();
      dialog.show();
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

                Bitmap unscaledBitmap=BitmapFactory.decodeFile(galleryFilePath);

                int widthBeforeScale=unscaledBitmap.getWidth();
                int heightBeforeScale =unscaledBitmap.getHeight();
                int profilePicWidth=addProfilePic.getWidth();
                int profilePicHeight=addProfilePic.getHeight();
                int new_width=profilePicWidth;
                int new_height=profilePicHeight;

                scaledBitmap = Bitmap.createScaledBitmap(unscaledBitmap,new_width,new_height, true);

                addProfilePic.setBackgroundResource(0);
                addProfilePic.setImageBitmap(scaledBitmap);





            }

            else if((requestCode == 2 && resultCode == RESULT_OK
                    && null != data))
            {
                Bundle extras = data.getExtras();
                Bitmap unscaledBitmap = (Bitmap) extras.get("data");



                int widthBeforeScale=unscaledBitmap.getWidth();
                int heightBeforeScale =unscaledBitmap.getHeight();
                int profilePicWidth=addProfilePic.getWidth();
                int profilePicHeight=addProfilePic.getHeight();
                int new_width=profilePicWidth;
                int new_height=profilePicHeight;
                scaledBitmap = Bitmap.createScaledBitmap(unscaledBitmap,new_width,new_height, true);

                addProfilePic.setBackgroundResource(0);
                addProfilePic.setImageBitmap(scaledBitmap);
            }


            else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }

    public void registerUser(View view)
    {
        Log.v("Reg","Button Pressed");
        Profile newUser=new Profile();
        Pattern p2=Pattern.compile("^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$");
        Matcher m2=p2.matcher(email.getText().toString());
        boolean userExists=false;

        for(Profile pr :gs.getListOfProfiles())
        {
            if (pr.getEmail().equals(email.getText().toString()))
            {
                userExists=true;
            }
        }

        if(!password.getText().toString().equals(retypePassword.getText().toString()))
        {
            Toast.makeText(this,"Passwords do not match",Toast.LENGTH_LONG).show();
            password.setText("");
            retypePassword.setText("");
            password.requestFocus();
        }

    else if     (firstName.getText().toString().equals(""))
        {
            Toast.makeText(this,"Please Enter First Name",Toast.LENGTH_SHORT).show();
            firstName.requestFocus();
        }
        else if     (surname.getText().toString().equals(""))
        {
            Toast.makeText(this,"Please Enter Surname",Toast.LENGTH_SHORT).show();
            surname.requestFocus();
        }
        else if     (location.getText().toString().equals(""))
        {
            Toast.makeText(this,"Please Enter Location",Toast.LENGTH_SHORT).show();
            location.requestFocus();
        }

        else if     (password.getText().toString().equals(""))
        {
            Toast.makeText(this,"Please Enter Password",Toast.LENGTH_SHORT).show();
            password.requestFocus();
        }



        else if(!m2.find())
        {
            Toast.makeText(this,"email Format should be like example@example.com:",Toast.LENGTH_SHORT).show();
            email.setText("");
            email.requestFocus();
        }

        else if(userExists)
        {
            Toast.makeText(this,"User already exists",Toast.LENGTH_SHORT).show();
        }

        else
        {
            newUser.setFirstName(firstName.getText().toString());
            newUser.setSecondName(surname.getText().toString());
            newUser.setEmail(email.getText().toString());
            newUser.setPassword(password.getText().toString());
            newUser.setLocation(location.getText().toString());
            newUser.setListOfBikes(new ArrayList<Bike>());
            newUser.setDrawableId(R.drawable.no_profile_pic);
            if(scaledBitmap!=null) {
                newUser.setSelectedProfilePic(scaledBitmap);
            }
            gs.getListOfProfiles().add(newUser);
            gs.setProfile(newUser);
            gs.setLoggedIn(true);
            Toast.makeText(this,"Welcome "+newUser.getFirstName(),Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Base.class));


        }




    }
}

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
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

import wit.lf.itsmybike.data.Profile;

public class EditProfile extends Activity {

    private EditText editProfileFirstName;
    private EditText editProfileSurname;
    private EditText editProfileLocation;
    private Button editProfileSaveButton;
    private Button changePasswordButton;
    private EditText newPassword;
    private EditText retypeNewPassword;
    private GlobalState gs;
    private Boolean passwordBeingChanged;
    private ImageView editProfilePic;
    private ImageView editProfilePicEditIcon;
    private String galleryFilePath;
    private Bitmap scaledBitmap;
    private Profile userProfile;
    private int height;
    private int width;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            galleryFilePath = "";
            setContentView(R.layout.activity_edit_profile);
            gs = (GlobalState) getApplication();
            editProfileFirstName = (EditText) findViewById(R.id.editProfileFirstName);
            editProfileSurname = (EditText) findViewById(R.id.editProfileSurname);
            editProfileLocation = (EditText) findViewById(R.id.editProfileLocation);
            editProfileSaveButton = (Button) findViewById(R.id.editProfileSaveButton);
            editProfilePicEditIcon = (ImageView) findViewById(R.id.editProfilePicEditIcon);
            editProfilePic = (ImageView) findViewById(R.id.editProfilePic);

             userProfile = (Profile) ParseUser.getCurrentUser();

            editProfileFirstName.setText(userProfile.getFirstName());
            editProfileSurname.setText(userProfile.getSecondName());
            editProfileLocation.setText(userProfile.getLocation());
            Bitmap bmapForSize=BitmapFactory.decodeResource(getResources(), R.drawable.no_profile_pic);
            height=bmapForSize.getHeight();
            width=bmapForSize.getWidth();
            getProfilePicAsBitmap();
            editProfilePicEditIcon.setBackgroundResource(R.drawable.edit);
            newPassword = (EditText) findViewById(R.id.newPassword);
            changePasswordButton = (Button) findViewById(R.id.editPasswordButton);
            retypeNewPassword = (EditText) findViewById(R.id.retypeNewPassword);
            newPassword.setVisibility(View.INVISIBLE);
            retypeNewPassword.setVisibility(View.INVISIBLE);
            passwordBeingChanged = false;
        }

        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    public void getProfilePicAsBitmap() {


        try {

            byte[] data = gs.readLocalProfilePic();
            scaledBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            editProfilePic.setImageBitmap(scaledBitmap);

        }


        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    public void changePassword(View view) {

        try {
            newPassword.setVisibility(View.VISIBLE);
            retypeNewPassword.setVisibility(View.VISIBLE);
            newPassword.requestFocus();
            changePasswordButton.setVisibility(View.INVISIBLE);
            passwordBeingChanged = true;
        }

        catch(Exception ex)
        {
             ex.printStackTrace();
        }


    }


    public void saveProfile(View view) {

        try {

            if (passwordBeingChanged) {


                if (editProfileFirstName.getText().toString().equals("")) {
                    Toast.makeText(this, "Please Enter First Name", Toast.LENGTH_SHORT).show();
                    editProfileFirstName.requestFocus();
                } else if (editProfileSurname.getText().toString().equals("")) {
                    Toast.makeText(this, "Please Enter Surname", Toast.LENGTH_SHORT).show();
                    editProfileSurname.requestFocus();
                } else if (editProfileLocation.getText().toString().equals("")) {
                    Toast.makeText(this, "Please Enter Location", Toast.LENGTH_SHORT).show();
                    editProfileLocation.requestFocus();
                } else if (newPassword.getText().toString().equals("")) {
                    Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                    newPassword.requestFocus();
                } else if (!newPassword.getText().toString().equals(retypeNewPassword.getText().toString())) {
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    newPassword.setText("");
                    retypeNewPassword.setText("");
                    newPassword.requestFocus();
                } else {


                    userProfile.put("firstName", editProfileFirstName.getText().toString());
                    userProfile.put("surName", editProfileSurname.getText().toString());
                    userProfile.put("location", editProfileLocation.getText().toString());
                     gs.saveProfilePicLocally(prepareProfilePicForSaving());
                    userProfile.setPassword(newPassword.getText().toString());
                    userProfile.saveEventually(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                            if (e == null) {
                                gs.saveProfilePicToParse(prepareProfilePicForSaving());
                            } else {
                                Toast.makeText(EditProfile.this, "Something went wrong", Toast.LENGTH_LONG).show();
                            }

                        }
                    });


                }
                Toast.makeText(EditProfile.this, "Profile Updated", Toast.LENGTH_LONG).show();
                Intent i=new Intent(this,Base.class);
                i.setAction("open profile");
                finish();
                startActivity(i);

            } else {

                if (editProfileFirstName.getText().toString().equals("")) {
                    Toast.makeText(this, "Please Enter First Name", Toast.LENGTH_SHORT).show();
                    editProfileFirstName.requestFocus();
                } else if (editProfileSurname.getText().toString().equals("")) {
                    Toast.makeText(this, "Please Enter Surname", Toast.LENGTH_SHORT).show();
                    editProfileSurname.requestFocus();
                } else if (editProfileLocation.getText().toString().equals("")) {
                    Toast.makeText(this, "Please Enter Location", Toast.LENGTH_SHORT).show();
                    editProfileLocation.requestFocus();
                } else {

                    userProfile.put("firstName", editProfileFirstName.getText().toString());
                    userProfile.put("surName", editProfileSurname.getText().toString());
                    userProfile.put("location", editProfileLocation.getText().toString());
                    gs.saveProfilePicLocally(prepareProfilePicForSaving());
                    userProfile.saveEventually(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                            if (e == null) {
                                gs.saveProfilePicToParse(prepareProfilePicForSaving());
                            } else {
                                Toast.makeText(EditProfile.this, "Something went wrong", Toast.LENGTH_LONG).show();
                            }

                        }
                    });

                    finish();
                    Intent i=new Intent(this,Base.class);
                    i.setAction("open profile");
                    finish();
                    startActivity(i);

                }

            }
        }

            catch(Exception ex)
            {

            }


    }

    public void editProfilePic(View view) {


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

    public byte[] prepareProfilePicForSaving()
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;


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
                scaledBitmap = Bitmap.createScaledBitmap(unscaledBitmap,width,height, true);

                         editProfilePic.setImageBitmap(scaledBitmap);







            }

            else if((requestCode == 2 && resultCode == RESULT_OK
                    && null != data))
            {
                Bundle extras = data.getExtras();
                Bitmap unscaledBitmap = (Bitmap) extras.get("data");
                scaledBitmap = Bitmap.createScaledBitmap(unscaledBitmap,width,height, true);
                editProfilePic.setBackgroundResource(0);
                editProfilePic.setImageBitmap(scaledBitmap);
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
}





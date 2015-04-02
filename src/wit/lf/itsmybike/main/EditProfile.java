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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        galleryFilePath = "";
        scaledBitmap=null;
        setContentView(R.layout.activity_edit_profile);
        gs = (GlobalState) getApplication();
        editProfileFirstName = (EditText) findViewById(R.id.editProfileFirstName);
        editProfileSurname = (EditText) findViewById(R.id.editProfileSurname);
        editProfileLocation = (EditText) findViewById(R.id.editProfileLocation);
        editProfileSaveButton = (Button) findViewById(R.id.editProfileSaveButton);
        editProfileFirstName.setText(gs.getProfile().getFirstName());
        editProfileSurname.setText(gs.getProfile().getSecondName());
        editProfileLocation.setText(gs.getProfile().getLocation());
        newPassword = (EditText) findViewById(R.id.newPassword);
        changePasswordButton = (Button) findViewById(R.id.editPasswordButton);
        retypeNewPassword = (EditText) findViewById(R.id.retypeNewPassword);
        newPassword.setVisibility(View.INVISIBLE);
        retypeNewPassword.setVisibility(View.INVISIBLE);
        passwordBeingChanged = false;
        editProfilePicEditIcon = (ImageView) findViewById(R.id.editProfilePicEditIcon);
        editProfilePic = (ImageView) findViewById(R.id.editProfilePic);
        if(gs.getProfile().getSelectedProfilePic()!=null)
        {
            editProfilePic.setBackgroundResource(0);
            editProfilePic.setImageBitmap(gs.getProfile().getSelectedProfilePic());
        }
        else {
            editProfilePic.setBackgroundResource(gs.getProfile().getDrawableId());
        }
        editProfilePicEditIcon.setBackgroundResource(R.drawable.edit);



    }


    public void changePassword(View view) {
        newPassword.setVisibility(View.VISIBLE);
        retypeNewPassword.setVisibility(View.VISIBLE);
        newPassword.requestFocus();
        changePasswordButton.setVisibility(View.INVISIBLE);
        passwordBeingChanged = true;


    }


    public void saveProfile(View view) {
        gs.getProfile().setFirstName(editProfileFirstName.getText().toString());
        gs.getProfile().setSecondName(editProfileSurname.getText().toString());
        gs.getProfile().setLocation(editProfileLocation.getText().toString());
        if(scaledBitmap!=null) {
            gs.getProfile().setSelectedProfilePic(scaledBitmap);
        }


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
                Toast.makeText(this, "Profile Updated", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, Base.class);
                startActivity(intent);
            }


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
                Toast.makeText(this, "Profile Updated", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, Base.class);
                startActivity(intent);
            }

        }

    }

    public void editProfilePic(View view) {




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
                int profilePicWidth=editProfilePic.getWidth();
                int profilePicHeight=editProfilePic.getHeight();
                int new_width=profilePicWidth;
                int new_height=profilePicHeight;

                scaledBitmap = Bitmap.createScaledBitmap(unscaledBitmap,new_width,new_height, true);

                editProfilePic.setBackgroundResource(0);
                editProfilePic.setImageBitmap(scaledBitmap);





            }

            else if((requestCode == 2 && resultCode == RESULT_OK
                    && null != data))
            {
                Bundle extras = data.getExtras();
                Bitmap unscaledBitmap = (Bitmap) extras.get("data");



                int widthBeforeScale=unscaledBitmap.getWidth();
                int heightBeforeScale =unscaledBitmap.getHeight();
                int profilePicWidth=editProfilePic.getWidth();
                int profilePicHeight=editProfilePic.getHeight();
                int new_width=profilePicWidth;
                int new_height=profilePicHeight;
                scaledBitmap = Bitmap.createScaledBitmap(unscaledBitmap,new_width,new_height, true);

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





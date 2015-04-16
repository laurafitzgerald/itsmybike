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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.itsmybike.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.util.List;

import wit.lf.itsmybike.data.Bike;

public class EditBike extends Activity {

    private EditText editBikeNickname;
    private EditText editBikeSerialNumber;
    private EditText editBikeMake;
    private ImageView editBikeImage;
    private ImageView editIconEditBikeImage;
    private GlobalState gs;
    private String galleryFilePath;
    private Bitmap scaledBitmap;
    private ParseFile fileContainingBikePic;
    private int width;
    private int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_edit_bike);
            gs = (GlobalState) getApplication();
            editBikeNickname = (EditText) findViewById(R.id.editBikeNickname);
            editBikeSerialNumber = (EditText) findViewById(R.id.editBikeSerialNumber);
            editBikeMake = (EditText) findViewById(R.id.editBikeMake);
            editBikeImage = (ImageView) findViewById(R.id.editBikeImage);
            Bitmap bmapForSize=BitmapFactory.decodeResource(getResources(), R.drawable.no_profile_pic);
            height=bmapForSize.getHeight();
            width=bmapForSize.getWidth();
            editIconEditBikeImage = (ImageView) findViewById(R.id.editIconEditBikeImage);
            getBikeFromParse(gs.getBikeToEdit().getSerialNo());
        }

        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void getBikeFromParse(String serialNumber)
    {

        try {

            ParseQuery<ParseObject> query = ParseQuery.getQuery("Bike");
            if(gs.connectedToInternet(this)==false) {
                query.fromLocalDatastore();
            }
            query.whereEqualTo("serialNumber", serialNumber);
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> bikeList, ParseException e) {

                    if(e==null) {
                        Bike bikeFromParse = (Bike) bikeList.get(0);
                        editBikeNickname.setText(bikeFromParse.getNickname());
                        editBikeSerialNumber.setText(bikeFromParse.getSerialNo());
                        editBikeMake.setText(bikeFromParse.getMake());
                        getBikePicAsBitmap(bikeFromParse);

                        editIconEditBikeImage.setBackgroundResource(R.drawable.edit);
                    }

                    else
                    {
                        Log.v("EditBike", "problem retrieving bike to edit");
                    }

                }
            });

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

    }

    public void getBikePicAsBitmap(Bike bike) {



       try {


           byte[] data = gs.readLocalBikePic(bike.getSerialNo());
           scaledBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
           editBikeImage.setImageBitmap(scaledBitmap);
           Log.v("EditBike","Image retrieved locally");
       }

       catch (Exception ex)
       {
           ex.printStackTrace();
           Log.v("EditBike","Problem retrieving local image");

       }




    }

    public void saveBike(View view)
    {

       try {
           Bike bikeFromProfile = new Bike();


           if (editBikeNickname.getText().toString().equals("")) {
               Toast.makeText(this, "Please enter nickname", Toast.LENGTH_SHORT).show();
               editBikeNickname.requestFocus();
           } else if (editBikeSerialNumber.getText().toString().equals("")) {
               Toast.makeText(this, "Please enter serial number", Toast.LENGTH_SHORT).show();
               editBikeSerialNumber.requestFocus();
           } else if (editBikeMake.getText().toString().equals("")) {
               Toast.makeText(this, "Please enter make", Toast.LENGTH_SHORT).show();
               editBikeMake.requestFocus();

           } else {


               ParseQuery<ParseObject> query = ParseQuery.getQuery("Bike");
               if (gs.connectedToInternet(this)==false)
               {
                   query.fromLocalDatastore();
               }
               query.whereEqualTo("serialNumber", gs.getBikeToEdit().getSerialNo());
               Log.v("EditBike","serial number of bike to edit: "+gs.getBikeToEdit().getSerialNo());
               query.findInBackground(new FindCallback<ParseObject>() {
                   public void done(List<ParseObject> bikeList, ParseException e) {

                        if(e==null)
                       {
                       Bike bike = (Bike) bikeList.get(0);
                       bike.put("nickname", editBikeNickname.getText().toString());
                       bike.put("serialNumber", editBikeSerialNumber.getText().toString());
                       bike.put("make", editBikeMake.getText().toString());
                       bike.put("userId", ParseUser.getCurrentUser());
                       gs.saveBikePicLocally(prepareBikePicForSave(), editBikeSerialNumber.getText().toString());
                       bike.saveEventually(new SaveCallback() {
                           @Override
                           public void done(ParseException e) {
                               if(e==null) {
                                   gs.saveBikePicToParse(prepareBikePicForSave(), editBikeSerialNumber.getText().toString());
                               }

                               else
                               {
                                   Log.v("EditBike","problem saving bike pic to parse from edit screen");
                               }

                           }
                       });
                   }

                   else
                   {
                       Log.v("EditBike","problem retrieving bike using query");
                   }
                   }
               });




               finish();

               //gs.getProfile().getListOfBikes().add(bikeToAdd);
               Toast.makeText(this, "Bike updated", Toast.LENGTH_SHORT).show();
               Intent i=new Intent(this,Base.class);
               i.setAction("open profile");
               finish();
               startActivity(i);
           }

       }

       catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void changeBikePicture(View view)
    {
        Log.v("EditBike","Edit picture pressed");

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

            Log.v("EditBike","Edit picture pressed but "+ex.toString());

        }

    }


    public byte[] prepareBikePicForSave() {

        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            return byteArray;
        }

        catch (Exception ex)
        {
            ex.printStackTrace();
        }


        return null;
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
                editBikeImage.setImageBitmap(scaledBitmap);






            }

            else if((requestCode == 2 && resultCode == RESULT_OK
                    && null != data))
            {
                Bundle extras = data.getExtras();
                Bitmap unscaledBitmap = (Bitmap) extras.get("data");



                int widthBeforeScale=unscaledBitmap.getWidth();
                int heightBeforeScale =unscaledBitmap.getHeight();
                int profilePicWidth=editBikeImage.getWidth();
                int profilePicHeight=editBikeImage.getHeight();
                int new_width=profilePicWidth;
                int new_height=profilePicHeight;
                scaledBitmap = Bitmap.createScaledBitmap(unscaledBitmap,width,height,true);
                editBikeImage.setImageBitmap(scaledBitmap);

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

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.itsmybike.R;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

import wit.lf.itsmybike.data.Bike;

public class AddBike extends Activity {

    private EditText addBikeNickname;
    private EditText addBikeSerialNumber;
    private EditText addBikeMake;
    private ImageView addBikeImage;
    private ImageView addIconAddBikeImage;
    private GlobalState gs;
    private String galleryFilePath;
    private Bitmap scaledBitmap;
    private ParseFile fileContainingBikePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bike);
        gs=(GlobalState)getApplication();
        addBikeNickname=(EditText)findViewById(R.id.addBikeNickname);
        addBikeSerialNumber=(EditText)findViewById(R.id.addBikeSerialNumber);
        addBikeMake=(EditText)findViewById(R.id.addBikeMake);
        addBikeImage =(ImageView)findViewById(R.id.addBikeImage);
        addIconAddBikeImage=(ImageView)findViewById(R.id.addIconAddBikeImage);

        scaledBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.no_bike_pic);
        addBikeImage.setImageBitmap(scaledBitmap);
        addIconAddBikeImage.setBackgroundResource(R.drawable.add);





    }

    public void addBikePicture(View view)
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

    public byte[] prepareBikePicForSave() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;



    }

    public void saveBike(View view)


    {
      if (addBikeNickname.getText().toString().equals(""))
      {
          Toast.makeText(this,"Please enter nickname",Toast.LENGTH_SHORT).show();
          addBikeNickname.requestFocus();
      }
        else if (addBikeSerialNumber.getText().toString().equals(""))
        {
            Toast.makeText(this,"Please enter serial number",Toast.LENGTH_SHORT).show();
            addBikeSerialNumber.requestFocus();
        }

      else if (addBikeMake.getText().toString().equals(""))
      {
          Toast.makeText(this,"Please enter make",Toast.LENGTH_SHORT).show();
          addBikeMake.requestFocus();

      }

        else {



          
        Bike bike = Bike.create(Bike.class);
  		bike.put("nickname", addBikeNickname.getText().toString());
  		bike.put("serialNumber", addBikeSerialNumber.getText().toString());
  		bike.put("make", addBikeMake.getText().toString());
  		bike.put("userId", ParseUser.getCurrentUser());
        gs.saveBikePicLocally(prepareBikePicForSave(),addBikeSerialNumber.getText().toString());
        bike.pinInBackground();
  		bike.saveEventually(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                gs.saveBikePicToParse(prepareBikePicForSave(),addBikeSerialNumber.getText().toString());

            }
        });

          Toast.makeText(this, "Bike added", Toast.LENGTH_SHORT).show();
          startActivity(new Intent(this, Base.class));
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

                Bitmap unscaledBitmap= BitmapFactory.decodeFile(galleryFilePath);

                int widthBeforeScale=unscaledBitmap.getWidth();
                int heightBeforeScale =unscaledBitmap.getHeight();
                int profilePicWidth=addBikeImage.getWidth();
                int profilePicHeight=addBikeImage.getHeight();
                int new_width=profilePicWidth;
                int new_height=profilePicHeight;

                scaledBitmap = Bitmap.createScaledBitmap(unscaledBitmap,new_width,new_height, true);
                addBikeImage.setImageBitmap(scaledBitmap);







            }

            else if((requestCode == 2 && resultCode == RESULT_OK
                    && null != data))
            {
                Bundle extras = data.getExtras();
                Bitmap unscaledBitmap = (Bitmap) extras.get("data");



                int widthBeforeScale=unscaledBitmap.getWidth();
                int heightBeforeScale =unscaledBitmap.getHeight();
                int profilePicWidth=addBikeImage.getWidth();
                int profilePicHeight=addBikeImage.getHeight();
                int new_width=profilePicWidth;
                int new_height=profilePicHeight;
                scaledBitmap = Bitmap.createScaledBitmap(unscaledBitmap,new_width,new_height, true);
                addBikeImage.setImageBitmap(scaledBitmap);

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

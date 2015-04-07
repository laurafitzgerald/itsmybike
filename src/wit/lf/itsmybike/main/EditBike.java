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

import wit.lf.itsmybike.data.Bike;

public class EditBike extends Activity {

    private EditText editBikeNickname;
    private EditText editBikeSerialNumber;
    private EditText editBikeMake;
    private ImageView editBikeImage;
    private ImageView editIconEditBikeImage;
    private Bike bikeFromProfileBeingEdited;
    private GlobalState gs;
    private String galleryFilePath;
    private Bitmap scaledBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bike);
        gs=(GlobalState)getApplication();
        editBikeNickname=(EditText)findViewById(R.id.editBikeNickname);
        editBikeSerialNumber=(EditText)findViewById(R.id.editBikeSerialNumber);
        editBikeMake=(EditText)findViewById(R.id.editBikeMake);

       editBikeNickname.setText(gs.getBikeToEdit().getNickname());
        editBikeSerialNumber.setText(gs.getBikeToEdit().getSerialNo());
        editBikeMake.setText(gs.getBikeToEdit().getMake());
        editBikeImage=(ImageView)findViewById(R.id.editBikeImage);
        editIconEditBikeImage=(ImageView)findViewById(R.id.editIconEditBikeImage);
       editBikeImage.setBackgroundResource(gs.getBikeToEdit().getDrawableId());
        editIconEditBikeImage.setBackgroundResource(R.drawable.edit);




    }

    public void saveBike(View view)
    {
        Bike bikeFromProfile=new Bike();


/*
        for (Bike b:gs.getProfile().getListOfBikes())
        {
            if(b.getSerialNo().equals(gs.getBikeToEdit().getSerialNo()))
            {
                bikeFromProfile=b;
            }
        }
*/
        if (editBikeNickname.getText().toString().equals(""))
        {
            Toast.makeText(this,"Please enter nickname",Toast.LENGTH_SHORT).show();
            editBikeNickname.requestFocus();
        }

        else if (editBikeSerialNumber.getText().toString().equals(""))
        {
            Toast.makeText(this,"Please enter serial number",Toast.LENGTH_SHORT).show();
            editBikeSerialNumber.requestFocus();
        }

        else if (editBikeMake.getText().toString().equals(""))
        {
            Toast.makeText(this,"Please enter make",Toast.LENGTH_SHORT).show();
            editBikeMake.requestFocus();

        }

        else {

          /*  if(scaledBitmap!=null) {
                gs.getProfile().getListOfBikes().get(gs.getProfile().getListOfBikes().indexOf(bikeFromProfile)).setSelectedBikePic(scaledBitmap);
            }
            gs.getProfile().getListOfBikes().get(gs.getProfile().getListOfBikes().indexOf(bikeFromProfile)).setNickname(editBikeNickname.getText().toString());
            gs.getProfile().getListOfBikes().get(gs.getProfile().getListOfBikes().indexOf(bikeFromProfile)).setSerialNo(editBikeSerialNumber.getText().toString());
            gs.getProfile().getListOfBikes().get(gs.getProfile().getListOfBikes().indexOf(bikeFromProfile)).setMake(editBikeMake.getText().toString());
            Toast.makeText(this, "Bike Updated", Toast.LENGTH_SHORT).show();*/
            startActivity(new Intent(this, Base.class));
        }
    }

    public void changeBikePicture(View view)
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
                int profilePicWidth=editBikeImage.getWidth();
                int profilePicHeight=editBikeImage.getHeight();
                int new_width=profilePicWidth;
                int new_height=profilePicHeight;

                scaledBitmap = Bitmap.createScaledBitmap(unscaledBitmap,new_width,new_height, true);

                editBikeImage.setBackgroundResource(0);
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
                scaledBitmap = Bitmap.createScaledBitmap(unscaledBitmap,new_width,new_height, true);

                editBikeImage.setBackgroundResource(0);
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

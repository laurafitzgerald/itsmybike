package wit.lf.itsmybike.main;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by john on 02/04/2015.
 */
public class PictureGrabber extends Activity

{


    private String fromWhere;



    String bitmapAsCode;
    private ImageView imageFromGallery;

    public PictureGrabber(){}

    public PictureGrabber (String fromWhere)
    {
        this.fromWhere=fromWhere;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Intent openGallery = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(openGallery, 1);
    }


    public String getFromWhere() {
        return fromWhere;
    }

    public void setFromWhere(String fromWhere) {
        this.fromWhere = fromWhere;
    }

    public void getPictureFromGallery(View view) {



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
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                bitmapAsCode = cursor.getString(columnIndex);
                cursor.close();

                // Set the Image in ImageView after decoding the String
                imageFromGallery.setImageBitmap(BitmapFactory
                        .decodeFile(bitmapAsCode));

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }


    public String getBitmapAsCode() {
        return bitmapAsCode;
    }

    public void setBitmapAsCode(String bitmapAsCode) {
        this.bitmapAsCode = bitmapAsCode;
    }



}

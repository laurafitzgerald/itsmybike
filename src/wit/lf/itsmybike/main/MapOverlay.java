/*package wit.lf.itsmybike.main;


import com.google.android.maps.MapView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

public class MapOverlay extends com.google.android.maps.Overlay{
	
	GlobalState gs;
	
	 public boolean draw(Canvas canvas, MapView mapView, 
			    boolean shadow, long when) 
			    {
			        super.draw(canvas, mapView, shadow);                   

			        //---translate the GeoPoint to screen pixels---
			        Point screenPts = new Point();
			        mapView.getProjection().toPixels(p, screenPts);

			        //---add the marker---
			        Bitmap bmp = BitmapFactory.decodeResource(
			            gs.getResources(), R.drawable.pink);            
			        canvas.drawBitmap(bmp, screenPts.x, screenPts.y-32, null);         
			        return true;
			    }

}
*/
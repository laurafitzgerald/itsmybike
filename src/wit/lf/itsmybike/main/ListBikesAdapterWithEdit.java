package wit.lf.itsmybike.main;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.itsmybike.R;
import com.parse.ParseFile;

import java.util.List;

import wit.lf.itsmybike.data.Bike;

/**
 * Created by john on 01/04/2015.
 */
public class ListBikesAdapterWithEdit extends BaseAdapter
{

    Context context;

    protected List<Bike> listBikes;
    LayoutInflater inflater;
    private ParseFile fileContainingBikePic;
    private GlobalState gs;




    private ViewHolder viewHolder;

    public ListBikesAdapterWithEdit(Context context, List<Bike> listBikes,GlobalState gs)
    {

        this.listBikes = listBikes;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.gs=gs;




    }


    @Override
    public int getCount() {
        return listBikes.size();
    }

    @Override
    public Object getItem(int position) {
        return listBikes.get(position);
    }

  @Override
    public long getItemId(int position) {
        return listBikes.get(position).getDrawableId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {




        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = this.inflater.inflate(R.layout.list_bike_with_edit_option, parent, false);
            viewHolder.nickname_editable = (TextView) convertView.findViewById(R.id.bike_nickname_editable);
            viewHolder.serial_editable = (TextView) convertView.findViewById(R.id.bike_serial_editable);
            viewHolder.make_editable=(TextView)convertView.findViewById(R.id.bike_make_editable);
            viewHolder.imgBike_editable= (ImageView) convertView.findViewById(R.id.img_bike_editable);
            viewHolder.editIcon=(ImageView) convertView.findViewById(R.id.editIcon);


            convertView.setTag(viewHolder);


        }else{

            viewHolder = (ViewHolder) convertView.getTag();


        }


        Bike bike = listBikes.get(position);
        viewHolder.nickname_editable.setText(bike.getNickname());
        viewHolder.serial_editable.setText(bike.getSerialNo());
        viewHolder.make_editable.setText(bike.getMake());
        getBikePicAsBitmap(bike);
        viewHolder.editIcon.setImageResource(R.drawable.edit);

        return convertView;


    }


    static class ViewHolder {
        TextView nickname_editable;
        TextView serial_editable;
        TextView make_editable;
        ImageView imgBike_editable;
        ImageView editIcon;

    }

    public void getBikePicAsBitmap(Bike bike)
    {

       try {
           if (gs.readLocalBikePic(bike.getSerialNo()) != null) {
               byte[] data = gs.readLocalBikePic(bike.getSerialNo());
               viewHolder.imgBike_editable.setImageBitmap(BitmapFactory.decodeByteArray(data, 0, data.length));
           } else {
               viewHolder.imgBike_editable.setBackgroundResource(R.drawable.no_bike_pic);
           }
       }

       catch (Exception ex)
       {
           ex.printStackTrace();
       }





    }



}

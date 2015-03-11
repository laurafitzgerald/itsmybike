package wit.lf.itsmybike.main;

import java.util.ArrayList;

import wit.lf.itsmybike.data.Bike;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.itsmybike.R;

public class BikeAdapter extends ArrayAdapter<Bike> {

    private Context context;
    private ArrayList<Bike> bikes;
    public Resources res;
    Bike currRowVal = null;
    LayoutInflater inflater;

    public BikeAdapter(Context context,
            int textViewResourceId, ArrayList<Bike> bikes,
            Resources resLocal) {
        super(context, textViewResourceId, bikes);
        this.context = context;
        this.bikes = bikes;
        this.res = resLocal;        
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        View row = inflater.inflate(R.layout.bike_spinner_item, parent, false);
        currRowVal = null;
        currRowVal = (Bike) bikes.get(position);
        TextView label = (TextView) row.findViewById(R.id.spinnerItem);
   
        if (position == 0) {
           label.setText("Please select bike");
        } else {
            label.setText(currRowVal.getNickname());
          
        }

        return row;
    }
}
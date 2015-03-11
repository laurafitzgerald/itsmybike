package wit.lf.itsmybike.main;

import java.util.List;

import wit.lf.itsmybike.data.Bike;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.itsmybike.R;

public class ListBikesAdapter extends BaseAdapter{

	
	Context context;
	
	protected List<Bike> listBikes;
	LayoutInflater inflator;
	
	public ListBikesAdapter(Context context, List<Bike> listBikes){
		
		this.listBikes = listBikes;
		this.inflator = LayoutInflater.from(context);
		this.context = context;
				
		
		
		
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
		ViewHolder viewHolder;
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = this.inflator.inflate(R.layout.layout_list_bike, parent, false);
			
			
			
			viewHolder.txtNickname = (TextView) convertView.findViewById(R.id.text_bike_nickname);
			viewHolder.txtSerial = (TextView) convertView.findViewById(R.id.text_bike_serial);
			
			viewHolder.imgBike = (ImageView) convertView.findViewById(R.id.img_bike);
			
			
			convertView.setTag(viewHolder);
			
			
		}else{
			
			viewHolder = (ViewHolder) convertView.getTag();
			
			
		}
		
		
		Bike bike = listBikes.get(position);
		viewHolder.txtNickname.setText(bike.getNickname());
		viewHolder.txtSerial.setText(bike.getSerialNo());
		//viewHolder.txtMake.setTag(bike.getMake());
		viewHolder.imgBike.setImageResource(bike.getDrawableId());
		
		return convertView;
		
		
	}
	

static class ViewHolder {
	  TextView txtNickname;
	  TextView txtSerial;
	  TextView txtMake;
	  ImageView imgBike;
	 
	}

	

}


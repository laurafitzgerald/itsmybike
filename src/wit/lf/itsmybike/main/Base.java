package wit.lf.itsmybike.main;


import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.itsmybike.R;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import wit.lf.itsmybike.data.Bike;

public class Base extends FragmentActivity {

	
	private GlobalState gs;
	private TabsAdapter tabsAdapter;
	private ViewPager viewPager;
	private ActionBar actionBar;
    private View view;
    private ActionBar.Tab profileFragmentTab;


	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		
		switch(item.getItemId()){
		
		
		case R.id.log_out:

			ParseUser.logOut();
		    Toast.makeText(getApplicationContext(), "Logging Out...", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(Base.this, LogInScreen.class);
			startActivity(intent);
			finish();
			
			return true;
			

			

		default:
			return super.onOptionsItemSelected(item);
			
		
		}
		
		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		
		
		return true;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);



		
		gs = (GlobalState) getApplication();
		
		populateData();
		
		viewPager = new ViewPager(this);
		viewPager.setId(R.id.base);
		
		
		setContentView(viewPager);
		
		 actionBar = getActionBar();

		
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
		tabsAdapter = new TabsAdapter(this, viewPager);
        profileFragmentTab=actionBar.newTab();
        profileFragmentTab.setText("Profile");
        profileFragmentTab.setTag("profileFragment");



		tabsAdapter.addTab(actionBar.newTab().setText("Home"), HomeFragment.class, null);
		tabsAdapter.addTab(profileFragmentTab, ProfileFragment.class, null);
		tabsAdapter.addTab(actionBar.newTab().setText("Report"), ReportFragment.class, null);




        if(getIntent().getAction()!=null)
        {


            if (getIntent().getAction().equals("open profile"))
            {
                actionBar.setSelectedNavigationItem(1);
            }
        }
		

		if(savedInstanceState != null)
        {




            actionBar.setSelectedNavigationItem(savedInstanceState.getInt("tab", 0));

			
		}




	}

	@Override
	protected void onSaveInstanceState(Bundle outState){
		
		
		super.onSaveInstanceState(outState);
		outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
	}





	
	
	private void populateData(){
		
		
		
		/*StolenBike parseTest = new StolenBike(52.249030, -7.137352, "", "24/06/2014");
		
		gs.getStolenBikes().add(new StolenBike(52.249030, -7.137352, "", "24/06/2014"));
		gs.getStolenBikes().add(new StolenBike(52.246323, -7.142427, "", "20/12/2014"));
		gs.getStolenBikes().add(new StolenBike(52.245758, -7.131462, "", "01/01/2015"));
		gs.getStolenBikes().add(new StolenBike(52.245698, -7.141462, "", "05/02/2014"));
		gs.getStolenBikes().add(new StolenBike(52.246565, -7.141462, "", "06/07/2014"));
		gs.getStolenBikes().add(new StolenBike(52.254243, -7.142345, "", "15/10/2014"));
		gs.getStolenBikes().add(new StolenBike(52.252345, -7.131876, "", "08/10/2014"));
		gs.getStolenBikes().add(new StolenBike(52.253487, -7.137654, "", "09/02/2015"));
		gs.getStolenBikes().add(new StolenBike(52.249056, -7.138754, "", "15/01/2015"));
		*/
		
		
		

		
		//gs.getBikes().add(bikeOne);
		//gs.getBikes().add(bikeTwo);

		
		
	
		
		
	}

    public void editProfile(View view)
    {
        startActivity(new Intent(this,EditProfile.class));
    }

    public void editBike(View view)
    {
        RelativeLayout rowContainingButton=(RelativeLayout)view.getParent();


        TextView serialNumberTV=(TextView)rowContainingButton.getChildAt(4);
        String serialNumber=serialNumberTV.getText().toString();

        for (Bike b: gs.getListOfBikes())
        {
            if(b.getSerialNo().equals(serialNumber))
            {
                gs.setBikeToEdit(b);
            }
        }

        startActivity(new Intent(this,EditBike.class));

        
       
    }


    public void deleteBike(View aview)
    {
        view=aview;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(this.getResources().getString(R.string.confirm_bike_deletion));
        builder.setPositiveButton(this.getResources().getString(R.string.delete_bike), new DialogInterface.OnClickListener() {


            @Override
            public void onClick(DialogInterface dialog, int which) {
                RelativeLayout rowContainingButton=(RelativeLayout)view.getParent();

                TextView serialNumberTV=(TextView)rowContainingButton.getChildAt(4);
                String serialNumber=serialNumberTV.getText().toString();

                for (Bike b: gs.getListOfBikes())
                {
                    if(b.getSerialNo().equals(serialNumber))
                    {
                        gs.setBikeToEdit(b);
                    }
                }



                ParseQuery<ParseObject> query = ParseQuery.getQuery("Bike");
                if (gs.connectedToInternet(Base.this)==false)
                {
                    query.fromLocalDatastore();
                }
                query.whereEqualTo("serialNumber", gs.getBikeToEdit().getSerialNo());
                Log.v("EditBike", "serial number of bike to edit: " + gs.getBikeToEdit().getSerialNo());
                query.findInBackground(new FindCallback<ParseObject>() {
                    public void done(List<ParseObject> bikeList, ParseException e) {

                        if(e==null)
                        {
                            Bike bike = (Bike) bikeList.get(0);

                            bike.deleteEventually(new DeleteCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if(e==null)
                                    {

                                        Toast.makeText(Base.this,"Bike Deleted",Toast.LENGTH_LONG).show();

                                       Intent i=new Intent(Base.this,Base.class);
                                       i.setAction("open profile");
                                       finish();
                                       startActivity(i);

                                    }

                                    else
                                    {

                                        Log.v("DeleteBike","problem deleting bike pic");
                                    }

                                }
                            });
                        }

                        else
                        {
                            Log.v("DeleteBike","problem retrieving bike using query");
                        }
                    }
                });





            }
        });

        builder.setNegativeButton(this.getResources().getString(R.string.do_not_delete_bike), new DialogInterface.OnClickListener() {


            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });




        AlertDialog dialog=builder.create();
        dialog.show();





    }

    public void addBike(View view)
    {
        startActivity(new Intent(this,AddBike.class));
    }

			
}
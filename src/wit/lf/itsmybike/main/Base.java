package wit.lf.itsmybike.main;


import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.itsmybike.R;
import com.parse.ParseUser;

import wit.lf.itsmybike.data.Bike;

public class Base extends FragmentActivity {

	
	private GlobalState gs;
	private TabsAdapter tabsAdapter;
	private ViewPager viewPager;
	private ActionBar actionBar;


	
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

		tabsAdapter.addTab(actionBar.newTab().setText("Home"), HomeFragment.class, null);
		tabsAdapter.addTab(actionBar.newTab().setText("Profile"), ProfileFragment.class, null);
		tabsAdapter.addTab(actionBar.newTab().setText("Report"), ReportFragment.class, null);
	
		

		if(savedInstanceState != null){
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

    public void addBike(View view)
    {
        startActivity(new Intent(this,AddBike.class));
    }

			
}
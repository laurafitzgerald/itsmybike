package wit.lf.itsmybike.main;


import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.itsmybike.R;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import wit.lf.itsmybike.data.Bike;
import wit.lf.itsmybike.data.Profile;
import wit.lf.itsmybike.data.StolenBike;

public class GlobalState extends Application {


    private ArrayList<StolenBike> stolenBikes = new ArrayList<StolenBike>();
    private Profile profile;
    //private ArrayList<Bike> bikes = new ArrayList<Bike>();
    private boolean loggedIn;
    private Double currentLat;
    private Double currentLng;
    private List<Profile> listOfProfiles = new ArrayList<Profile>();
    private Bike bikeToEdit;
    private List<Bike> listOfBikes = new ArrayList<Bike>();
    private File profilePicFile;
    private byte[] theBytes;
    private String serialNumber;
    private ParseFile fileContainingProfilePic;
    private ParseFile fileContainingBikePic;
    Double selectedLat;
    Double selectedLng;
    public boolean compressProfilePic;
 



	public void onCreate() {


        Parse.enableLocalDatastore(this);
        compressProfilePic=false;

        Parse.initialize(this, getString(com.example.itsmybike.R.string.parse_application_id), getString(R.string.parse_client_key));

        ParseObject.registerSubclass(StolenBike.class);
        ParseObject.registerSubclass(Profile.class);
        ParseObject.registerSubclass(Bike.class);
        ParseInstallation.getCurrentInstallation().saveInBackground();
         profilePicFile = new File(this.getFilesDir(),"profilePic.txt");
    }


    public List<Profile> getListOfProfiles() {
        return listOfProfiles;
    }

    public Double getCurrentLat() {
        return currentLat;
    }

    public void setCurrentLat(Double currentLat) {
        this.currentLat = currentLat;
    }

    public Double getCurrentLng() {
        return currentLng;
    }

    public void setCurrentLng(Double currentLng) {
        this.currentLng = currentLng;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public List<Bike> getListOfBikes() {
        return listOfBikes;
    }

    public void setListOfBikes(List<Bike> listOfBikes) {
        this.listOfBikes = listOfBikes;
    }

	/*public ArrayList<Bike> getBikes() {
		return bikes;
	}*/

	/*public void setBikes(ArrayList<Bike> bikes) {
		this.bikes = bikes;
	}*/

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {

        this.profile = profile;
    }

    public ArrayList<StolenBike> getStolenBikes() {
        return stolenBikes;
    }

    public void setStolenBikes(ArrayList<StolenBike> stolenBikes) {
        this.stolenBikes = stolenBikes;
    }


    public Bike getBikeToEdit() {
        return bikeToEdit;
    }

    public void setBikeToEdit(Bike bikeToEdit) {
        this.bikeToEdit = bikeToEdit;
    }



    public void saveProfilePicToParse(final byte[] byteArray)
    {


        fileContainingProfilePic = new ParseFile("profilePicFile.jpg", byteArray);
        try {
            fileContainingProfilePic.save();

            ParseUser user = ParseUser.getCurrentUser();
            user.put("profilePic", fileContainingProfilePic);
            user.saveEventually(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        saveProfilePicLocally(byteArray);
                        Log.v("SaveEventually", "No bother saving profile pic");
                    } else {
                        Log.v("SaveEventually", "Problem saving profile pic");
                    }
                }
            });

        }

        catch (ParseException e)
        {
            e.printStackTrace();
        }




    }


    public byte[] readLocalProfilePic()

    {
        byte[] byteArray=null;
        try {

            FileInputStream fis = new FileInputStream(profilePicFile);
            byteArray = new byte[(int) profilePicFile.length()];
            fis.read(byteArray);



        } catch (FileNotFoundException e) {

            e.printStackTrace();
            Log.v("FileError", "file not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArray;
    }

    public byte[] readParseProfilePic()

    {
      ParseUser user=ParseUser.getCurrentUser();
      ParseFile profilePicFile= (ParseFile)user.get("profilePic");
      profilePicFile.getDataInBackground(new GetDataCallback() {
          @Override
          public void done(byte[] bytes, ParseException e) {

              theBytes=bytes;
          }
      });
        return theBytes;
    }

    public void populateLocalBikeList() {

        ParseQuery<Bike> query = new ParseQuery<Bike>("Bike");
        query.whereEqualTo("userId", ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<Bike>() {
            @Override
            public void done(List<Bike> bikes, ParseException e) {

                if (e == null) {

                    Log.v("Populate local bikes","size of list: "+bikes.size());

                    for (Bike b : bikes) {
                        try {
                            b.pinInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e != null) {
                                        Log.v("Populate local bikes", "Failed to save bike locally");
                                    } else {
                                        Log.v("Populate local bikes", "Bike saved locally");
                                    }

                                }
                            });
                            ParseFile pf = (ParseFile) b.get("bikePic");
                            serialNumber = b.getSerialNo();
                            pf.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] bytes, ParseException e) {

                                    if(e==null)
                                    {

                                        saveBikePicLocally(bytes, serialNumber);
                                    }

                                    else
                                    {
                                        Log.v("Populate local bikes","Failed to populate");
                                    }
                                }
                            });
                        }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                        Log.v("Populate local bikes","Failed to populate");
                    }

                    }
                }
            }
        });
    }



    public void saveProfilePicLocally(byte[] byteArray) {
        try {

            FileOutputStream fos = new FileOutputStream(profilePicFile);
            fos.write(byteArray);
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        }


    }

    public void saveBikePicToParse(final byte[] byteArray, final String serialNumber)
    {
        fileContainingBikePic = new ParseFile("bikePicFile.jpg", byteArray);
        try {
            fileContainingBikePic.save();

            ParseQuery<Bike> query = new ParseQuery<Bike>("Bike");
            query.whereEqualTo("serialNumber", serialNumber);
            query.findInBackground(new FindCallback<Bike>() {
                @Override
                public void done(List<Bike> bikes, ParseException e) {

                    if(e==null)
                    {
                        Bike bike = bikes.get(0);
                        bike.put("bikePic", fileContainingBikePic);
                        bike.saveInBackground();
                        saveBikePicLocally(byteArray, serialNumber);
                        Log.v("SaveBikePicToParse","no problem saving bike pic to parse");
                    }

                    else
                    {
                        Log.v("SaveBikePicToParse","Problem saving bike pic");
                    }
                }
            });
        } catch (ParseException e) {
            e.printStackTrace();
        }




         }






    public byte[] readLocalBikePic(String serialNumber)

    {
        byte[] byteArray=null;
        try {

            File theFile=new File(this.getFilesDir() + "/"+serialNumber+".txt");
            Log.v("ReadBikePicLocally", "Bike pic read from: "+theFile.getAbsolutePath()+"File length: "+theFile.length());
            FileInputStream fis = new FileInputStream(theFile);
            byteArray = new byte[(int) theFile.length()];
            fis.read(byteArray);



        } catch (Exception e) {

            e.printStackTrace();
            Log.v("Test","read local bike pic error:"+ e.toString());
        }



        return byteArray;
    }


    public void saveBikePicLocally(byte[] byteArray, String serialNumber) {
        try {

            File bikePicFile = new File(this.getFilesDir() + "/" + serialNumber + ".txt");
            FileOutputStream fos = new FileOutputStream(bikePicFile);
            fos.write(byteArray);
            fos.close();
            Log.v("SaveBikePicLocally", "Bike Pic Saved Locally"+bikePicFile.getAbsolutePath());
        }

       catch(Exception e)
            {
                e.printStackTrace();
                Log.v("SaveBikePicLocally", "Bike Pic Can't Save Locally"+e.toString());
            }




    }

    public boolean connectedToInternet(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }

        return false;
    }






}





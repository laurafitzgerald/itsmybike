package wit.lf.itsmybike.data;


import android.graphics.Bitmap;

import java.util.ArrayList;

public class Profile {

	
	
	private String firstName;
	private String secondName;
	//private Integer noOfBikes;
	private String password;
	private String email;
	private String location;
	private int drawableId;
    private ArrayList<Bike>listOfBikes;



    private Bitmap selectedProfilePic;
	
	




public Profile(){};

	public Profile(String firstName, String secondName,String email,String password,String location,int drawableId,ArrayList<Bike>listOfBikes){
		
		
		this.firstName = firstName;
		this.secondName = secondName;
		this.location = location;
		//this.noOfBikes = noOfBikes;
		this.drawableId = drawableId;
		this.email = email;
		this.password = password;
        this.listOfBikes=listOfBikes;

		
			
		
		
		
		
	}






	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getFirstName() {
		return firstName;
	}



	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}



	public String getSecondName() {
		return secondName;
	}



	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}



	/*public Integer getNoOfBikes() {
		return noOfBikes;
	}*/



	/*public void setNoOfBikes(Integer noOfBikes) {
		this.noOfBikes = noOfBikes;
	}*/





	public int getDrawableId() {
		return drawableId;
	}






	public void setDrawableId(int drawableId) {
		this.drawableId = drawableId;
	}






	public String getLocation() {
		return location;
	}



    public Bitmap getSelectedProfilePic() {
        return selectedProfilePic;
    }

    public void setSelectedProfilePic(Bitmap selectedProfilePic) {
        this.selectedProfilePic = selectedProfilePic;
    }


	public void setLocation(String location) {
		this.location = location;
	}

    public ArrayList<Bike> getListOfBikes() {
        return listOfBikes;
    }

    public void setListOfBikes(ArrayList<Bike> listOfBikes) {
        this.listOfBikes = listOfBikes;
    }

}

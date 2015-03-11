package wit.lf.itsmybike.data;


public class Profile {

	
	
	private String firstName;
	private String secondName;
	private Integer noOfBikes;
	private String password;
	private String email;
	private String location;
	private int drawableId;
	
	






	public Profile(String firstName, String secondName, String location, Integer noOfBikes, int drawableId, String email, String password){
		
		
		this.firstName = firstName;
		this.secondName = secondName;
		this.location = location;
		this.noOfBikes = noOfBikes;
		this.drawableId = drawableId;		
		this.email = email;
		this.password = password;
		
			
		
		
		
		
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



	public Integer getNoOfBikes() {
		return noOfBikes;
	}



	public void setNoOfBikes(Integer noOfBikes) {
		this.noOfBikes = noOfBikes;
	}




	
	public int getDrawableId() {
		return drawableId;
	}






	public void setDrawableId(int drawableId) {
		this.drawableId = drawableId;
	}






	public String getLocation() {
		return location;
	}






	public void setLocation(String location) {
		this.location = location;
	}

}

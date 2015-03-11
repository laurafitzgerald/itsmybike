package wit.lf.itsmybike.data;

//here are some changes

public class Bike {

	private int drawableId;

	private String serialNo;
	
	private String nickname;
	private String make;
	private boolean stolen;
	
	


	public Bike(int drawableId, String nickname, String serialNo, String make ){
		
		this.drawableId = drawableId;
		this.serialNo = serialNo;
		
		this.nickname = nickname;
		this.make = make;
		this.stolen = false;
		
		
	}
	
	
	public boolean isStolen() {
		return stolen;
	}

	public void setStolen(boolean stolen) {
		this.stolen = stolen;
	}

	public int getDrawableId() {
		return drawableId;
	}


	public void setDrawableId(int drawableId) {
		this.drawableId = drawableId;
	}


	public String getMake() {
		return make;
	}


	public void setMake(String make) {
		this.make = make;
	}


	public String getSerialNo() {
		return serialNo;
	}


	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}





	public String getNickname() {
		return nickname;
	}


	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}

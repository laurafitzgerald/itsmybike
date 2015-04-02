package wit.lf.itsmybike.data;

//here are some changes

import android.graphics.Bitmap;

public class Bike {

	private int drawableId;

	private String serialNo;
	
	private String nickname;
	private String make;
	private boolean stolen;



    private Bitmap selectedBikePic;
	
	


	public Bike(int drawableId, String nickname, String serialNo, String make ){
		
		this.drawableId = drawableId;
		this.serialNo = serialNo;
		
		this.nickname = nickname;
		this.make = make;
		this.stolen = false;
		
		
	}

    public Bike(){}
	
	
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


    public Bitmap getSelectedBikePic() {
        return selectedBikePic;
    }

    public void setSelectedBikePic(Bitmap selectedBikePic) {
        this.selectedBikePic = selectedBikePic;
    }


	public String getNickname() {
		return nickname;
	}


	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}

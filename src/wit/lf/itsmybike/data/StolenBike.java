package wit.lf.itsmybike.data;

public class StolenBike {

	
	private Double lat;
	private Double lng;
	private String serialNumber;
	private String date;
	
	public StolenBike(Double lat, Double lng, String serialNumber, String date){
		
		this.lat = lat;
		this.lng = lng;
		this.serialNumber = serialNumber;
		this.date = date;
		
		
		
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}
}

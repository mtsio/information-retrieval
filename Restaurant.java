public class Restaurant {
    private String businessId;
    private String name;
    private String address;
    private String city;
    private String state;
    private String postalCode;
    private double longitude;
    private double latitude;
    private double stars;
    private long reviewCount;
    private String neighborhood;

    Restaurant (String businessId, String name, String address, String city,
		double longitude, double latitude,
		double stars, long reviewCount, String neighborhood,
		String state, String postalCode) {
	this.businessId = businessId;
	this.name = name;
	this.address = address;
	this.city = city;
	this.latitude = latitude;
	this.longitude = longitude;
	this.stars = stars;
	this.reviewCount = reviewCount;
	this.neighborhood = neighborhood;
	this.state = state;
	this.postalCode = postalCode;
    }

    public Object getField (String field) {
	Object returnValue = null;
	
	switch (field) {
	case "businessId":
	    returnValue = businessId;
	    break;
	case "name":
	    returnValue = name;
	    break;
	case "address":
	    returnValue = address;
	    break;
	case "city":
	    returnValue = city;
	    break;
	case "latitude":
	    returnValue = latitude;
	    break;
	case "longitude":
	    returnValue = longitude;
	    break;
	case "stars":
	    returnValue = stars;
	    break;
	case "reviewCount":
	    returnValue = reviewCount;
	    break;
	case "neighborhood":
	    returnValue = neighborhood;
	    break;
	case "state":
	    returnValue = state;
	    break;
	case "postalCode":
	    returnValue = postalCode;
	default:
	    break;
	}
	
	return returnValue;
    }

    public String getCity () {
	return this.city;
    }
}

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class JsonParser {
    final static String fileName = "../dataset/business.json";
	
    public ArrayList<Restaurant> getRestaurantsFromJson () {
	
	ArrayList<JSONObject> json=new ArrayList<JSONObject>();
	ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
	JSONObject obj;
        
	// This will reference one line at a time
	String line = null;

	try {
	    int i=0;
	    long reviews=0;
	    long minRev=20000;
	    long maxRev=0;
	    double averageRev=0 ;
	    // FileReader reads text files in the default encoding.
	    FileReader fileReader = new FileReader(fileName);
            
	    // Always wrap FileReader in BufferedReader.
	    BufferedReader bufferedReader = new BufferedReader(fileReader);
	    while((line = bufferedReader.readLine()) != null) {            	
		obj = (JSONObject) new JSONParser().parse(line);
		JSONObject jsonObject = (JSONObject) obj;                               
		JSONArray msg = (JSONArray) jsonObject.get("categories");
		Iterator<String> iterator = msg.iterator();

		while (iterator.hasNext()) {
		    String word = iterator.next();
		    if(word.equals("Restaurants")){
			if(i<20000){
			    i++;
			    json.add(obj);
			    String businessId = (String) jsonObject.get ("business_id");
			    String name = (String) jsonObject.get ("name");
			    String address = (String) jsonObject.get ("address");
			    String city = (String) jsonObject.get ("city");
			    String state = (String) jsonObject.get ("state");
			    String postalCode = (String) jsonObject.get ("postal_code");
			    double longitude = (double) jsonObject.get ("longitude");
			    double latitude = (double) jsonObject.get ("latitude");
			    double stars = (double) jsonObject.get ("stars");
			    long reviewCount = (long) jsonObject.get ("review_count");
			    String neighborhood = (String) jsonObject.get ("neighborhood");
			    JSONObject attributes = (JSONObject)jsonObject.get ("attributes");
			    //			    boolean hasTV = attributes.get ("HasTV");
			    
			    Restaurant restaurant = 
				new Restaurant (businessId, name, address, city,
						longitude, latitude, stars, reviewCount,
						neighborhood, state, postalCode);
			    restaurants.add (restaurant);

			    
			    // if(reviewCount > maxRev){
			    // 	maxRev=reviewCount;
			    // }
			    // if(reviewCount < minRev){
			    // 	minRev=reviewCount;
			    // }
			    // reviews = reviews + reviewCount;

			}
			else {
			    break;
			}
		    }
		    //System.out.println(word);
		}
		if(i>=20000){
		    break;
		}
	    }
	    // Always close files.
	    // averageRev = (double)reviews/10000;
	    // System.out.println("Total reviews: "+reviews);
	    // System.out.println("Minimum reviews: "+minRev);
	    // System.out.println("Maximum: "+maxRev);
	    // System.out.println("Average reviews: "+averageRev);
	    bufferedReader.close();         
	}
	catch(FileNotFoundException ex) {
	    System.out.println("Unable to open file '" + fileName + "'");                
	}
	catch(IOException ex) {
	    System.out.println("Error reading file '" + fileName + "'");                  
	    // Or we could just do this: 
	    // ex.printStackTrace();
	} catch (ParseException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	return restaurants;
    }
}


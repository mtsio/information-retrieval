import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class JsonParser {
    final static String businessFile = "../dataset/business.json";
    final static String reviewFile = "../dataset/review.json";
    final static int numRestaurants = 20000;
    TreeSet<String> restaurantIds = new TreeSet<String>();
    
    public ArrayList<Restaurant> getRestaurantsFromJson () {
	
	ArrayList<JSONObject> json=new ArrayList<JSONObject>();
	ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
	JSONObject obj;
        
	// This will reference one line at a time
	String line = null;

	try {
	    int r=0;
	    long reviews=0;
	    long minRev=20000;
	    long maxRev=0;
	    double averageRev=0 ;
	    // FileReader reads text files in the default encoding.
	    FileReader fileReader = new FileReader(businessFile);
            
	    // Always wrap FileReader in BufferedReader.
	    BufferedReader bufferedReader = new BufferedReader(fileReader);
	    while((line = bufferedReader.readLine()) != null && r < numRestaurants) {
		
		obj = (JSONObject) new JSONParser().parse(line);
		JSONObject jsonObject = (JSONObject) obj;
		JSONArray msg = (JSONArray) jsonObject.get("categories");
		Iterator<String> iterator = msg.iterator();

		while (iterator.hasNext()) {
		    String word = iterator.next();
		    if(word.equals("Restaurants")){
			String businessId = (String) jsonObject.get ("business_id");
			restaurantIds.add(businessId);

			String name = (String) jsonObject.get ("name");
			String address =
			    (String) jsonObject.get ("address");
			String city = (String) jsonObject.get ("city");
			String state = (String) jsonObject.get ("state");
			String postalCode =
			    (String) jsonObject.get ("postal_code");
			double longitude =
			    (double) jsonObject.get ("longitude");
			double latitude =
			    (double) jsonObject.get ("latitude");
			double stars = (double) jsonObject.get ("stars");
			long reviewCount =
			    (long) jsonObject.get ("review_count");
			String neighborhood =
			    (String) jsonObject.get ("neighborhood");
			JSONObject attributes =
			    (JSONObject)jsonObject.get ("attributes");
			Restaurant restaurant = 
			    new Restaurant (businessId, name, address,
					    city, longitude, latitude,
					    stars, reviewCount,
					    neighborhood, state,
					    postalCode);
			restaurants.add (restaurant);
			r++;

			break;
			
			    // if(reviewCount > maxRev){
			    // 	maxRev=reviewCount;
			    // }
			    // if(reviewCount < minRev){
			    // 	minRev=reviewCount;
			    // }
			    // reviews = reviews + reviewCount;
		    }
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
	    System.out.println("Unable to open file '" + businessFile + "'");                
	}
	catch(IOException ex) {
	    System.out.println("Error reading file '" + businessFile + "'");                  
	    // Or we could just do this: 
	    // ex.printStackTrace();
	} catch (ParseException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	return restaurants;
    }

    public ArrayList<Review>
	getRestaurantReviewsFromJson (ArrayList<Restaurant> restaurants) {

	ArrayList<Review> restaurantReviews = new ArrayList<Review>();
	String reviewId = "";
	String userId;
	String businessId;
	double stars;
	String date;
	String text;
	// int useful, funny, cool; 

	JSONObject jsonObject;
	String line = null;
	int numReviewRestaurants = 0;
	
	System.out.println (restaurantIds.size());
	
	try {
	    FileReader fileReader = new FileReader(reviewFile);
	    BufferedReader bufferedReader = new BufferedReader(fileReader);
	    
		while((line = bufferedReader.readLine()) != null
		      && numReviewRestaurants < 1000000) {
		    jsonObject = (JSONObject) new JSONParser().parse(line);
		    businessId = (String)jsonObject.get ("business_id");
		    if (restaurantIds.contains (businessId)) {
			reviewId = (String)jsonObject.get ("review_id");
			userId = (String)jsonObject.get ("user_id");
			stars = (long)jsonObject.get ("stars");
			date = (String)jsonObject.get ("date");
			text = (String)jsonObject.get ("text");
			// useful = (int)jsonObject.get ("useful");
			// funny = (int)jsonObject.get ("funny");
			// cool = (int)jsonObject.get ("cool");
			restaurantReviews.add(new Review(reviewId, userId,
							 businessId, stars, date,
							 text));
			numReviewRestaurants++;
		    }
		}

		bufferedReader.close();
		System.out.println (numReviewRestaurants);

	} catch (IOException ex) {
	    System.out.println (ex.getMessage ());
	} catch (ParseException parseEx) {
	    System.out.println (parseEx.getMessage ());
	}
	
	return restaurantReviews;
    }
}

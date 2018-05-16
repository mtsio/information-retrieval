import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.io.IOException;


public class SearchEngine {
    static ArrayList<Restaurant> restaurants;
    static String index = "index";
    static String city = "Oregon";
    static String field = "city";
    static int numResults = 10;

    public static void main (String[] args) {
	boolean createIndex = false;
	boolean searchRestaurant = false;
	String location = null;
	
	for (int i=0; i<args.length; i++) {
	    if ("-index".equals (args[i])) {
		createIndex = true;
		i++;
	    } else if ("-restaurant".equals (args[i])) {
		searchRestaurant = true;
		location = args[i+1];
	    }
	}
	
	if (createIndex) {
	    JsonParser parser = new JsonParser ();
	    restaurants = parser.getRestaurantsFromJson ();

	    IndexCreator index = new IndexCreator ();
	    index.createDocs (restaurants);
	}

	if (searchRestaurant)
	    searchRestaurant (location);
    }


    public static void searchRestaurant (String searchField) {
	prepareSearcher (searchField);
    }
    
	    
    private static void prepareSearcher (String city) {
	try {
	    IndexReader reader = DirectoryReader.open (FSDirectory.open (Paths.get (index)));
	    IndexSearcher searcher = new IndexSearcher (reader);
	    Analyzer analyzer = new StandardAnalyzer ();

	    QueryParser parser = new QueryParser (field, analyzer);
	    Query query = parser.parse(city);
	    System.out.println("Searching for: " + query.toString(field));

	    doSearch (searcher, query, numResults);

	} catch (IOException e) {
	    System.out.println ("Cannot find index file");
	} catch (ParseException pe) {
	    System.out.println (pe.getMessage ());
	}
    }

    private static void doSearch (IndexSearcher searcher, Query query, int numResults)
	throws IOException {
	TopDocs results = searcher.search (query, numResults);
	ScoreDoc[] hits = results.scoreDocs;
	int numTotalHits = Math.toIntExact (results.totalHits);

	System.out.println (numTotalHits + " total matching documents");
	System.out.println ("Showing " + numResults + " of them\n");

	int start = 0;
	int end = Math.min (numResults, numTotalHits);

	for (int i=start; i<end; i++) {
	    //	    System.out.println ("doc="+hits[i].doc+" score="+hits[i].score);

	    Document doc = searcher.doc (hits[i].doc);
	    String name = doc.get ("name");
	    if (name != null)
		System.out.print (name);
	    String address = doc.get ("address");
	    if (address != null)
		System.out.print (", " + address);
	    System.out.print (", " + doc.get ("city"));
	    System.out.print (", " + doc.get ("stars") + "\u2605");
	    System.out.println (", Reviews: " + doc.get ("reviewCount"));
	    
	}
    }
}

import org.apache.lucene.analysis.standard.*;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.StoredField;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.io.IOException;

public class IndexCreator {
    static String indexPath = "index";    
    static IndexWriter writer = null;
    // create a new Document per line
    public static void createDocs (ArrayList<Restaurant> restaurants) {	


	try {
	    Directory dir = FSDirectory.open(Paths.get(indexPath));
	    Analyzer analyzer = new StandardAnalyzer();
	    IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
	    iwc.setOpenMode(OpenMode.CREATE);
	    
	    writer = new IndexWriter(dir, iwc);
	
	    for (Restaurant r : restaurants) {
		createDoc (writer, r);
	    }

	    writer.close ();
	}
	catch (IOException e) {
	    System.out.println (e.getMessage ());
	}
    }

    private static void createDoc (IndexWriter writer, Restaurant r) throws IOException {
	Document doc = new Document ();
	Field cityField = new TextField ("city", (String) r.getField ("city"),
					 Field.Store.YES);
	Field nameField = new StringField ("name", (String) r.getField ("name"),
					   Field.Store.YES);
	Field addressField = new TextField ("address",
					    (String) r.getField ("address"),
					    Field.Store.YES);
	Field starsField = new StoredField ("stars",
					    (Double) r.getField ("stars"));
	Field reviewCountField = new StoredField ("reviewCount",
						  (Long) r.getField ("reviewCount"));
	Field neighborhoodField =
	    new TextField ("neighborhood", (String) r.getField ("neighborhood"),
			   Field.Store.YES);
	Field stateField =
	    new TextField ("state", (String) r.getField ("state"), Field.Store.YES);
	Field postalCodeField =
	    new TextField ("postalCode", (String) r.getField ("state"),
			   Field.Store.YES);
	
	doc.add (cityField);
	doc.add (nameField);
	doc.add (addressField);
	doc.add (starsField);
	doc.add (reviewCountField);
	doc.add (neighborhoodField);
	doc.add (stateField);
	doc.add (postalCodeField);
	
	writer.addDocument (doc);
    }
}
    

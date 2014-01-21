package webs;

import java.net.UnknownHostException;
import java.util.Set;

import com.mongodb.*;

public class Mongo {
	
  public static boolean mongo(String name, String pass) throws UnknownHostException {
// Connect to MongoDB
	  MongoClient mongo = new MongoClient("192.168.146.129", 27017);
	  // create bd
	  DB db = mongo.getDB("login");
	  // add or use collection
	  DBCollection coll = db.getCollection("login");
	  DBCursor cur = coll.find();
	  String s = null;
	  while(cur.hasNext()) {
		  s = cur.next().toString() ;
	      if(s.contains("\"username\" : "+"\""+name+"\"")&&s.contains("\"password\" : "+"\""+pass+"\""))
	    	 return true;
	  }
	  return false;
	  
	  
	  
	  
	  // add new doc
//	  BasicDBObject document = new BasicDBObject();
//	  document.put("username", "1234");
//	  document.put("password", 1234);
//	  table.insert(document);
//	  search collections
//	  Set<String> colls = db.getCollectionNames();
//	  for (String s : colls) {
//	      System.out.println(s);
//	  }
	  
	  
  }
}

package webs;

import java.net.UnknownHostException;
import java.util.Set;

import com.mongodb.*;

public class UserDB {
	
  public static boolean isUser (String name, String pass) throws UnknownHostException {
	  
	  MongoClient mongo = new MongoClient("192.168.146.129", 27017);
	  DB db = mongo.getDB("AndroidServer");
	  DBCollection coll = db.getCollection("Users");
	  DBCursor cur = coll.find();
	  String s = null;
	  while(cur.hasNext()) {
		  s = cur.next().toString() ;
	      if(s.contains("\"username\" : "+"\""+name+"\"")&&s.contains("\"password\" : "+"\""+pass+"\""))
	    	 return true;
	  }
	  return false;	  	  
  }
}

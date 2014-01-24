package webs;

import java.net.UnknownHostException;
import java.util.Set;

import com.mongodb.*;

public class UserDB {
	
  public static boolean isUser (String name, String pass) throws UnknownHostException {
	  
	  MongoClient mongo = new MongoClient("192.168.146.129", 27017);
	  DB db = mongo.getDB("AndroidServer");
	  DBCollection coll = db.getCollection("Users");
	  BasicDBObject querry = new BasicDBObject();
	  querry.put("username", name );
	  querry.put("password", pass);
	  DBCursor cur = coll.find(querry);
	  if( cur.hasNext() )
    	 return true;
	  return false;	  	  
  }
}

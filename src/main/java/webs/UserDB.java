package webs;

import java.net.UnknownHostException;

import com.mongodb.*;

public class UserDB {
	
	private static MongoClient mongo;
	private static DB db;
	private static DBCollection coll;
	
	public static void initDB() throws UnknownHostException{
		
		mongo = new MongoClient(Config.IP_DB, Config.DB_PORT);
		db = mongo.getDB("AndroidServer");
		coll = db.getCollection("Users");
		if(coll.count()<=0){
			BasicDBObject defUser = new BasicDBObject();
			defUser.put("username", "test");
			defUser.put("password", "test");
			coll.insert(defUser);
			System.out.println("Database was created successfully, username test");
		} else System.out.println("The database already exists");
	}
		
	public static boolean isUser (String name, String pass) throws UnknownHostException {

		BasicDBObject querry = new BasicDBObject();
		querry.put("username", name );
		querry.put("password", pass);
		DBCursor cur = coll.find(querry);
		if( cur.hasNext() )
			return true;
		return false;	  	
	}
}


package users;

import java.net.UnknownHostException;

import webs.Config;

import com.mongodb.*;

public class UserDB {

	private static MongoClient mongo;
	private static DB db;

	public static void initDB() throws UnknownHostException{

		mongo = new MongoClient(Config.IP_DB, Config.DB_PORT);
		db = mongo.getDB("AndroidServer");
		DBCollection coll = db.getCollection("Users");
		if(coll.count()<=0){
			BasicDBObject defUser = new BasicDBObject();
			defUser.put("username", "test");
			defUser.put("password", "test");
			coll.insert(defUser);
			System.out.println("Database was created successfully, username test");
		} else System.out.println("The database already exists");
	}

	public static User getUser(String name, String pass) {

		
	    BasicDBObject querry = new BasicDBObject();
        querry.put("username", name );
        querry.put("password", pass);
        DBCollection coll = db.getCollection("Users");
        DBCursor cursor = coll.find(querry);
        
        if( cursor.hasNext() ) {
             DBObject obj =  cursor.next();
             return new User(String.valueOf(obj.get("username")));
        }
        
        return null;
		



	}

	public static boolean isUser (String name, String pass) {
	    return getUser(name, pass) != null;
	}
}

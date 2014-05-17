package com.users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.webs.Config;

import redis.clients.jedis.Jedis;

public class UserDB {

	private static Jedis redis;

	public static void initDB() {
		redis = new Jedis(Config.IP_DB, Config.DB_PORT);
		redis.select(4);
		if (redis.dbSize() == 0) {
			Map<String, String> dataUser = new HashMap<String, String>();
			dataUser.put("username", "test");
			dataUser.put("password", "test");
			redis.hmset("test", dataUser);
			System.out.println("Database was created successfully, username test");
		} else
			System.out.println("The database already exists");
	}

	public static User getUser(String name, String pass) {
		if (redis.hget(name, "password").equals(pass))
			return new User(name);
		return null;
	}

	public static String getIdUser(String email) {
		String id;
		if (redis.hget(email, "id") == null) {
			id = UUID.randomUUID().toString();
			Map<String, String> userList = new HashMap<String, String>();
			userList.put("email", email);
			userList.put("id", id);
			redis.hmset(email, userList);
		} else
			id = redis.hget(email, "id");
		return id;
	}

	public static boolean isUser(String name, String pass) {
		return getUser(name, pass) != null;
	}

	public static List <String> UserList() {
		Set <String> keys = redis.keys("*");
		List <String> userList = new ArrayList <String>();
		String email;
		String id;
		Iterator<String> it = keys.iterator();
		while(it.hasNext()){
			email = it.next().toString();
			if(email.contains("@")){
				id = redis.hget(email, "id");
				userList.add(email + ":  " + id);
			}
		}
		return userList;
	}
}
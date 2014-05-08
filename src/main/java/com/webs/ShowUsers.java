package com.webs;

import javax.servlet.http.HttpServletRequest;

import com.mvc.Controller;
import com.mvc.JsonView;
import com.mvc.PathParser;
import com.mvc.View;
import com.users.UserDB;

public class ShowUsers extends Controller {
	
	private String dataRoot;

	public ShowUsers(String dataRoot) {
		this.dataRoot = dataRoot;
	}
	
	public View get(HttpServletRequest request, PathParser pathInfo) throws Exception {		
		return new JsonView(UserDB.UserList());		
	}
	
}

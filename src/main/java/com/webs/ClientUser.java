package com.webs;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.mvc.Controller;
import com.mvc.JsonView;
import com.mvc.PathParser;
import com.mvc.View;
import com.users.UserDB;

public class ClientUser extends Controller {

	private String dataRoot;
	private String id;
	private String email;

	public ClientUser(String dataRoot) {
		this.dataRoot = dataRoot;
	}

	public View get(HttpServletRequest request, PathParser pathInfo) throws Exception {		
		if(StringUtils.isNotBlank(request.getParameter("email"))){	
			id = UserDB.getIdUser(request.getParameter("email"));
			new CategoryManipulation(dataRoot).createClienDir(id);
		}
		return new JsonView(id);
	}

}

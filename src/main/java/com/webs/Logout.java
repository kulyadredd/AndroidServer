package com.webs;

import javax.servlet.http.HttpServletRequest;

import com.auth.AuthService;
import com.mvc.Controller;
import com.mvc.PathParser;
import com.mvc.Redirect;
import com.mvc.View;

public class Logout extends Controller {
	
	final private AuthService auth;
	
	public Logout(AuthService auth){
		this.auth = auth;
	}
	
	@Override
	public View get(HttpServletRequest request, PathParser pathInfo)
			throws Exception {
		
		if(auth.isLogged(request.getSession()))
			auth.logout(request.getSession());		
		return new Redirect("/");
		
		
	}
	
}

package webs;

import javax.servlet.http.HttpServletRequest;

import users.UserDB;

import auth.AuthService;
import mvc.Controller;
import mvc.PathParser;
import mvc.Redirect;
import mvc.View;

@SuppressWarnings("serial")
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

package webs;

import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mvc.Controller;
import mvc.TemplateView;
import mvc.PathParser;
import mvc.View;

public class LogIn extends Controller{
	
	private String user_name = "1234"; // from db ))
	private String user_password = "1234"; // its too
	private HttpSession session;
	private static HashMap<String, Object> map;
	private final String upload_url = "/up";
	private final String session_tag = "Login";
	
	
	@Override
	public View post(HttpServletRequest request, PathParser pathInfo)
			throws Exception {
		session = request.getSession();
		if(request.getParameter("btn_e")!=null && request.getParameter("btn_e").equals("Exit")){
			session.removeAttribute(session_tag);
			map.clear();
			return new TemplateView("NoAccess.vm", new HashMap<String, Object>());
		}
		if(checkUser(request.getParameter("log_name"), request.getParameter("log_pass")))			
			session.setAttribute(session_tag, user_name);			
		
		
		if(session.getAttribute(session_tag)!=null){
			map = new HashMap<String, Object>();
			map.put("name", user_name);	
			map.put("link", upload_url);
			return new TemplateView("Access.vm", map);
		}
		
		
		
		return new TemplateView("NoAccess.vm", new HashMap<String, Object>());
	}
	
	@Override
	public View get(HttpServletRequest request, PathParser pathInfo)
			throws Exception {
		session = request.getSession();
		if(session.getAttribute(session_tag)!=null)			
			return new TemplateView("Access.vm", map);
		
		return new TemplateView("NoAccess.vm", new HashMap<String, Object>());
	}
	
	private boolean checkUser(String name, String pass){
		if(name!=null&&pass!=null)
			if(user_name.equals(name)&&user_password.equals(pass))
				return true;
		return false;			
	}
}

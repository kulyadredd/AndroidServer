package webs;

import java.util.HashMap;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mvc.Controller;
import mvc.PathParser;
import mvc.TemplateView;
import mvc.View;

public class UpLoadFile extends Controller {
	
	private HttpSession session;
	private final String session_tag = "Login";
	
	@Override
	public View get(HttpServletRequest request, PathParser pathInfo)
			throws Exception {
		session = request.getSession();
		if(session.getAttribute(session_tag)!=null){
			
			//to do some logic upload file
			
			return new TemplateView("Upload.vm", new HashMap<String, Object>());
		}else
			return new TemplateView("ErrorUrl.vm", new HashMap<String, Object>());
	}
}

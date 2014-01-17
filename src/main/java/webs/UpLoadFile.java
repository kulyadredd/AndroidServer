package webs;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import mvc.Controller;
import mvc.PathParser;
import mvc.TemplateView;
import mvc.View;
import webs.UPFile;

public class UpLoadFile extends Controller {
	
	private HttpSession session;
	private final String session_tag = "Login";
    
	@Override
	public View get(HttpServletRequest request, PathParser pathInfo)
			throws Exception {
		session = request.getSession();
		if(session.getAttribute(session_tag)!=null)
			return new TemplateView("Upload.vm", new HashMap<String, Object>());
		else
			return new TemplateView("ErrorUrl.vm", new HashMap<String, Object>());
	}
	
	public View post(HttpServletRequest request,PathParser pathInfo) 
			throws IOException, ServletException {
		InputStream in = request.getInputStream();
		if (in!=null){
		new UPFile(in);     
		return new TemplateView("Uploads.vm", new HashMap<String, Object>());	
		}else return new TemplateView("Uploadf.vm", new HashMap<String, Object>());	
	}	
}


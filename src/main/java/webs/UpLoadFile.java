package webs;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mvc.Controller;
import mvc.PathParser;
import mvc.TemplateView;
import mvc.View;


public class UpLoadFile extends Controller {
	
	private HttpSession session;
	private final String session_tag = "Login";
	private static HashMap<String, Object> map = new HashMap<String, Object>();    
	
	@Override
	public View get(HttpServletRequest request, PathParser pathInfo)
			throws Exception {
		session = request.getSession();
		if(session.getAttribute(session_tag)!=null){
			map.clear();
			map.put("name",session.getAttribute(session_tag));
			return new TemplateView("Upload.vm", map);
		}else
			return new TemplateView("NoAccess.vm", new HashMap<String, Object>());
	}	

	public View post(HttpServletRequest request,PathParser pathInfo) 
			throws IOException, ServletException {
		session  = request.getSession();
		if (session.getAttribute(session_tag)!=null){
			DownloadManager dm = new DownloadManager(request.getInputStream());			
			dm.upload();
			return new TemplateView("Upload.vm", map);	
		}else
		return new TemplateView("Upload.vm", map);	
	}	
}


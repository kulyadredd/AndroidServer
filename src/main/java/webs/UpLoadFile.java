package webs;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.eclipse.jetty.util.MultiPartInputStream;
import org.eclipse.jetty.util.MultiPartInputStream.MultiPart;

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
		if(session.getAttribute(session_tag)!=null)
			return new TemplateView("Upload.vm", new HashMap<String, Object>());
		else
			return new TemplateView("NoAccess.vm", new HashMap<String, Object>());
	}
	
	public View post(HttpServletRequest request,PathParser pathInfo) 
			throws IOException, ServletException {
		session  = request.getSession();
		System.out.println(request.getContentType());
		if (session.getAttribute(session_tag)!=null){						
			DownloadManager dm = new DownloadManager(request.getInputStream());
			dm.upload();			
		return new TemplateView("Upload.vm", new HashMap<String, Object>());	
		}else return new TemplateView("NoAccess.vm", new HashMap<String, Object>());	
	}	
}


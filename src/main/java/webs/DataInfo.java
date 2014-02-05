package webs;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import mvc.Controller;
import mvc.DataInfoView;
import mvc.PathParser;
import mvc.View;

public class DataInfo extends Controller{
	
	private String[] resources;
	
	public View get(HttpServletRequest request, PathParser pathInfo)
			throws Exception {
		if (request.getRequestURI().toString()!=null && request.getRequestURI().toString().contains("/info")&&
			request.getParameter("param")==null	)
			return new DataInfoView(resourcesInfo(null));
		else
			return new DataInfoView(resourcesInfo(request.getParameter("param")));
	}
	private String[] resourcesInfo(String param) {
		String dirName = null;
		if (param!=null)
		dirName = System.getProperty("user.dir")+"\\images\\"+param;
		else dirName = System.getProperty("user.dir")+"\\images\\";
		File checkDir = new File (dirName);
		resources =checkDir.list();
		return resources ;		
	}
}

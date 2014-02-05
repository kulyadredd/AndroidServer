package webs;

import java.io.File;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import mvc.Controller;
import mvc.DataInfoView;
import mvc.PathParser;
import mvc.View;

public class DataInfo extends Controller{
	
	private String[] resources;
	
	public View get(HttpServletRequest request, PathParser pathInfo)
			throws Exception {
		
		if (request.getParameter("datainfo")!=null && request.getParameter("datainfo").equals("1"))
			return new DataInfoView(resourcesInfo(null));
		else if (request.getParameter("VOfC")!=null) 
			return new DataInfoView(resourcesInfo(request.getParameter("VOfC")));
		else return null;
		
	}
	
	
	private String resourcesInfo(String valueOfCategories) {
		
		String dirName = null;
		if (valueOfCategories!=null)
			dirName = System.getProperty("user.dir")+"\\images\\"+valueOfCategories;
		else dirName = System.getProperty("user.dir")+"\\images\\";
		File checkDir = new File (dirName);
		resources =checkDir.list();
		if (resources!=null)
			return Arrays.toString(resources);	
		else return "";
	}
}

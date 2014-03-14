package webs;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import mvc.Controller;
import mvc.PathParser;
import mvc.View;

@SuppressWarnings("serial")
public class AddCategory extends Controller {

	
	private static final String[] TYPE_FILE = { "images", "sounds", "text" };
	private String dataRoot;
	
	public AddCategory(String dataRoot){
		this.dataRoot = dataRoot;
	}
	
	public View get(HttpServletRequest request, PathParser pathInfo)
			throws Exception {
		String newcategory = request.getParameter("newcategory");
		for (int i = 0; i < TYPE_FILE.length; i++) {
			File cat = new File(dataRoot + File.separator + TYPE_FILE[i] + File.separator + newcategory);
			cat.mkdir();
		}
		return null;
	}

}

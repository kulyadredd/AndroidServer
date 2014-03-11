package webs;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import mvc.Controller;
import mvc.PathParser;
import mvc.View;

public class AddCategory extends Controller {

	private static final String[] typeFile = { "images\\", "sounds\\", "text\\" };

	public View get(HttpServletRequest request, PathParser pathInfo)
			throws Exception {
		String newcategory = request.getParameter("newcategory");
		for (int i = 0; i < typeFile.length; i++) {
			File cat = new File(typeFile[i] + newcategory);
			cat.mkdir();
		}
		return null;
	}

}

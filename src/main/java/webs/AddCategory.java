package webs;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import mvc.Controller;
import mvc.PathParser;
import mvc.View;

public class AddCategory extends Controller {

	private static final String[] TYPE_FILE = { "images\\", "sounds\\", "text\\" };

	public View get(HttpServletRequest request, PathParser pathInfo)
			throws Exception {
		String newcategory = request.getParameter("newcategory");
		for (int i = 0; i < TYPE_FILE.length; i++) {
			File cat = new File(TYPE_FILE[i] + newcategory);
			cat.mkdir();
		}
		return null;
	}

}

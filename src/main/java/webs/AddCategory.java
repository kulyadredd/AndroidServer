package webs;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import mvc.Controller;
import mvc.ErrorView;
import mvc.JsonView;
import mvc.PathParser;
import mvc.View;

@SuppressWarnings("serial")
public class AddCategory extends Controller {

	private String dataRoot;
	
	public AddCategory(String dataRoot){
		this.dataRoot = dataRoot;
	}
	
	public View get(HttpServletRequest request, PathParser pathInfo)
			throws Exception {
		String newcategory = request.getParameter("newcategory");
		
		File images = new File(dataRoot + File.separator + "images" + File.separator + newcategory);
		boolean imageCategoryCreated = images.mkdir();
		
		File sounds = new File(dataRoot + File.separator + "sounds" + File.separator + newcategory);
        boolean soundsCategoryCreated = sounds.mkdir();
        
        File texts = new File(dataRoot + File.separator + "text" + File.separator + newcategory);
        boolean textsCategoryCreated = texts.mkdir();
		
        if (!imageCategoryCreated || !soundsCategoryCreated || !textsCategoryCreated)
            return ErrorView.SERVER_ERROR_GENERIC;
        
		return new JsonView(newcategory);
	}

}

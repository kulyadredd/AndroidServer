package com.app;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.mvc.Controller;
import com.mvc.JsonView;
import com.mvc.PathParser;
import com.mvc.View;

public class AppClientCategory extends Controller {
	
	private String dataRoot;
	
	public AppClientCategory(String dataRoot) {
		this.dataRoot = dataRoot;
	}

	public View get(HttpServletRequest request, PathParser pathInfo)
			throws Exception {		
			return new JsonView(getUserCategoryList(request.getParameter("id")));		
	}
		
	private Map<String, String[]> getUserCategoryList(String id) {
		Map<String, String[]> category = new HashMap<String, String[]>();
        String dirName = dataRoot + File.separator + "Users" + File.separator + id + File.separator +"images" + File.separator;
        File checkDir = new File(dirName);        
        category.put("user", checkDir.list());
        return category;
	}
	
}

package com.app;

import static org.apache.commons.lang.StringUtils.isBlank;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.mvc.Controller;
import com.mvc.JsonView;
import com.mvc.PathParser;
import com.mvc.View;

@SuppressWarnings("serial")
public class AppDataInfo extends Controller {
	
	private String dataRoot;

	private Map<String, String[]> values = new HashMap<String, String[]>();
	
	public AppDataInfo(String dataRoot) {
		this.dataRoot = dataRoot;
	}

	public View get(HttpServletRequest request, PathParser pathInfo)
			throws Exception {
		String rest = pathInfo.getRest().toString();
		if (isBlank(rest)){
			getBaseCategoryList();
			if(!isBlank(request.getParameter("id")))
				getUserCategoryList(request.getParameter("id"));
		
			return new JsonView(values);
		}else 
			return new JsonView(getFileList(rest, request.getParameter("id")));		
	}

	private void getBaseCategoryList(){
        String dirName = dataRoot + File.separator + "BaseCategory" + File.separator + "images" + File.separator;
        File checkDir = new File(dirName);
        values.put("base", checkDir.list());
	}
	
	private void getUserCategoryList(String id) {
        String dirName = dataRoot + File.separator + "Users" + File.separator + id + File.separator +"images" + File.separator;
        File checkDir = new File(dirName);        
        values.put("user", checkDir.list());
	}
	
	private String[] getFileList(String valueOfCategories, String id) {		
		String dirName = dataRoot+File.separator+ "Users" + File.separator + id + File.separator + valueOfCategories;
		File checkDir = new File (dirName);
		return checkDir.list();
	}
}
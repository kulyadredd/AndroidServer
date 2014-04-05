package com.webs;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.auth.AuthService;
import com.mvc.Controller;
import com.mvc.PathParser;
import com.mvc.TemplateView;
import com.mvc.View;

public class CategoryManager extends Controller {

	private String dataRoot;
	
	public CategoryManager(String dataRoot){
		this.dataRoot = dataRoot;
	}

	public View get(HttpServletRequest request, PathParser pathInfo) throws Exception {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("name", AuthService.getLoggerUser(request.getSession()).getUsername());
        map.put("v", Version.version());
        return new TemplateView("Upload.vm", map);
    }
	
}

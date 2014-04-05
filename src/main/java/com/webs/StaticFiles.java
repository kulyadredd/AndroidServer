package com.webs;

import javax.servlet.http.HttpServletRequest;

import com.mvc.Controller;
import com.mvc.PathParser;
import com.mvc.StaticFilesView;
import com.mvc.View;

@SuppressWarnings("serial")
public class StaticFiles extends Controller {

    private String root;

    public StaticFiles(String root) {
        this.root = root;
    }

    @Override
    public View get(HttpServletRequest request, PathParser pathInfo) throws Exception {
        return new StaticFilesView(root+request.getRequestURI());
    }

}

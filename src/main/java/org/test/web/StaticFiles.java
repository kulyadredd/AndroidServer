package org.test.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.test.mvc.Controller;
import org.test.mvc.PathParser;
import org.test.mvc.StaticFilesView;
import org.test.mvc.View;

public class StaticFiles extends Controller{	

	
	@Override
	public View get(HttpServletRequest request, PathParser pathInfo)
			throws Exception {
		// TODO Auto-generated method stub
		return new StaticFilesView(request.getRequestURI());
	}
	

}

package webs;

import javax.servlet.http.HttpServletRequest;

import mvc.Controller;
import mvc.PathParser;
import mvc.StaticFilesView;
import mvc.View;

public class StaticFiles extends Controller{	

	
	@Override
	public View get(HttpServletRequest request, PathParser pathInfo)
			throws Exception {
		// TODO Auto-generated method stub
		return new StaticFilesView(request.getRequestURI().substring(1));
	}
	

}

package webs;

import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import mvc.Controller;
import mvc.JsonView;
import mvc.PathParser;
import mvc.TemplateView;
import mvc.View;
import auth.AuthService;

@SuppressWarnings("serial")
public class UpLoadFile extends Controller {
	
	private String dataRoot;
	
	public UpLoadFile(String dataRoot){
		this.dataRoot = dataRoot;
	}

    @Override
    public View get(HttpServletRequest request, PathParser pathInfo) throws Exception {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("name", AuthService.getLoggerUser(request.getSession()).getUsername());
        map.put("v", Version.version());
        return new TemplateView("Upload.vm", map);
    }

    public View post(HttpServletRequest request, PathParser pathInfo) throws IOException, ServletException {
        DownloadManager dm = new DownloadManager(request, dataRoot);
        dm.upload();       
        
        return new JsonView(dm.getAnswer());
    }
}
package webs;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.eclipse.jetty.util.MultiPartInputStream.MultiPart;

import mvc.Controller;
import mvc.JsonView;
import mvc.PathParser;
import mvc.TemplateView;
import mvc.View;
import auth.AuthService;

public class UpLoadFile extends Controller {

    @Override
    public View get(HttpServletRequest request, PathParser pathInfo) throws Exception {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("name", AuthService.getLoggerUser(request.getSession()).getUsername());
        map.put("v", Version.version());
        return new TemplateView("Upload.vm", map);
    }

    public View post(HttpServletRequest request, PathParser pathInfo) throws IOException, ServletException {
        DownloadManager dm = new DownloadManager(request);
        dm.upload();       
        
        return new JsonView(dm.getAnswer());
    }
}
package webs;

import javax.servlet.http.HttpServletRequest;

import mvc.Controller;
import mvc.DataFilesView;
import mvc.PathParser;
import mvc.View;

public class DataFiles extends Controller {

    private String root;

    public DataFiles(String root) {
        this.root = root;
    }

    @Override
    public View get(HttpServletRequest request, PathParser pathInfo) throws Exception {
        return new DataFilesView(root+request.getRequestURI());
    }

}

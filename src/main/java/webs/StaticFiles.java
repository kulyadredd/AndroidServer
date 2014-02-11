package webs;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import mvc.Controller;
import mvc.PathParser;
import mvc.StaticFilesView;
import mvc.View;

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

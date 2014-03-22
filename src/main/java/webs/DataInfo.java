package webs;

import static org.apache.commons.lang.StringUtils.isBlank;

import java.io.File;

import javax.servlet.http.HttpServletRequest;



import mvc.Controller;
import mvc.JsonView;
import mvc.PathParser;
import mvc.View;

public class DataInfo extends Controller{
	
    private String dataRoot;
	public DataInfo(String dataRoot){
        this.dataRoot = dataRoot;
	}
	
	public View get(HttpServletRequest request, PathParser pathInfo)
			throws Exception {
	    String rest = pathInfo.getRest().toString();
		if (isBlank(rest))
			return new JsonView(getCategoryList());
		else 
			return new JsonView(getFileList(rest));
		
	}

    private String[] getCategoryList() {	
        String dirName = dataRoot + File.separator + "images" + File.separator;
        File checkDir = new File(dirName);
        return checkDir.list();
    }
	
	private String[] getFileList(String valueOfCategories) {
		
		String dirName = dataRoot+File.separator+valueOfCategories;
		File checkDir = new File (dirName);
		return checkDir.list();
	}
}

package webs;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import mvc.Controller;
import mvc.PathParser;
import mvc.View;

@SuppressWarnings("serial")
public class RemoveData extends Controller {
	
	private String dataRoot;
	
	public RemoveData(String dataRoot){
		this.dataRoot = dataRoot;
	}

	@Override
	public View get(HttpServletRequest request, PathParser pathInfo)
			throws Exception {
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			String path = request.getParameter("path");
			if(StringUtils.isNotBlank(path)){
				File removeFile;
				if(path.toLowerCase().equals("undefined")){
					String category = request.getParameter("category");
					if(StringUtils.isNotBlank(category)){
						
						removeFile = new File(dataRoot+File.separator+"text"+File.separator+category+File.separator+id+".txt");
						if(removeFile.exists())
							removeFile.delete();
					}
				}else{					
					removeFile = new File(dataRoot + (path.startsWith(File.separator)?path:File.separator+path));
					if(removeFile.exists())
						removeFile.delete();
				}					
			}
		}
		return super.get(request, pathInfo);
	}
}

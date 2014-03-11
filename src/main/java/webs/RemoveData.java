package webs;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import mvc.Controller;
import mvc.PathParser;
import mvc.View;

public class RemoveData extends Controller {

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
						System.out.println("text\\"+category+"\\"+id+".txt");
						removeFile = new File("text\\"+category+"\\"+id+".txt");
						if(removeFile.exists())
							removeFile.delete();
					}
				}else{
					System.out.println(path.substring(1));
					removeFile = new File(path.substring(1));
					if(removeFile.exists())
						removeFile.delete();
				}
					
			}
		}
		return super.get(request, pathInfo);
	}
}

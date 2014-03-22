package webs;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import mvc.JsonView;
import mvc.View;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;


public class TextFiles extends DataFiles {

    public TextFiles(String root) {
        super(root);
    }

    @Override
    protected View saveUploadedFile(HttpServletRequest request, String category, String id, String base, File outputDir) throws FileUploadException, IOException {
        String titleName = request.getParameter("title");
        if (StringUtils.isBlank(titleName))
            return super.saveUploadedFile(request, category, id, base, outputDir);
        
        File output = new File(outputDir, id+".txt");
        FileUtils.writeStringToFile(output, titleName);
        
        return new JsonView(composeUpdateObject(category, id, base, output));
    }
    
}

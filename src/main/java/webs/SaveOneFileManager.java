package webs;

import static org.apache.commons.io.FilenameUtils.getExtension;
import static org.apache.commons.io.FilenameUtils.getName;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

public class SaveOneFileManager {

    private InputStream in = null;
    private final File output;

    public SaveOneFileManager(HttpServletRequest request, File outputDir, String id) throws FileUploadException, IOException {
        List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
        FileItem theFileItem = null;
        for (FileItem aFileItem : items)
            if (StringUtils.isNotBlank(aFileItem.getName())){
                theFileItem = aFileItem;  
                break;
            }
            
        String fileName = getName(theFileItem.getName());
        in = theFileItem.getInputStream();
        output = new File(outputDir, id + "." + getExtension(fileName));       
    }

    public File getNewFile() {
        return output;
    }

    public void save() throws IOException {
        FileOutputStream out = new FileOutputStream(output);
        try {
            IOUtils.copy(in, out);
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }
    }

}

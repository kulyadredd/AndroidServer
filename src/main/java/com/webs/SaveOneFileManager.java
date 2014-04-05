package com.webs;

import static org.apache.commons.io.FilenameUtils.getExtension;
import static org.apache.commons.io.FilenameUtils.getName;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

public class SaveOneFileManager {

    private InputStream in = null;
    private final File output;

    public SaveOneFileManager(HttpServletRequest request, File outputDir, String id) throws IOException, ServletException {
        Collection<Part> parts = request.getParts();
        Part theFilePart = null;
        
        for (Part aPart : parts)
            if (StringUtils.isNotBlank(aPart.getSubmittedFileName())){
                theFilePart = aPart;  
                break;
            }
            
        String fileName = getName(theFilePart.getSubmittedFileName());
        in = theFilePart.getInputStream();
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

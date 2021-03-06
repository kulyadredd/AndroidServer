package com.webs;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.mvc.Controller;
import com.mvc.DataFilesView;
import com.mvc.ErrorView;
import com.mvc.JsonView;
import com.mvc.PathParser;
import com.mvc.View;

public class DataFiles extends Controller {

    public final String root;

    public DataFiles(String root) {
        this.root = root;
    }
    
    @Override
    public View get(HttpServletRequest request, PathParser pathInfo) throws Exception {
    	if(new File(root+File.separator+"BaseCategory"+URLDecoder.decode(request.getRequestURI(), "UTF-8")).exists())
    		return new DataFilesView(root+File.separator+"BaseCategory"+URLDecoder.decode(request.getRequestURI(), "UTF-8"));
    	return null;
    }

    public View post(HttpServletRequest request, PathParser pathInfo) throws IOException, ServletException, FileUploadException {
        String category = pathInfo.cutNext();
        if (StringUtils.isBlank(category))
            return ErrorView.FORBIDDEN_GENERIC;
        
        String id = pathInfo.cutNext();
        // Keep in mind, this is non-atomi, non-threadsafe
        if (StringUtils.isBlank(id))
            id = UUID.randomUUID().toString();
        
        String base = request.getServletPath().substring(1);
        File outputDir = new File(root+File.separator+"BaseCategory"+File.separator+base+File.separator+category);
        
        if (!outputDir.exists())
            return ErrorView.FORBIDDEN_GENERIC;

        return saveUploadedFile(request, category, id, base, outputDir);
    }

    protected View saveUploadedFile(HttpServletRequest request, String category, String id, String base, File outputDir) throws IOException, ServletException {
        SaveOneFileManager fileManager = new SaveOneFileManager(request, outputDir , id);
        File newFile = fileManager.getNewFile();
        
        if (newFile.exists())
            return ErrorView.FORBIDDEN_GENERIC;
        
        fileManager.save();       
        
        return new JsonView(composeUpdateObject(category, id, base, newFile));
    }

    protected Map<String, Map<String, String>> composeUpdateObject(String category, String id, String base, File newFile) throws IOException {
       
    	Map<String, String> vals = new HashMap<String, String>();
        String uri = "/"+base+"/"+category+"/"+newFile.getName();
        if ("labels".equals(base))
            vals.put(base.substring(0, base.length()-1), FileUtils.readFileToString(newFile));
        else
        	vals.put(base.substring(0, base.length()-1),uri);
        Map<String, Map<String, String>> responseStub = new HashMap<String, Map<String, String>>();
        responseStub.put("/"+category+"/"+id, vals);
        return responseStub;
    }

    @Override
    public View delete(HttpServletRequest request) throws Exception {
        File f = new File(this.root+File.separator+"BaseCategory"+URLDecoder.decode(request.getRequestURI(), "UTF-8"));
        
        if (!f.exists())
            return ErrorView.NOT_FOUND_GENERIC;
        
        if (!f.delete())
            return ErrorView.FORBIDDEN_GENERIC;
        
        String base = request.getServletPath().substring(1);
        String key =  request.getPathInfo();
        key = key.substring(0, key.lastIndexOf('.'));
        
        Map<String, String> vals = new HashMap<String, String>();
        String val = "null";
        vals.put(base.substring(0, base.length()-1),val);        
        Map<String, Map<String, String>> responseStub = new HashMap<String, Map<String, String>>();
        responseStub.put(key, vals);
        
        return new JsonView(responseStub);
    }
    
}
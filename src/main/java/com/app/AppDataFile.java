package com.app;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang.StringUtils;

import com.mvc.Controller;
import com.mvc.DataFilesView;
import com.mvc.ErrorView;
import com.mvc.JsonView;
import com.mvc.PathParser;
import com.mvc.View;
import com.webs.SaveOneFileManager;

@SuppressWarnings("serial")
public class AppDataFile extends Controller{
	
	public final String dataRoot;
	
	public AppDataFile(String dataRoot) {
		this.dataRoot = dataRoot;
	}
	
	public View get(HttpServletRequest request, PathParser pathInfo) throws Exception{
		return new DataFilesView(dataRoot+File.separator+"Users"+File.separator+pathInfo.cutNext()+File.separator+request.getServletPath().substring(4)+File.separator+URLDecoder.decode(pathInfo.getRest(), "UTF-8"));		
	}
	
    public View post(HttpServletRequest request, PathParser pathInfo) throws IOException, ServletException, FileUploadException {
        String category = pathInfo.cutNext();
        if (StringUtils.isBlank(category))
            return ErrorView.FORBIDDEN_GENERIC;        
        String id = pathInfo.cutNext();       
        String base = request.getServletPath().substring(4);
        String userId = pathInfo.cutNext();
        File outputDir = new File(dataRoot+File.separator+"Users"+File.separator+userId+File.separator+base+File.separator+category);       
        if (!outputDir.exists())
            return ErrorView.FORBIDDEN_GENERIC;
        return saveUploadedFile(request, category, id, base, outputDir);
    }
	
    protected View saveUploadedFile(HttpServletRequest request, String category, String id, String base, File outputDir) throws IOException, ServletException {
        SaveOneFileManager fileManager = new SaveOneFileManager(request, outputDir , id);
//        File newFile = fileManager.getNewFile();        
//        if (newFile.exists())
//            return ErrorView.FORBIDDEN_GENERIC;        
        fileManager.save();             
        return new JsonView(200);
    }
   
    public View delete(HttpServletRequest request, PathParser pathInfo) throws Exception { 
    	String category = pathInfo.cutNext();
    	String userId = pathInfo.cutNext();
        File f = new File(this.dataRoot+File.separator+"Users"+File.separator+userId+File.separator+request.getServletPath().substring(4)+File.separator+URLDecoder.decode(category, "UTF-8")+File.separator+pathInfo.getRest());       
        if (!f.exists())
            return ErrorView.NOT_FOUND_GENERIC;        
        if (!f.delete())
            return ErrorView.FORBIDDEN_GENERIC;        
        return new JsonView(200);
    }
}

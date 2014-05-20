package com.webs;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import com.mvc.Controller;
import com.mvc.ErrorView;
import com.mvc.JsonView;
import com.mvc.PathParser;
import com.mvc.View;

public class CategoryManipulation extends Controller {

	private String dataRoot;
	private String path;

	public CategoryManipulation(String dataRoot) {
		this.dataRoot = dataRoot;
	}

	public View get(HttpServletRequest request, PathParser pathInfo) throws Exception {
		if (request.getParameter("id")==null){
			path = File.separator + "BaseCategory" + File.separator;
			if (request.getParameter("newcategory")!=null){
				newcategory(path, request.getParameter("newcategory"));		
				return new JsonView(request.getParameter("newcategory"));
			}else{
				prepareRename(path, request.getParameter("oldcategory"), request.getParameter("renamecategory"));
				return new JsonView(request.getParameter("renamecategory"));
			}
		}else{
			path = File.separator + "Users" + File.separator + request.getParameter("id") + File.separator;
			if (request.getParameter("newcategory")!=null){				
				newcategory(path, request.getParameter("newcategory"));
				return new JsonView(request.getParameter("newcategory"));
			}else{
				prepareRename(path, request.getParameter("oldcategory"), request.getParameter("renamecategory"));
				return new JsonView(request.getParameter("renamecategory"));
			}
		}
	}

	private ErrorView prepareRename(String path, String oldCategory, String renameCategory) {		
		File oldImages = new File (dataRoot+ path + "images" + File.separator + oldCategory);
		File renameImages = new File (dataRoot+ path + "images" + File.separator + renameCategory);
		boolean imageCategoryRename = renamecategory(oldImages, renameImages);
		
		File oldSounds = new File (dataRoot+ path + "sounds" + File.separator + oldCategory);
		File renameSounds = new File (dataRoot+ path + "sounds" + File.separator + renameCategory);
		boolean soundsCategoryRename = renamecategory(oldSounds, renameSounds);
		
		File oldTexts = new File (dataRoot+ path + "labels" + File.separator + oldCategory);
		File renameTexts = new File (dataRoot+ path + "labels" + File.separator + renameCategory);
		boolean textsCategoryRename = renamecategory(oldTexts, renameTexts);
		
		if (!imageCategoryRename || !soundsCategoryRename || !textsCategoryRename)
			return ErrorView.SERVER_ERROR_GENERIC;
		else
			return null;		
	}

	private ErrorView newcategory(String path, String newcateg) {
		File images = new File(dataRoot + path + "images" + File.separator + newcateg);
		boolean imageCategoryCreated = images.mkdir();

		File sounds = new File(dataRoot + path + "sounds" + File.separator + newcateg);
		boolean soundsCategoryCreated = sounds.mkdir();

		File texts = new File(dataRoot + path + "labels" + File.separator + newcateg);
		boolean textsCategoryCreated = texts.mkdir();

		if (!imageCategoryCreated || !soundsCategoryCreated || !textsCategoryCreated)
			return ErrorView.SERVER_ERROR_GENERIC;
		else
			return null;		
	}

	private boolean renamecategory(File oldImages, File renameImages){
		boolean success = oldImages.renameTo(renameImages);
		if(!success)
			return false;
		else
			return true;	
	}
	
	public void createClientDir(String clientDir){
		File sourceDir = new File(dataRoot+File.separator+"Users"+File.separator+clientDir);
		if(!sourceDir.exists())
			if (sourceDir.mkdir()){
				File images = new File (dataRoot+File.separator+"Users"+File.separator+clientDir+File.separator+"images");
				images.mkdir();
				File sounds = new File (dataRoot+File.separator+"Users"+File.separator+clientDir+File.separator+"sounds");
				sounds.mkdir();
				File text = new File (dataRoot+File.separator+"Users"+File.separator+clientDir+File.separator+"labels");
				text.mkdir();
			}		
	}
	
	public View delete(HttpServletRequest request) throws Exception {
		String delcategory = request.getPathInfo();
		if (request.getParameter("id")==null){
			path = File.separator + "BaseCategory" + File.separator;
			deleteCategory(path, delcategory);
		}else{
			path = File.separator + "Users" + File.separator + request.getParameter("id") + File.separator;
			deleteCategory(path, delcategory);
		}		
		return new JsonView(delcategory);
	}
	
	private ErrorView deleteCategory(String path, String delcategory) {
		File images = new File(dataRoot + path + "images" + File.separator + delcategory);
		boolean imageCategoryDelete = deleteCategory(images);

		File sounds = new File(dataRoot + path + "sounds" + File.separator + delcategory);
		boolean soundsCategoryDelete = deleteCategory(sounds);

		File texts = new File(dataRoot + path + "labels" + File.separator + delcategory);
		boolean textsCategoryDelete = deleteCategory(texts);

		if (!imageCategoryDelete || !soundsCategoryDelete || !textsCategoryDelete)
			return ErrorView.SERVER_ERROR_GENERIC;
		else
			return null;		
	}

	private boolean deleteCategory(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				File f = new File(dir, children[i]);
				deleteCategory(f);
			}
			dir.delete();
		} else
			dir.delete();
		return true;
	}
}
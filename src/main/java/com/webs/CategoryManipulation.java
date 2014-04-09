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

	public CategoryManipulation(String dataRoot) {
		this.dataRoot = dataRoot;
	}

	public View get(HttpServletRequest request, PathParser pathInfo)
			throws Exception {
		if (request.getParameter("newcategory")!=null){
			String newcategory = request.getParameter("newcategory");
	
			File images = new File(dataRoot + File.separator + "images" + File.separator + newcategory);
			boolean imageCategoryCreated = images.mkdir();
	
			File sounds = new File(dataRoot + File.separator + "sounds" + File.separator + newcategory);
			boolean soundsCategoryCreated = sounds.mkdir();
	
			File texts = new File(dataRoot + File.separator + "labels" + File.separator + newcategory);
			boolean textsCategoryCreated = texts.mkdir();
	
			if (!imageCategoryCreated || !soundsCategoryCreated || !textsCategoryCreated)
				return ErrorView.SERVER_ERROR_GENERIC;
						
			return new JsonView(newcategory);
		
		}else{
			String oldCategory = request.getParameter("oldcategory");
			String renameCategory = request.getParameter("renamecategory");
			
			File oldImages = new File (dataRoot+ File.separator + "images" + File.separator + oldCategory);
			File renameImages = new File (dataRoot+ File.separator + "images" + File.separator + renameCategory);
			boolean imageCategoryRename = renamecategory(oldImages, renameImages);
			
			File oldSounds = new File (dataRoot+ File.separator + "sounds" + File.separator + oldCategory);
			File renameSounds = new File (dataRoot+ File.separator + "sounds" + File.separator + renameCategory);
			boolean soundsCategoryRename = renamecategory(oldSounds, renameSounds);
			
			File oldTexts = new File (dataRoot+ File.separator + "labels" + File.separator + oldCategory);
			File renameTexts = new File (dataRoot+ File.separator + "labels" + File.separator + renameCategory);
			boolean textsCategoryRename = renamecategory(oldTexts, renameTexts);
			
			if (!imageCategoryRename || !soundsCategoryRename || !textsCategoryRename)
				return ErrorView.SERVER_ERROR_GENERIC;
			
			return new JsonView(renameCategory);
		}
	}

	private boolean renamecategory(File oldImages, File renameImages){
		boolean success = oldImages.renameTo(renameImages);
		if(!success)
			return false;
		else
			return true;	
	}
	
	public View delete(HttpServletRequest request) throws Exception {
		String delcategory = request.getPathInfo();

		File images = new File(dataRoot + File.separator + "images" + File.separator + delcategory);
		boolean imageCategoryDelete = deleteCategory(images);

		File sounds = new File(dataRoot + File.separator + "sounds" + File.separator + delcategory);
		boolean soundsCategoryDelete = deleteCategory(sounds);

		File texts = new File(dataRoot + File.separator + "labels" + File.separator + delcategory);
		boolean textsCategoryDelete = deleteCategory(texts);

		if (!imageCategoryDelete || !soundsCategoryDelete || !textsCategoryDelete)
			return ErrorView.SERVER_ERROR_GENERIC;

		return new JsonView(delcategory);
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

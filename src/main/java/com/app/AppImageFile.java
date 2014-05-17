package com.app;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.servlets.DataRateLimitedServlet;

import com.mvc.PathParser;
import com.mvc.ResizeImageView;
import com.mvc.View;

public class AppImageFile extends AppDataFile{
	
	private int ratiowigth;
	private int ratioheigth;
	private BufferedImage originalImage;
	
	public AppImageFile(String dataRoot) {
		super(dataRoot);		
	}
	
	public View get(HttpServletRequest request, PathParser pathInfo)
			throws Exception {
		String userId = pathInfo.cutNext();
		String category = pathInfo.cutNext();
		String name = pathInfo.cutNext();		
		if (name.contains(".png") || name.contains(".jpg"))
			return super.get(request, pathInfo);
		else {
			int wigth = Integer.parseInt(name);
			int heigth = Integer.parseInt(pathInfo.cutNext());
			String filepath = dataRoot +File.separator+"Users"+File.separator+userId+"/images/" + URLDecoder.decode(category, "UTF-8") + File.separator + pathInfo.cutNext();
			if (new File(filepath).exists())
				originalImage = ImageIO.read(new File(filepath));
			else 
				return null;
			if (originalImage.getWidth() > originalImage.getHeight())
				normalResolutionImage(wigth, heigth);
			else
				converseResolutioImage(wigth, heigth);
			return new ResizeImageView(filepath, resizeFile());
		}
	}

	private void converseResolutioImage(int wigth, int heigth)
			throws IOException {
		if (wigth > heigth) {
			ratioheigth = wigth;
			Double kof = (double) (wigth * 100 / originalImage.getHeight());
			ratiowigth = (int) (originalImage.getWidth() * kof / 100);
		} else {
			ratioheigth = heigth;
			Double kof = (double) (heigth * 100 / originalImage.getHeight());
			ratiowigth = (int) (originalImage.getWidth() * kof / 100);
		}
	}

	private void normalResolutionImage(int wigth, int heigth)
			throws IOException {
		if (wigth > heigth) {
			ratiowigth = wigth;
			Double kof = (double) (wigth * 100 / originalImage.getWidth());
			ratioheigth = (int) (originalImage.getHeight() * kof / 100);
		} else {
			ratiowigth = heigth;
			Double kof = (double) (heigth * 100 / originalImage.getWidth());
			ratioheigth = (int) (originalImage.getHeight() * kof / 100);
		}
	}

	private InputStream resizeFile() throws IOException {
		int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
		BufferedImage resizedImage = new BufferedImage(ratiowigth, ratioheigth, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, ratiowigth, ratioheigth, null);
		g.dispose();
		ByteArrayOutputStream resizedImageOS = new ByteArrayOutputStream();
		ImageIO.write(resizedImage, "png", resizedImageOS);
		InputStream in = new ByteArrayInputStream(resizedImageOS.toByteArray());
		return in;

	}

}


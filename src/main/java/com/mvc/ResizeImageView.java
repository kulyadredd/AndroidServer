package com.mvc;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.nio.file.Paths;
import java.util.Arrays;

import org.apache.commons.io.FilenameUtils;

import com.util.MimeType;

public class ResizeImageView implements View {

	private Path path;
	private InputStream in;

	public ResizeImageView(String path, InputStream in) {
		this.path = Paths.get(path);
		this.in = in;
	}

	public void view(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String ext = FilenameUtils.getExtension(path.getFileName().toString());
		response.setContentType(MimeType.getByFileExtension(ext).getMimeType());
		resizeFileShow(response.getOutputStream());
	}

	private void resizeFileShow(OutputStream out) throws IOException {
		try {
			int ch = 0;
			byte[] buffer = new byte[4096];
			while ((ch = in.read(buffer)) != -1) {
				out.write(buffer, 0, ch);
				Arrays.fill(buffer, (byte) 0);
			}
		} finally {
			if (in != null)
				in.close();
		}
	}

}

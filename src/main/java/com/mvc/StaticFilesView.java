package com.mvc;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;

import com.util.MimeType;

public class StaticFilesView implements View {

    private String path;

    public StaticFilesView(String path) {
        this.path = path;
    }

    @Override
    public void view(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String ext = FilenameUtils.getExtension(path);
        response.setContentType(MimeType.getByFileExtension(ext).getMimeType());
        response.setCharacterEncoding("iso-8859-1");
        writeFile(response.getWriter(), path);
    }

    private void writeFile(PrintWriter out, String path) throws IOException {
        InputStream in = this.getClass().getResourceAsStream(path);
        
        try {
            int ch = 0;
            while ((ch = in.read()) != -1) {
                out.print((char) ch);
            }
        } finally {
            if (in != null)
                in.close();
        }

    }
}

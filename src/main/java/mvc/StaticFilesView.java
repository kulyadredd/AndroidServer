package mvc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StaticFilesView implements View {

    private String path;

    public StaticFilesView(String path) {
        this.path = path;
    }

    @Override
    public void view(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (path.endsWith(".js"))
            response.setContentType("application/javascript");
        else if (path.endsWith(".css"))
            response.setContentType("text/css");
        else if (path.endsWith(".html"))
            response.setContentType("text/html");
        else if (path.endsWith(".png") || path.endsWith(".jpg"))
            response.setContentType("image/png");
        else if (path.endsWith(".mid"))
            response.setContentType("audio/midi");
        else if (path.endsWith(".mp3"))
            response.setContentType("audio/mpeg");
        else if (path.endsWith(".txt"))
            response.setContentType("text/plain");

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

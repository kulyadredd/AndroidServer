package mvc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DataFilesView implements View {

    private String path;

    public DataFilesView(String path) {
        this.path = path;
    }

    @Override
    public void view(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (path.endsWith(".js"))
            response.setContentType("text/plain");
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
        writeFile(response.getOutputStream(), path);
    }

    private void writeFile(OutputStream out, String path) throws IOException {
        InputStream in = new FileInputStream(new File(path));
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

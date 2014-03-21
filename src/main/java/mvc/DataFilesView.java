package mvc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;

import util.MimeType;

public class DataFilesView implements View {

    private Path p;

    public DataFilesView(String path) {
        this.p = Paths.get(path);
    }

    @Override
    public void view(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String ext = FilenameUtils.getExtension(p.getFileName().toString());
        response.setContentType(MimeType.getByFileExtension(ext).getMimeType());
        Files.copy(p, response.getOutputStream());
    }

}
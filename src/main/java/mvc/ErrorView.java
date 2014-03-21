package mvc;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ErrorView implements View {
    
    public static final ErrorView SERVER_ERROR_GENERIC = new ErrorView(500);

    public static final View FORBIDDEN_GENERIC = new ErrorView(403);

    public static final View NOT_FOUND_GENERIC = new ErrorView(404);
    
    private int statusCode;

    public ErrorView(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public void view(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(statusCode);
    }

}

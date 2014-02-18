package mvc;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Redirect implements View {

    
    private String redirect;

    public Redirect(String redirect) {
        this.redirect = redirect;
    }

    @Override
    public void view(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendRedirect(redirect);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

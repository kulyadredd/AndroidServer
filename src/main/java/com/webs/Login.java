package com.webs;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.auth.AuthService;
import com.mvc.Controller;
import com.mvc.PathParser;
import com.mvc.Redirect;
import com.mvc.TemplateView;
import com.mvc.View;
import com.webs.filters.LoginFilter;

public class Login extends Controller{
	
    private final AuthService authService;

    public Login(AuthService authService) {
        this.authService = authService;
    }
	
	
	@Override
	public View post(HttpServletRequest request, PathParser pathInfo)
			throws Exception {
        String email = request.getParameter("name");
        String password = request.getParameter("pass");
        String redirect = (String) request.getSession().getAttribute(LoginFilter.REDIRECT_ATTRIBUTE); 
        if (isNotBlank(email) && isNotBlank(password)) {
            if (authService.authorize(email, password, request.getSession())) {
                if (isNotBlank(redirect)){
                    request.getSession().removeAttribute(LoginFilter.REDIRECT_ATTRIBUTE);
                    return new Redirect(redirect);
                }
                return new Redirect("/");
            } else {
                return new Redirect("/login?error=email&email=" + email);
            }
        } else {
            return new Redirect("/login?error=blank&email=" + email);
        }
	}
	
	
	@Override
	public View get(HttpServletRequest request, PathParser pathInfo)
			throws Exception {
	    HashMap<String, Object> context = new HashMap<String, Object>();
	    String err = String.valueOf(request.getAttribute("error"));
	    context.put("err", err);
        context.put("v", Version.version());
        return new TemplateView("login.vm", context);
	}

}

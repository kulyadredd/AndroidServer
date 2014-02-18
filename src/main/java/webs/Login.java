package webs;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import webs.filters.LoginFilter;
import mvc.Controller;
import mvc.PathParser;
import mvc.Redirect;
import mvc.TemplateView;
import mvc.View;
import auth.AuthService;

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
        
//	    HashMap<String, Object> map = new HashMap<String, Object>();
//		session = request.getSession();
//		if(request.getParameter("btn_e") != null && request.getParameter("btn_e").equals("Exit")){
//			session.removeAttribute(session_tag);
//			map.clear();
//			map.put("incorrect", "");
//			return new TemplateView("login.vm", map);
//		}
//		
//		if(checkUser(request.getParameter("log_name"), request.getParameter("log_pass")))			
//			session.setAttribute(session_tag, request.getParameter("log_name"));
//		
//		if(session.getAttribute(session_tag) != null){
//			map.put("name", request.getParameter("log_name"));	
//			map.put("link", upload_url);
//			return new TemplateView("Access.vm", map);
//		}
//		
//		return new TemplateView("login.vm", map);
	}
	
	
	@Override
	public View get(HttpServletRequest request, PathParser pathInfo)
			throws Exception {
//		session = request.getSession();
//		if(session.getAttribute(session_tag)!=null)			
//			return new TemplateView("Access.vm", map);
		
//		return new TemplateView("login.vm", map);
	    HashMap<String, Object> context = new HashMap<String, Object>();
	    String err = String.valueOf(request.getAttribute("error"));
	    context.put("err", err);
        context.put("v", Version.version());
        return new TemplateView("login.vm", context);
	}

}

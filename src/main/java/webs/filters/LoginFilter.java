package webs.filters;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import webs.Config;
import auth.AuthService;

public class LoginFilter implements Filter {

    public static final String REDIRECT_ATTRIBUTE = "redirect";
    public static final String LOGIN_URI = "/login";
    private List<String> exceptionList;
    private AuthService auth;

    public LoginFilter(AuthService auth, Config config, List<String> exceptionList) {
        this.auth = auth;
        this.exceptionList = exceptionList;
    }


    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException { 
        HttpServletRequest request = (HttpServletRequest) req;

        if (isUnsecured(request.getRequestURI())) {
            chain.doFilter(req, resp);
        } else {        	
    		if (auth.isLogged(request.getSession())) {
    			try{
    				chain.doFilter(req, resp);
    			}catch(Exception e){
    				e.printStackTrace();
    			}    		        		        		
            } else {
                String fullUri = request.getRequestURI()
                        + ((request.getQueryString() == null) ? "" : "?" + request.getQueryString());
                request.getSession().setAttribute(REDIRECT_ATTRIBUTE, fullUri);
                req.getRequestDispatcher(LOGIN_URI).forward(req, resp);
            }
        }
    }
     
    private boolean isUnsecured(String requestURI) {
        for (String template : this.exceptionList)
            if (template.endsWith("*")){
                String head = template.substring(0, template.length()-1);
                if (requestURI.startsWith(head))
                    return true;
            } else {
                if (requestURI.equals(template))
                    return true;
            }
            
        return false;
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }

    @Override
    public void destroy() {  }

}

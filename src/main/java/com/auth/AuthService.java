package com.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.users.User;
import com.users.UserDB;

public class AuthService {
    
    private static final String CURRENTUSER = "user.current";

    public static User getLoggerUser(HttpSession session) {
        return ((User) session.getAttribute(CURRENTUSER));
    }

    public void logout(HttpSession session) {
        session.invalidate();
    }

    public boolean authorize(String username, String password, HttpSession session) {
        User user = authenticate(username, password);

        if (user != null) {
            session.setAttribute(CURRENTUSER, user);
            return true;
        }

        return false;
    }

    protected User authenticate(String username, String password) {
        return UserDB.getUser(username, password);
    }

    public String getUserId(HttpServletRequest request) {
        return getLoggerUser(request.getSession()).getUsername();
    }

    public boolean isLogged(HttpSession session) {
        return getLoggerUser(session) != null;
    }

}

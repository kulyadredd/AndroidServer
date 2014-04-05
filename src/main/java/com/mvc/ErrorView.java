package com.mvc;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

public class ErrorView implements View {
    
    public static final ErrorView SERVER_ERROR_GENERIC = new ErrorView(500);

    public static final View FORBIDDEN_GENERIC = new ErrorView(403);

    public static final View NOT_FOUND_GENERIC = new ErrorView(404);
    
    private int statusCode;
    private String message;

    public ErrorView(int statusCode) {
        this.statusCode = statusCode;
    }
    
    public ErrorView(int stausCode, String message){
        statusCode = stausCode;
        this.message = message;
    }

    @Override
    public void view(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(statusCode);
        if (StringUtils.isNotBlank(message))
            response.getOutputStream().write(message.getBytes());
    }

}

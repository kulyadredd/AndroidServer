package mvc;

import static mvc.PathParser.pathInfoParser;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Controller extends HttpServlet implements Servlet {

    private static final long serialVersionUID = 1L;

    public View get(HttpServletRequest request, PathParser pathInfo) throws Exception {
        return null;
    }

    public View post(HttpServletRequest request) throws Exception {
        return post(request, PathParser.pathInfoParser(request.getPathInfo()));
    }

    public View post(HttpServletRequest request, PathParser pathInfo) throws Exception {
        return null;
    }

    public View put(HttpServletRequest request, PathParser pathInfo) throws Exception {
        return null;
    }

    public View delete(HttpServletRequest request) throws Exception {
        return null;
    }

    @Override
    protected final void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        try {
            View view = get(request, pathInfoParser(request.getPathInfo()));
            if (view != null) {
                view.view(request, response);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        try {
            View view = post(request);
            if (view != null) {
                view.view(request, response);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        try {
            View view = delete(request);
            if (view != null) {
                view.view(request, response);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            View view = put(request, pathInfoParser(request.getPathInfo()));
            if (view != null) {
                view.view(request, response);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}

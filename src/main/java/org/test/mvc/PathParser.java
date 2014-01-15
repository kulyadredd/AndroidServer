package org.test.mvc;

public class PathParser {
	
    private String rest;
    private String separator;

    public PathParser(String string, String splitRegex) {
        this.rest = string;
        this.separator = splitRegex;
    }

	public static PathParser pathInfoParser(String pathInfo) {
        if (pathInfo == null)
            pathInfo = "";
        if (pathInfo.startsWith("/")) {
            pathInfo = pathInfo.substring(1);
        }
        return new PathParser(pathInfo, "/");
    }

	public static String parseTail(String a) {
		int idx = a.indexOf('/');
		idx = a.indexOf('/', idx+1);
		return a.substring(idx);
	}
	
	public String getRest(){
		return this.rest;
	}
	
}

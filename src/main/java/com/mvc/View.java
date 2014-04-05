package com.mvc;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface View {

	void view(HttpServletRequest request, HttpServletResponse response) throws IOException;

}

package mvc;

import java.io.*;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DataInfoView implements View {
	
	private String[] resources;  
	
	public DataInfoView(String[] resourcesInfo) {
		this.resources = resourcesInfo;
	}

	@Override
	public void view(HttpServletRequest request, HttpServletResponse response)
			throws IOException {		
		response.getWriter().print(Arrays.toString(resources));
	}
}

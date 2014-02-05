package mvc;

import java.io.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DataInfoView implements View {
	
	private String catalogOfResources;  
	
	public DataInfoView(String resourcesInfo) {
		this.catalogOfResources = resourcesInfo;
	}

	@Override
	public void view(HttpServletRequest request, HttpServletResponse response)
			throws IOException {		
		response.getWriter().print(catalogOfResources);
	}
}

package mvc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import webs.Config;

public class StaticFilesView implements View {
	
	private InputStream in;
	private String root;

	public StaticFilesView(String staticRoot) {
		
		this.root = staticRoot.toLowerCase();
	}

	@Override
	public void view(HttpServletRequest request, HttpServletResponse response)
			throws IOException {		
		
//		if(!root.startsWith(Config.parse(new Properties()).staticRoot))
//			return;				
		

		if(root.endsWith(".png")||root.endsWith(".jpg"))
			response.setContentType("image/png");
		else if(root.endsWith(".mid"))
			response.setContentType("audio/midi");

		try{			
			writeFile(response.getWriter(), root);
		}
		catch(Exception e){
			e.getMessage();
		}
		
		
	}
	
private void writeFile(PrintWriter out, String path)throws IOException{		
		in = new FileInputStream(new File(root));
		int ch = 0;
		while((ch = in.read()) != -1){
			out.print((char) ch);			
		}
		if(in != null)
			in.close();
		
	}

}

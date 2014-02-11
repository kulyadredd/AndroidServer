package mvc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StaticFilesView implements View {
	
	private InputStream in;
	private String path;

	public StaticFilesView(String path) {
	    this.path = path;
	}

	@Override
	public void view(HttpServletRequest request, HttpServletResponse response)
			throws IOException {		
		
//		if(!root.startsWith(Config.parse(new Properties()).staticRoot))
//			return;				
		
		if(path.endsWith(".png")||path.endsWith(".jpg"))
			response.setContentType("image/png");
		else if(path.endsWith(".mid"))
			response.setContentType("audio/midi");
		else if(path.endsWith(".mp3"))
			response.setContentType("audio/mpeg");
		else if(path.endsWith(".txt"))	
			response.setContentType("text/plain");

		try{			
			writeFile(response.getWriter(), path);
		}
		catch(Exception e){
			e.getMessage();
		}
		
		
	}
	
private void writeFile(PrintWriter out, String path)throws IOException{		
		in = new FileInputStream(new File(path));
		int ch = 0;
		while((ch = in.read()) != -1){
			out.print((char) ch);			
		}
		if(in != null)
			in.close();
		
	}

}

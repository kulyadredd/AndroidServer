package webs;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;


public class DownloadManager {
	
	private HttpServletRequest request;
	private String dataRoot;
	private String id;
	private String category;
	private String fileName;
	private InputStream in;
	private byte[] file;	
	private String path;
	private boolean isNew;
	
	public DownloadManager(HttpServletRequest request, String dataRoot){
		this.request = request;
		this.dataRoot = dataRoot;
	}
	
	
	private void prepare() throws IOException {		
		
		try {
			List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
			for (FileItem fileItem : items) {
				String fieldName = fileItem.getFieldName();
				if(fieldName.equalsIgnoreCase("id")){
					InputStream id_stream = fileItem.getInputStream();
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					int b = 0;
					while((b = id_stream.read())!=-1)
						baos.write(b);
					id = new String(baos.toByteArray());
					if(id_stream!=null)
						id_stream.close();
				}else{					
					fileName = FilenameUtils.getName(fileItem.getName());
					category = fieldName;
					in = fileItem.getInputStream();
				}
			}
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private String getCorrectName(){
		if(StringUtils.isNotBlank(id))
			return id + fileName.substring(fileName.lastIndexOf("."), fileName.length());
		else{
			isNew = true;
			return id = String.valueOf(new Random(System.currentTimeMillis()).nextInt(10000))
					+ ((char)(new Random(System.currentTimeMillis()).nextInt(25)+97))
					+ fileName.substring(fileName.lastIndexOf("."), fileName.length());
		}
	}
	
	private byte[] file() throws IOException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int current = 0;
		while((current = in.read(buffer))!=-1){
			baos.write(buffer, 0, current);
			Arrays.fill(buffer, (byte)0);
		}
		file = baos.toByteArray();
		return file;
			
	}

	public void upload() throws IOException{
		
		prepare();
		path = Config.getPath(fileName, category)+getCorrectName();
		System.out.println(path);
		File f = new File(dataRoot+File.separator + path);		
		FileOutputStream out = new FileOutputStream(f);		
		out.write(file());
		if(in!=null)
			in.close();
		if(out!=null)
			out.close();		
		
	}
	
	public List<String> getAnswer(){
		List<String> answer = new LinkedList<String>();		
		if(path.toLowerCase().endsWith("txt"))
			answer.add(new String(file));
		else
			answer.add(path.replace((char) 92, '/'));
		if(isNew)
			answer.add(id.substring(0, id.lastIndexOf(".")));
		return answer;
		
	}
		
}

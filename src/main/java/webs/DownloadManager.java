package webs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

public class DownloadManager {
	private InputStream in;
	private String fileName;
	private LinkedList<Byte> nameLine = new LinkedList<>();

	
	public DownloadManager(InputStream in){
		this.in = in;
	}
	
	
	public void upload() throws IOException {
		
		FileOutputStream out = null;
		LinkedList<Byte> list = new LinkedList<Byte>();
		int b = 0;
		while ((b = in.read()) != -1) list.add((byte) b);		
		clearWrap(list);
		File f = new File(Config.getPath(fileName.toLowerCase())+fileName);
		System.out.println(f.getAbsolutePath());
		out = new FileOutputStream(f);
		for (Byte bt : list) out.write(bt);
		in.close();
		out.close();
	}
	
	private void clearWrap(List<Byte> l){
		int count = 0;
		while (count < 4) {
			if (l.get(0) == 10)
				count++;
			if(count==1)
				nameLine.add(l.get(0));					
			l.remove(0);
		}
		setName();
		count = 0;
		while (count < 2) {
			if (l.get(l.size() - 1) == 13)
				count++;
			l.remove(l.size() - 1);
		}
	}
	
	private void setName(){		
		byte[] array = new byte[nameLine.size()];
		int index = 0;
		for (Byte b : nameLine) array[index++]=b;	
		String line = new String(array);
		this.fileName = "/"+line.substring(line.lastIndexOf("=")+2,line.length()-2);		
	}	


}

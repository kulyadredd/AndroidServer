package com.webs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;


public class Config {

    public static Config parseDefault() throws IOException {
        File f = new File("kittns.conf");        
        if (!f.exists())
            f = tildeExpand("~/etc/kittns.conf");
        if (!f.exists())
            f = new File("/etc/kittns/kittns.conf");
        if (!f.exists())
            return new Config();
        
        return parse(f);
    }

	private File configFile;
	private int port = 8080;
	public String staticRoot = "/static";
	public String dataRoot = ".";
	public static final int DB_PORT = 6379;
	public static final String IP_DB = "54.81.212.158";
	private static final String IMAGES_PATH = "images";
	private static final String AUDIO_PATH = "sounds";
	private static final String TEXT_PATH = "text";
    
    public static Config parse(File configFile) throws IOException {
        Config cfg = parse(loadProperties(configFile));

        return cfg;
    }
    
    public static Properties loadProperties(File file) throws IOException {
        Reader reader = null;
        Properties props = new Properties();
        try {
            if (file.exists()) {
                System.err.println("Reading config file: " + file.getAbsolutePath());
                reader = new BufferedReader(new FileReader(file));
                props.load(reader);
                props.setProperty("config.file", file.getAbsolutePath());
            }
            return props;
        } finally {
            closeQuietly(reader);
        }
    }
    
    public static Config parse(Properties config) throws IOException {
        Config cfg = new Config();
        cfg.port = getProperty(config, "port", cfg.port);
        cfg.staticRoot = config.getProperty("static.root", cfg.staticRoot);
        cfg.dataRoot = config.getProperty("data.root", cfg.dataRoot);
        cfg.configFile = new File(config.getProperty("config.file"));
        return cfg;
    }
    
    public static int getProperty(Properties p, String propertyName, int defaultValue){
    	String s = p.getProperty(propertyName);
    	if (s == null)
    		return defaultValue;
    	
    	return Integer.parseInt(s);
    }
    public String getPathResources(){
    	
    	  String path = getClass().getResource("").toString();
    	  return path; 
    	  
    	 }
    
    public static File tildeExpand(String path) {
    	if (path.startsWith("~")) {
    		path = path.replaceFirst("~", getUserHome().getAbsolutePath());
    	}
    	return new File(path);
    }
    
    public static File getUserHome() {
        return new File(System.getProperty("user.home"));
    }
    
    public static void closeQuietly(Reader input) {
        try {
            if (input != null) {
                input.close();
            }
        } catch (IOException ioe) {
            // ignore
        }
    }

	public int getPort() {
		return port;
	}
	
	public static String getPath(String file,String category){
		if(file.endsWith(".png")||file.endsWith(".jpg"))
			return Config.IMAGES_PATH + 
					File.separator + 
					category + 
					File.separator;
		else if (file.endsWith(".txt"))
			return Config.TEXT_PATH + 
					File.separator + 
					category + 
					File.separator;			
		else return Config.AUDIO_PATH + 
				File.separator + 
				category + 
				File.separator;
	}
}

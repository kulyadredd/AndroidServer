package com.webs;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

import javax.servlet.MultipartConfigElement;

import org.apache.commons.io.FileUtils;
import org.apache.velocity.app.Velocity;
import org.eclipse.jetty.server.handler.ErrorHandler;

import com.app.AppClientCategory;
import com.app.AppDataFile;
import com.app.AppImageFile;
import com.app.ClientApp;
import com.app.AppDataInfo;
import com.auth.AuthService;
import com.users.UserDB;
import com.webs.filters.LogFilter;
import com.webs.filters.LoginFilter;


public class WebServer {	
	
    private HttpServer server;
    private Config config;
    
    public WebServer(Config opts) throws IOException {
        server = new HttpServer(opts.getPort(), "/");
        config = opts;
    }

    private void init() throws IOException, SQLException {

        ErrorHandler errorHandler = new ErrorHandler();
        errorHandler.setShowStacks(true);

        UserDB.initDB();
        initVelocity();        
        AuthService auth = new AuthService();
        String[] excludes = {"/images/*", "/sounds/*", "/labels/*", "/info/*", 
        		"/appimages/*", "/appsounds/*", "/applabels/*", "/js/*", "/newuser/*", 
        		"/appinfo*", "/appinfo/*", "/usercateg*","/category/*", "/favicon.ico", LoginFilter.LOGIN_URI };
        server.addFilter(new LogFilter());
        server.addFilter(new LoginFilter(auth, config, Arrays.asList(excludes)));
        
	    MultipartConfigElement mce = new MultipartConfigElement("/tmp", 1024*1024*50, 1024*1024*100, 1024*1024*10); // maxFileSize= 50 MB maxRequestSize=100 MB fileSizeThreshold= 10 MB
        server.add("/images/*", new ImageFiles(config.dataRoot), mce);
	    server.add("/sounds/*", new DataFiles(config.dataRoot), mce);
	    server.add("/labels/*", new TextFiles(config.dataRoot), new MultipartConfigElement("/tmp", 1048576, 1048576, 262144));
	    
	    server.add("/info/*", new DataInfo(config.dataRoot));
	    
	    server.add("/img/*", new StaticFiles(config.staticRoot));
	    server.add("/js/*", new StaticFiles(config.staticRoot) );  server.add("/css/*", new StaticFiles(config.staticRoot) );
	    
	    server.add("/html/*", new StaticFiles(config.staticRoot) );
	    
	    server.add("/", new CategoryManager(config.dataRoot));
	    server.add("/favicon.ico", new StaticFiles(config.staticRoot));
	    server.add("/category/*", new CategoryManipulation(config.dataRoot));
	    server.add("/login", new Login(auth));
	    server.add("/logout", new Logout(auth));
	    server.add("/showusers", new ShowUsers(config.dataRoot));
	    
	    server.add("/usercateg/*", new AppClientCategory(config.dataRoot));
	    server.add("/appinfo/*", new AppDataInfo(config.dataRoot));
	    server.add("/newuser/*", new ClientApp(config.dataRoot));
	    server.add("/appimages/*", new AppImageFile(config.dataRoot), mce);
	    server.add("/appsounds/*", new AppDataFile(config.dataRoot), mce);
	    server.add("/applabels/*", new AppDataFile(config.dataRoot), new MultipartConfigElement("/tmp", 1048576, 1048576, 262144));
	    
        System.out.println("v"+Version.version()+" init completed.");
    }

	private void initVelocity() {
        Velocity.setProperty("class.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.setProperty("resource.loader", "class");
        Velocity.init();
    }

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws Exception {
    	Config opts = null;
    	if (args.length > 0)
    		opts = Config.parse(Config.tildeExpand(args[0]));
    	else
    		opts = Config.parseDefault();
    	

        WebServer webServer = new WebServer(opts);
        webServer.init();
        webServer.start();
        System.out.println("Listening on port: "+opts.getPort());
        webServer.join();
    }
    
    public void start() {
        try {
            server.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void join() {
        try {
            server.join();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

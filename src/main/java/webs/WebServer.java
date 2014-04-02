package webs;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

import org.apache.velocity.app.Velocity;
import org.eclipse.jetty.server.handler.ErrorHandler;

import users.UserDB;
import webs.filters.LogFilter;
import webs.filters.LoginFilter;
import auth.AuthService;


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
        String[] excludes = { "/info/*", "/info", "/images/*", "/sounds/*",
                "/text/*", "/js/*", "/favicon.ico", LoginFilter.LOGIN_URI };
        
        server.addFilter(new LogFilter());
        server.addFilter(new LoginFilter(auth, config, Arrays.asList(excludes)));
        
	    server.add("/images/*", new ImageFiles(config.dataRoot) );
	    server.add("/sounds/*", new DataFiles(config.dataRoot) );
	    server.add("/text/*", new TextFiles(config.dataRoot));
	    
	    server.add("/info/*", new DataInfo(config.dataRoot));
	    
	    server.add("/img/*", new StaticFiles(config.staticRoot));
	    server.add("/js/*", new StaticFiles(config.staticRoot) );
	    server.add("/css/*", new StaticFiles(config.staticRoot) );
	    
	    server.add("/*", new CategoryManager(config.dataRoot));
	    
	    server.add("/remove", new RemoveData(config.dataRoot));
	    server.add("/cat", new CategoryManipulation(config.dataRoot));
	    server.add("/login", new Login(auth));
	    server.add("/logout", new Logout(auth));
	    
	    
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

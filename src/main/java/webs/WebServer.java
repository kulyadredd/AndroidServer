package webs;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import org.eclipse.jetty.server.handler.ErrorHandler;


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

	    server.add("/images/*", new StaticFiles() );
	    server.add("/sounds/*", new StaticFiles() );
	    server.add("/titles/*", new StaticFiles());
	    server.add("/info", new DataInfo());
	    server.add("/Login", new LogIn() );
	    server.add("/up", new UpLoadFile() );
        System.out.println("Init completed.");
    }

	/**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws Exception {
    	Config opts = null;
    	if (args.length > 0)
    		opts = Config.parse(new File(args[0]));
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

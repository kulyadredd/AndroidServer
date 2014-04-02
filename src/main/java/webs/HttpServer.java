package webs;

import java.lang.management.ManagementFactory;
import java.util.EnumSet;

import javax.management.MBeanServer;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.http.HttpServlet;

import org.eclipse.jetty.jmx.MBeanContainer;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class HttpServer {
    protected Server jetty;
    private ServletContextHandler context;

    public HttpServer(int port, String contextPath) {
        // Connector connector = new SocketConnector();
        Connector connector = new SelectChannelConnector();
        connector.setPort(port);
        connector.setStatsOn(true);

        int lowResourceMaxIdleTime = connector.getLowResourceMaxIdleTime();

        System.err.println("http lowResourceMaxIdleTime: " + lowResourceMaxIdleTime);
        connector.setMaxIdleTime(Integer.MAX_VALUE);
        int maxIdleTime = connector.getMaxIdleTime();

        System.err.println("http maxIdleTime: " + maxIdleTime);

        // StdErrLog log = new StdErrLog();
        // log.setDebugEnabled(true);
        // org.mortbay.log.Log.setLog(log);
        jetty = new Server();
        jetty.addConnector(connector);

        context = new ServletContextHandler(jetty, "/", ServletContextHandler.SESSIONS);
        context.setContextPath(contextPath);
        jetty.setHandler(context);
    }

//	private void enableMBeans() {
//        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
//        MBeanContainer mBeanContainer = new MBeanContainer(mBeanServer);
//        jetty.addBean(mBeanContainer);
//        try {
//            jetty.start();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

    public void join() throws InterruptedException {
        jetty.join();
    }

    public void stop() throws Exception {
        jetty.stop();
    }

    public void start() throws Exception {
        jetty.start();
    }

    public void add(String path, HttpServlet servlet) {
        context.addServlet(new ServletHolder(servlet), path);
    }

    public void addFilter(Filter filter) {
        context.addFilter(new FilterHolder(filter), "*", EnumSet.of(DispatcherType.REQUEST));
    }

    public void add(ServletHolder holder, String string) {
        context.addServlet(holder, string);
    }
}
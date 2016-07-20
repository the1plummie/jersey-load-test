package com.test.jersey.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import java.util.Properties;


public class JettyApp {
    public static void main(String[] args) throws Exception {
        int httpPort = 8080;
        int maxThreads = 1024;
        int minThreads = 32;
        int idleTimeout = 500;
        QueuedThreadPool pool = new QueuedThreadPool(maxThreads, minThreads, idleTimeout, null);

        Server server = new Server(pool);
        ServerConnector httpConnector = new ServerConnector(server);
        httpConnector.setPort(httpPort);
        //httpConnector.setIdleTimeout(100L);
        server.addConnector(httpConnector);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(1);
        jerseyServlet.setInitParameter("javax.ws.rs.Application", "com.test.jersey.server.MyResourceConfig");

        try {
            server.start();
            server.join();
        } catch (Throwable t) {
            throw new RuntimeException("error starting server", t);
        }
    }
}

package InventarioApp;

import InventarioApp.servlet.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;

/**
 * class that Load the Equipo information and start the Jetty and Socket servers
 */
public class Server {

    private static final int PORTSERVLET = 7050;
    protected static Logger log = LogManager.getLogger();

    /**
     * method that starts the Jetty Server and the Socket Server
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        //Start Servlet server
        new Thread(() -> {
            org.eclipse.jetty.server.Server server = new org.eclipse.jetty.server.Server(PORTSERVLET);

            ResourceHandler resourceHandler = new ResourceHandler();
            resourceHandler.setDirectoriesListed(true);
            resourceHandler.setWelcomeFiles(new String[]{"/../../webapp/index.html"});
            resourceHandler.setResourceBase(".");

            ServletContextHandler servletHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
            servletHandler.addServlet(EquipoInfoServlet.class, "/equipoInfo");
            servletHandler.addServlet(EquipoEditarServlet.class, "/eliminarEquipo");
            servletHandler.addServlet(ConsultarEquipo.class,"/consultarEquipo");

            HandlerList handlers = new HandlerList();
            handlers.setHandlers(new Handler[]{resourceHandler, servletHandler});
            server.setHandler(handlers);

            log.info("Starting server on port " + PORTSERVLET + "...");
            try {
                server.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}


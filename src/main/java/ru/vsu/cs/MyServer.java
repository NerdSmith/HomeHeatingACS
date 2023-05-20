package ru.vsu.cs;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.vsu.cs.controllers.BoilerController;
import ru.vsu.cs.controllers.EnvironmentController;
import ru.vsu.cs.controllers.EpochTimerController;
import ru.vsu.cs.controllers.RoomController;
import ru.vsu.cs.utils.DependencyInjector;

public class MyServer {
    public static void main(String[] args) throws Exception {
        DependencyInjector.inject();
        Server server = new Server(8080);

        ServletContextHandler servletContextHandler = new ServletContextHandler();
        servletContextHandler.setContextPath("/api");

        servletContextHandler.addServlet(new ServletHolder(new BoilerController()), "/boilers/*");
        servletContextHandler.addServlet(new ServletHolder(new RoomController()), "/rooms/*");
        servletContextHandler.addServlet(new ServletHolder(new EnvironmentController()), "/environment/*");
        servletContextHandler.addServlet(new ServletHolder(new EpochTimerController()), "/epochtimer/*");

        server.setHandler(servletContextHandler);

        server.start();
        server.join();
    }
}

package com.example;

import com.sun.jersey.freemarker.FreemarkerViewProcessor;
import com.sun.jersey.spi.container.servlet.ServletContainer;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.servlet.ServletRegistration;
import org.glassfish.grizzly.servlet.WebappContext;
import org.glassfish.jersey.grizzly2.servlet.GrizzlyWebContainerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;

public class Server {

    private static final Logger LOG = LoggerFactory.getLogger(Server.class);
    public static final URI BASE_URI = getBaseURI();

    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://0.0.0.0")
                .port(9999).path("/").build();
    }

    /**
     * Start the Jersey FreeMarker application.
     *
     * @param args does not matter.
     * @throws IOException in case the application could not be started.
     */
    public static void main(String[] args) throws Exception {
        new Server().startServer();
    }

    public static void startServer() throws IOException {
        LOG.info("Starting Grizzly Web Container.");

        WebappContext context = new WebappContext("context");
        ServletRegistration registration =
                context.addServlet("ServletContainer", ServletContainer.class);
        LOG.info("Path: "+Server.class.getPackage().getName());
        registration.setInitParameter("com.sun.jersey.config.property.packages", Server.class.getPackage().getName());
        // Add Freemarker template mapping
        registration.setInitParameter(FreemarkerViewProcessor.FREEMARKER_TEMPLATES_BASE_PATH,
                "freemarker");
        registration.addMapping("/*");



        // Create the container
        final HttpServer server = GrizzlyWebContainerFactory.create(BASE_URI);

        // Deploy the application into the container
        context.deploy(server);

        // Register shutdown hook for container
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                LOG.info("Stopping server..");
                server.shutdownNow();
            }
        }, "shutdownHook"));

        try {
            server.start();
            LOG.info("com.example.Server Ready... Press CTRL^C to exit..");
            Thread.currentThread().join();
        } catch (Exception e) {
            LOG.error("Exception starting HTTP server: ", e);
        }
    }
}
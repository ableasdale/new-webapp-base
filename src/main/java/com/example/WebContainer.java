package com.example;


import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.servlet.GrizzlyWebContainerFactory;
import org.glassfish.jersey.server.mvc.freemarker.FreemarkerMvcFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class WebContainer {

    private static final Logger LOG = LoggerFactory.getLogger(WebContainer.class);
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
        new WebContainer().startServer();
    }

    public static void startServer() throws IOException {
        LOG.info("Starting Grizzly Web Container.");


        String[] packages = {"com.example.resources"};
        final Map<String, String > initParams = new HashMap<String, String>();

        initParams.put("jersey.config.server.provider.classnames", "org.glassfish.jersey.server.mvc.freemarker.FreemarkerMvcFeature");
        initParams.put("jersey.config.server.provider.packages", packages[0]);
        initParams.put(FreemarkerMvcFeature.TEMPLATE_BASE_PATH, "freemarker");

        // Create the container
        final HttpServer server = GrizzlyWebContainerFactory.create(BASE_URI,initParams);

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
            LOG.info("com.example.WebContainer Ready... Press CTRL^C to exit..");
            LOG.info(String.format("For a list of available HTTP Resources go to: %sapplication.wadl", BASE_URI));
            Thread.currentThread().join();
        } catch (Exception e) {
            LOG.error("Exception starting HTTP server: ", e);
        }
    }
}
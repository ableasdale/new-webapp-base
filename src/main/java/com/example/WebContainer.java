package com.example;

import com.example.config.Config;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.servlet.GrizzlyWebContainerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class WebContainer {

    private static final Logger LOG = LoggerFactory.getLogger(WebContainer.class);

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

       /* System.setProperty("javax.xml.bind.JAXBContext",
                "com.sun.xml.internal.bind.v2.ContextFactory"); */


        Config.getSwaggerBeanConfig();

        final Map<String, String> initParams = Config.getBaseInitParams();

        initParams.put("jersey.config.server.provider.classnames", Config.getProviderClassnames());
        initParams.put("jersey.config.server.provider.packages", Config.RESOURCE_PACKAGES);

        /* Debug tracing
        initParams.put(ServerProperties.TRACING, TracingConfig.ALL.name());
        initParams.put(ServerProperties.TRACING_THRESHOLD, "VERBOSE"); */

        // Create the container
        final HttpServer server = GrizzlyWebContainerFactory.create(Config.getBaseURI(), initParams);

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
            LOG.info(String.format("%s ready... Press CTRL^C to exit..", WebContainer.class.getCanonicalName()));
            LOG.info(String.format("For a list of available HTTP Resources go to: %sapplication.wadl", Config.getBaseURI()));
            Thread.currentThread().join();
        } catch (Exception e) {
            LOG.error("Exception starting HTTP server: ", e);
        }
    }
}
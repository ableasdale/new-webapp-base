package com.example;

import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.freemarker.FreemarkerMvcFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;

public class HttpServer {

    private static final Logger LOG = LoggerFactory.getLogger(HttpServer.class);
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
        new HttpServer().startServer();
    }


    protected static org.glassfish.grizzly.http.server.HttpServer startServer() throws IOException {

        String[] packages = {"com.example.resources"};

        ResourceConfig rc = new ResourceConfig()
                .packages(packages)
                .property(FreemarkerMvcFeature.TEMPLATE_BASE_PATH, "freemarker")
                .register(org.glassfish.jersey.server.mvc.freemarker.FreemarkerMvcFeature.class);

        LOG.info("Starting grizzly HTTP Server");
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }


}
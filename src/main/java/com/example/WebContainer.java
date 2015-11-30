package com.example;


import com.example.config.Config;
import com.example.resources.BaseResource;
import io.swagger.jaxrs.config.BeanConfig;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.servlet.GrizzlyWebContainerFactory;
import org.glassfish.jersey.server.mvc.freemarker.FreemarkerMvcFeature;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;
import org.glassfish.jersey.server.mvc.mustache.MustacheMvcFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
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

        System.setProperty("javax.xml.bind.JAXBContext",
                "com.sun.xml.internal.bind.v2.ContextFactory");


        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0");
        beanConfig.setScan(true);
        beanConfig.setResourcePackage(BaseResource.class.getPackage().getName());
        beanConfig.setBasePath(Config.getBaseURI().toString());
        beanConfig.setDescription("Hello resources");
        beanConfig.setTitle("Hello API");

        final Map<String, String> initParams = new HashMap<String, String>();

        initParams.put("jersey.config.server.provider.classnames", Config.getProviderClassnames());

        // FOR JSP
        initParams.put("jersey.config.server.tracing", "ALL");
        initParams.put("jersey.config.servlet.filter.staticContentRegex", "/(images|js|styles|(/jsp))/.*");  //"(/index.jsp)|(/(content|(WEB-INF/jsp))/.*)");

        initParams.put("jersey.config.server.provider.packages", Config.RESOURCE_PACKAGES);
        initParams.put(FreemarkerMvcFeature.TEMPLATE_BASE_PATH, "freemarker");
        initParams.put(MustacheMvcFeature.TEMPLATE_BASE_PATH, "mustache");
        initParams.put(JspMvcFeature.TEMPLATE_BASE_PATH, "jsp");

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
package com.example;

import com.example.config.Config;
import io.swagger.jersey.listing.ApiListingResourceJSON;
import org.apache.jasper.servlet.JspServlet;
import org.glassfish.grizzly.servlet.FilterRegistration;
import org.glassfish.grizzly.servlet.ServletRegistration;
import org.glassfish.grizzly.servlet.WebappContext;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.moxy.json.MoxyJsonConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.freemarker.FreemarkerMvcFeature;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;
import org.glassfish.jersey.server.mvc.mustache.MustacheMvcFeature;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.DispatcherType;
import javax.ws.rs.ext.ContextResolver;
import java.io.IOException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/*
* NOTE - This is a mess right now (trying to get Swagger working... To use swagger - use WebContainer.java
* See the following if you want to see what the issue is:
* https://github.com/swagger-api/swagger-core/issues/1103
* https://github.com/ralf0131/swagger-core/commit/4d81afbcbf984c701c9ea3308f4641a6c7fb5c85
* https://github.com/swagger-api/swagger-core/wiki/Swagger-Core-Jersey-2.X-Project-Setup-1.5
* */
public class JasperHttpServer {

    private static final Logger LOG = LoggerFactory.getLogger(JasperHttpServer.class);

    private static final String JERSEY_SERVLET_CONTEXT_PATH = "";
    private static final String JSP_CLASSPATH_ATTRIBUTE =
            "org.apache.catalina.jsp_classpath";


    /**
     * Start the Jersey FreeMarker application.
     *
     * @param args does not matter.
     * @throws IOException in case the application could not be started.
     */
    public static void main(String[] args) throws Exception {
        new JasperHttpServer().startServer();
    }

    public static ContextResolver<MoxyJsonConfig> createMoxyJsonResolver() {
        final MoxyJsonConfig moxyJsonConfig = new MoxyJsonConfig();
        Map<String, String> namespacePrefixMapper
                = new HashMap<String, String>(1);
        namespacePrefixMapper.put(
                "http://www.w3.org/2001/XMLSchema-instance", "xsi");
        moxyJsonConfig.setNamespacePrefixMapper(namespacePrefixMapper)
                .setNamespaceSeparator(':');
        return moxyJsonConfig.resolver();
    }


    protected static org.glassfish.grizzly.http.server.HttpServer startServer() throws IOException {

        String[] packages = {"com.example.resources"};//,"io.swagger.resources"};

        // Configure Swagger
        Config.getSwaggerBeanConfig();

        ResourceConfig rc = new ResourceConfig()
                // Project specific packages
                .packages(packages)
                // MVC Engines
                .property(FreemarkerMvcFeature.TEMPLATE_BASE_PATH, Config.FREEMARKER_TEMPLATE_BASE_PATH)
                .property(MustacheMvcFeature.TEMPLATE_BASE_PATH, Config.MUSTACHE_TEMPLATE_BASE_PATH)
                .property(JspMvcFeature.TEMPLATE_BASE_PATH, Config.JSP_TEMPLATE_BASE_PATH)

                .register(org.glassfish.jersey.server.mvc.freemarker.FreemarkerMvcFeature.class)
                .register(org.glassfish.jersey.server.mvc.mustache.MustacheMvcFeature.class)
                // TODO - CURRENTLY NOT WORKING .register(ch.qos.logback.classic.ViewStatusMessagesServlet.class)
                //.register(io.swagger.jersey.config.JerseyJaxrsConfig.class)
                // Swagger Resources
                //.property("api.version", "1.0.0")
                //?.register(org.glassfish.jersey.servlet.ServletContainer.class)
                //.register(io.swagger.jaxrs.listing.ApiListingResource.class)
                //.register(io.swagger.jaxrs.listing.SwaggerSerializers.class)
                .register(JacksonFeature.class)
                .register(ApiListingResourceJSON.class)
                .register(createMoxyJsonResolver())
                // add detailed logging
                .register(LoggingFilter.class);
                //.register(ApiListingResourceJSON.class);
       /*
        rc.register(JerseyApiDeclarationProvider.class);
        rc.register(JerseyResourceListingProvider.class);
        rc.register(ApiListingResourceJSON.class);
                .property("api.version", "1.0.0")
                .property("api.version", "1.0.0")*/



        LOG.info("Starting grizzly HTTP Server");
        LOG.info(String.format("For a list of available HTTP Resources go to: %sapplication.wadl", Config.getBaseURI()));
        org.glassfish.grizzly.http.server.HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(Config.getBaseURI(), rc);

        WebappContext context = new WebappContext("WebappContext", JERSEY_SERVLET_CONTEXT_PATH);

        // Initialize and register Jersey Servlet
        FilterRegistration registration = context.addFilter("ServletContainer",
                ServletContainer.class);
        registration.setInitParameter("javax.ws.rs.Application",
                MyApplication.class.getName());
//        registration.setInitParameter(JspProperties.TEMPLATES_BASE_PATH,
//                "/WEB-INF/jsp");
        // configure Jersey to bypass non-Jersey requests (static resources and jsps)
        registration.setInitParameter(ServletProperties.FILTER_STATIC_CONTENT_REGEX,
                "(/(image|js|css)/?.*)|(/.*\\.jsp)|(/WEB-INF/.*\\.jsp)|"
                        + "(/WEB-INF/.*\\.jspf)|(/.*\\.html)|(/favicon\\.ico)|"
                        + "(/robots\\.txt)");

        registration.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), "/*");

        // Initialize and register JSP Servlet - this really doesn't seem right - needs further investigation

        ServletRegistration jspRegistration = context.addServlet(
                "JSPContainer", JspServlet.class.getName());
        jspRegistration.addMapping("/*");

        // Set classpath for Jasper compiler based on the current classpath
        context.setAttribute(JSP_CLASSPATH_ATTRIBUTE,
                System.getProperty("java.class.path"));

        context.deploy(httpServer);
        return httpServer;
    }

}
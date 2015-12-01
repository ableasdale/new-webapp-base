package com.example;

import com.example.config.Config;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;

import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.ext.ContextResolver;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/*
* NOTE - This is a mess right now (trying to get Swagger working... To use swagger - use WebContainer.java
* See the following if you want to see what the issue is:
* https://github.com/swagger-api/swagger-core/issues/1103
* https://github.com/ralf0131/swagger-core/commit/4d81afbcbf984c701c9ea3308f4641a6c7fb5c85
* https://github.com/swagger-api/swagger-core/wiki/Swagger-Core-Jersey-2.X-Project-Setup-1.5
* */

/* Note 2:
 * Jersey provides MVC support for JSP pages. There is a JSP template processor that resolves absolute template references to processable template references that are JSP pages as follows:

 if the absolute template reference does not end in ".jsp" append it to the reference; and
 if Servlet.getResource returns a non-null value for the appended reference then return the appended reference as the processable template reference otherwise return null.
 Thus the absolute template reference "/com/foo/Foo/index" would be resolved to "/com/foo/Foo/index.jsp" if there exists the JSP page "/com/foo/Foo/index.jsp" in web the application.
 */
public class HttpServer {

    private static final Logger LOG = LoggerFactory.getLogger(HttpServer.class);

    /**
     * Start the Jersey FreeMarker application.
     *
     * @param args does not matter.
     * @throws IOException in case the application could not be started.
     */
    public static void main(String[] args) throws Exception {
        new HttpServer().startServer();
    }

    /*
    public static ContextResolver<MoxyJsonConfig> createMoxyJsonResolver() {
        final MoxyJsonConfig moxyJsonConfig = new MoxyJsonConfig();
        Map<String, String> namespacePrefixMapper
                = new HashMap<String, String>(1);
        namespacePrefixMapper.put(
                "http://www.w3.org/2001/XMLSchema-instance", "xsi");
        moxyJsonConfig.setNamespacePrefixMapper(namespacePrefixMapper)
                .setNamespaceSeparator(':');
        return moxyJsonConfig.resolver();
    } */


    protected static org.glassfish.grizzly.http.server.HttpServer startServer() throws IOException {

        //String[] packages = {"com.example.resources"};//,"io.swagger.resources"};

        // Configure Swagger
        Config.getSwaggerBeanConfig();

        ResourceConfig rc = Config.getBaseResourceConfig();
        //rc.register(createMoxyJsonResolver());


       /*
        rc.register(JerseyApiDeclarationProvider.class);
        rc.register(JerseyResourceListingProvider.class);
        rc.register(ApiListingResourceJSON.class);
                .property("api.version", "1.0.0")
                .property("api.version", "1.0.0")*/


        LOG.info("Starting grizzly HTTP Server");
        LOG.info(String.format("For a list of available HTTP Resources go to: %sapplication.wadl", Config.getBaseURI()));
        org.glassfish.grizzly.http.server.HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(Config.getBaseURI(), rc);

        return httpServer;
    }

}
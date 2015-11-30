package com.example.config;

import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

/**
 * Created by Alex on 30/11/2015.
 */
public class Config {

    public static Integer PORT = 9995;
    public static String BASE_URI = "http://0.0.0.0";

    public static String RESOURCE_PACKAGES = "com.example.resources,io.swagger.jaxrs.listing";

    public static String[] PROVIDER_CLASSNAMES = {
            "org.glassfish.jersey.server.mvc.freemarker.FreemarkerMvcFeature",
            "org.glassfish.jersey.server.mvc.mustache.MustacheMvcFeature",
            "org.glassfish.jersey.server.mvc.jsp.JspMvcFeature",
            "ch.qos.logback.classic.ViewStatusMessagesServlet",
            "org.glassfish.jersey.filter.LoggingFilter",
            "org.glassfish.jersey.jackson.JacksonFeature"
    };

    public static String getProviderClassnames() {
        return StringUtils.join(PROVIDER_CLASSNAMES, ",");
    }

    public static URI getBaseURI() {
        return UriBuilder.fromUri(Config.BASE_URI).port(Config.PORT).path("/").build();
    }

}

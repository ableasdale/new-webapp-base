package com.example.config;

import com.example.resources.HelloResource;
import io.swagger.jaxrs.config.BeanConfig;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

/**
 * Created by Alex on 30/11/2015.
 */
public class Config {

    public static Integer PORT = 9995;
    public static String BASE_URI = "http://0.0.0.0";

    /*
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.0");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("0.0.0.0:9999");
        beanConfig.setBasePath("/api");
        beanConfig.setResourcePackage("com.example.resources");
        beanConfig.setScan(true);
        */
    // TODO - most of these are test values - figure out what ones are needed and refactor them out so they're more coherent
    public static BeanConfig getSwaggerBeanConfig() {
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0");
        beanConfig.setScan(true);
        beanConfig.setResourcePackage(HelloResource.class.getPackage().getName());
        beanConfig.setBasePath(Config.getBaseURI().toString());
        beanConfig.setDescription("Hello resources");
        beanConfig.setTitle("Hello API");
        return beanConfig;
    }

    public static String RESOURCE_PACKAGES = "com.example.resources,io.swagger.jaxrs.listing";

    public static String[] PROVIDER_CLASSNAMES = {
            "org.glassfish.jersey.server.mvc.freemarker.FreemarkerMvcFeature",
            "org.glassfish.jersey.server.mvc.mustache.MustacheMvcFeature",
            "org.glassfish.jersey.server.mvc.jsp.JspMvcFeature",
            "ch.qos.logback.classic.ViewStatusMessagesServlet",
            "org.glassfish.jersey.filter.LoggingFilter",
            "org.glassfish.jersey.jackson.JacksonFeature"
    };

    /* Helper Methods */

    public static String getProviderClassnames() {
        return StringUtils.join(PROVIDER_CLASSNAMES, ",");
    }

    public static URI getBaseURI() {
        return UriBuilder.fromUri(Config.BASE_URI).port(Config.PORT).path("/").build();
    }

}

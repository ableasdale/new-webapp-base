package com.example.config;

import com.example.resources.HelloResource;
import io.swagger.jaxrs.config.BeanConfig;
import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.server.mvc.freemarker.FreemarkerMvcFeature;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;
import org.glassfish.jersey.server.mvc.mustache.MustacheMvcFeature;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

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

    public static String FREEMARKER_TEMPLATE_BASE_PATH = "freemarker";
    public static String MUSTACHE_TEMPLATE_BASE_PATH = "mustache";
    public static String JSP_TEMPLATE_BASE_PATH = "/src/main/resources/jsp";

    /* Helper Methods */
    public static Map getBaseInitParams() {
        final Map<String, String> initParams = new HashMap<String, String>();
        initParams.put(FreemarkerMvcFeature.TEMPLATE_BASE_PATH, Config.FREEMARKER_TEMPLATE_BASE_PATH);
        initParams.put(MustacheMvcFeature.TEMPLATE_BASE_PATH, Config.MUSTACHE_TEMPLATE_BASE_PATH);
        initParams.put(JspMvcFeature.TEMPLATE_BASE_PATH, Config.JSP_TEMPLATE_BASE_PATH);
        return initParams;
    }
    public static String getProviderClassnames() {
        return StringUtils.join(PROVIDER_CLASSNAMES, ",");
    }

    public static URI getBaseURI() {
        return UriBuilder.fromUri(Config.BASE_URI).port(Config.PORT).path("/").build();
    }

}

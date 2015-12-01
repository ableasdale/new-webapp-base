package com.example.config;

import com.example.filters.CORSFilter;
import com.example.resources.BaseResource;
import io.swagger.jaxrs.config.BeanConfig;
import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.freemarker.FreemarkerMvcFeature;
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
    //public static String APP_RESOURCE_PACKAGES = "com.example.resources";
    public static String RESOURCE_PACKAGES = "com.example.resources,io.swagger.jaxrs.listing";
    public static String[] PROVIDER_CLASSNAMES = {
            "org.glassfish.jersey.server.mvc.freemarker.FreemarkerMvcFeature",
            "org.glassfish.jersey.server.mvc.mustache.MustacheMvcFeature",
            "ch.qos.logback.classic.ViewStatusMessagesServlet",
            "com.example.filters.CORSFilter"
    };
    public static String FREEMARKER_TEMPLATE_BASE_PATH = "freemarker";

    /*
            "org.glassfish.jersey.filter.LoggingFilter",
            "org.glassfish.jersey.jackson.JacksonFeature"
    };*/
    public static String MUSTACHE_TEMPLATE_BASE_PATH = "mustache";

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
        beanConfig.setResourcePackage(BaseResource.class.getPackage().getName());
        beanConfig.setBasePath(Config.getBaseURI().toString());
        beanConfig.setDescription("Hello resources");
        beanConfig.setTitle("Hello API");
        return beanConfig;
    }

    /* Helper Methods */
    public static Map getBaseInitParams() {
        final Map<String, String> initParams = new HashMap<String, String>();
        initParams.put(FreemarkerMvcFeature.TEMPLATE_BASE_PATH, Config.FREEMARKER_TEMPLATE_BASE_PATH);
        initParams.put(MustacheMvcFeature.TEMPLATE_BASE_PATH, Config.MUSTACHE_TEMPLATE_BASE_PATH);
        return initParams;
    }

    public static String getProviderClassnames() {
        return StringUtils.join(PROVIDER_CLASSNAMES, ",");
    }

    public static URI getBaseURI() {
        return UriBuilder.fromUri(Config.BASE_URI).port(Config.PORT).path("/").build();
    }

    public static ResourceConfig getBaseResourceConfig() {
        return new ResourceConfig()
                // Project specific packages
                .packages(Config.RESOURCE_PACKAGES)
                .register(CORSFilter.class)
                /* MVC (Template) Engines */
                // Freemarker
                .register(org.glassfish.jersey.server.mvc.freemarker.FreemarkerMvcFeature.class)
                .property(FreemarkerMvcFeature.TEMPLATE_BASE_PATH, Config.FREEMARKER_TEMPLATE_BASE_PATH)
                // Mustache
                .register(org.glassfish.jersey.server.mvc.mustache.MustacheMvcFeature.class)
                .property(MustacheMvcFeature.TEMPLATE_BASE_PATH, Config.MUSTACHE_TEMPLATE_BASE_PATH);


                /* Debug tracing */
                //.property(ServerProperties.TRACING, TracingConfig.ON_DEMAND.name())
                //.property(ServerProperties.TRACING_THRESHOLD, "VERBOSE")

                // TODO - CURRENTLY NOT WORKING .register(ch.qos.logback.classic.ViewStatusMessagesServlet.class)
                //.register(io.swagger.jersey.config.JerseyJaxrsConfig.class)
                // Swagger Resources
                //.property("api.version", "1.0.0")
                //?.register(org.glassfish.jersey.servlet.ServletContainer.class)
                //.register(io.swagger.jaxrs.listing.ApiListingResource.class)
                //.register(io.swagger.jaxrs.listing.SwaggerSerializers.class)
                //.register(JacksonFeature.class)
                //.register(ApiListingResourceJSON.class)
                //.register(createMoxyJsonResolver())
                // add detailed logging
                //.register(LoggingFilter.class);
                //.register(ApiListingResourceJSON.class);

    }
}

package com.example.filters;

/**
 * Created by Alex on 01/12/2015.
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

/**
 * Simplistic CORSFilter for Swagger UI - see:
 * http://stackoverflow.com/questions/18842390/jersey-2-2-containerresponsefilter-and-containerrequestfilter-never-get-execute
 * http://stackoverflow.com/questions/17197938/cors-support-for-a-jersey-app-with-wadl?rq=1
 */

@Provider
public class CORSFilter implements ContainerResponseFilter {

    private static final Logger LOG = LoggerFactory.getLogger(CORSFilter.class);

    @Override
    public void filter(ContainerRequestContext request,
                       ContainerResponseContext response) throws IOException {
        LOG.info("CORSFilter");
        response.getHeaders().add("Access-Control-Allow-Origin", "*");
        response.getHeaders().add("Access-Control-Allow-Headers",
                "Content-Type, api_key, Authorization, origin, accept");
        response.getHeaders().add("Access-Control-Allow-Credentials", "true");
        response.getHeaders().add("Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS, HEAD, PATCH");
    }
}


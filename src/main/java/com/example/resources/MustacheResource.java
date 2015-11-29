package com.example.resources;

import org.glassfish.jersey.server.mvc.Viewable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by ableasdale on 28/11/15.
 */

@Path("/mustache")
public class MustacheResource extends BaseResource {

    private static final Logger LOG = LoggerFactory.getLogger(MustacheResource.class);

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Viewable getDashboard() {
        LOG.info("Call to mustache resource (/mustache)");

        // renders the URI using "src/main/resources/mustache/mustache.mustache"
        return new Viewable("/mustache", createModel("Landing Page"));
    }

}

package com.example.resources;

import com.sun.jersey.api.view.Viewable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by ableasdale on 19/11/15.
 */

@Path("/")
public class RootResource extends BaseResource {


    private static final Logger LOG = LoggerFactory.getLogger(RootResource.class);

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Viewable getDashboard() {

        // renders the URI using "src/main/resources/freemarker/dashboard.ftl"
        return new Viewable("/rootresource", createModel("Landing Page"));
    }

}

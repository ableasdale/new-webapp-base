package com.example.resources;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.glassfish.jersey.server.mvc.Viewable;
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
@Api(value = "/", description = "Root Resource")
public class RootResource extends BaseResource {

    private static final Logger LOG = LoggerFactory.getLogger(RootResource.class);

    @GET
    @ApiOperation(value = "The Root Example Resource", notes = "Here is a root resource - this is an example of how you create and annotate a Jersey resource for Swagger")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Not sure what happend?"),
            @ApiResponse(code = 404, message = "Template not found")
    })

    @Produces(MediaType.TEXT_HTML)
    public Viewable getDashboard() {
        LOG.info("Call to root resource (/)");

        // renders the URI using "src/main/resources/freemarker/rootresource.ftl"
        return new Viewable("/rootresource", createModel("Landing Page"));
    }

}

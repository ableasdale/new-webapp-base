package com.example.resources;

import org.glassfish.jersey.server.mvc.Template;
import org.glassfish.jersey.server.mvc.Viewable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by ableasdale on 29/11/15.
 */
@Path("jsp")
@Template
public class JspResource extends BaseResource {

    private static final Logger LOG = LoggerFactory.getLogger(JspResource.class);

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Viewable getDashboard() {
        LOG.info("Call to JSP resource and in getDashboard()");

        // renders the URI using "src/main/resources/jsp/index.jsp"
        return new Viewable("index.jsp", createModel("JSP Landing Page"));
    }

}

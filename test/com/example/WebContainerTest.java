package com.example;


import static org.junit.Assert.assertEquals;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;

import com.example.config.Config;
import com.example.resources.BaseResource;
import org.glassfish.jersey.grizzly2.servlet.GrizzlyWebContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.ServletDeploymentContext;
import org.glassfish.jersey.test.TestProperties;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerException;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebContainerTest extends TestSupport {

    private static final Logger LOG = LoggerFactory.getLogger(WebContainerTest.class);

    @Override
    protected Application configure() {
        return super.configure();
    }

    @Override
    protected TestContainerFactory getTestContainerFactory() throws TestContainerException {
        // GrizzlyWebContainerFactory.create(Config.getBaseURI(), Config.getBaseInitParams());
        //return new GrizzlyWebTestContainerFactory(Config.getBaseURI(), Config.getBaseInitParams());
        return new GrizzlyWebTestContainerFactory();
    }

    @Override
    protected DeploymentContext configureDeployment() {
        forceSet(TestProperties.CONTAINER_PORT, "9995");
        //return ServletDeploymentContext.builder(configure()).build();
        /* return ServletDeploymentContext.forPackages(
                BaseResource.class.getClass().getPackage().getName()).build(); */
       return ServletDeploymentContext.forServlet(new ServletContainer(Config.getBaseResourceConfig())).build();
    }

    @Test
    public void testBootstrapResource() throws Exception {
        assertHtmlEnResponse(target("bootstrap").request().get(String.class));
    }

    @Test
    public void testAngularResource() throws Exception {
        assertAngularHtmlResponse(target("angular").request().get(String.class));
    }

    @Test
    public void testMustacheResource() throws Exception {
        assertHtmlEnResponse(target("mustache").request().get(String.class));
    }

    @Test
    public void testWadlResource() throws Exception {
        LOG.info(target("application.wadl").request().get(String.class));
        assertWadlResponse(target("application.wadl").request().get(String.class));
    }
}
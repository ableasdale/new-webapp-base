package com.example;

import org.junit.Test;

/**
 * Created by ableasdale on 18/11/15.
 */
public class ServerTest extends TestSupport {

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
        assertWadlResponse(target("application.wadl").request().get(String.class));
    }

    /*
    @Test
    public void testSwaggerJson() throws Exception {
        assertHtmlEnResponse(target("swagger.json").request().get(String.class));
    }*/
}

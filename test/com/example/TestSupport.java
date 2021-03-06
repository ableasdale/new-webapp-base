package com.example;

import com.example.config.Config;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;

import javax.ws.rs.core.Application;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Alex on 30/11/2015.
 */
public class TestSupport extends JerseyTest {

    protected Application configure() {
        forceSet(TestProperties.CONTAINER_PORT, "9995");
        Config.getSwaggerBeanConfig();
        //System.setProperty("jersey.test.port", "9995");
        return Config.getBaseResourceConfig();
    }

/*
    protected void assertHtmlResponse(String response) {
        assertNotNull("No text returned!", response);
        assertResponseContains(response, "<html>");
        assertResponseContains(response, "</html>");
    }*/

    protected void assertSwaggerJsonResponse(String response) {
        assertResponseContains(response, "\"swagger\":\"2.0\"");
    }

    protected void assertWadlResponse(String response) {
        assertResponseContains(response, "<application xmlns=\"http://wadl.dev.java.net/2009/02\">");
        assertResponseContains(response, "</application>");
    }

    protected void assertAngularHtmlResponse(String response) {
        assertNotNull("No text returned!", response);
        assertResponseContains(response, "<html ng-app>");
        assertResponseContains(response, "</html>");
    }

    protected void assertHtmlEnResponse(String response) {
        assertNotNull("No text returned!", response);
        assertResponseContains(response, "<html lang=\"en\">");
        assertResponseContains(response, "</html>");
    }

    protected void assertResponseContains(String response, String text) {
        assertTrue("Response should contain " + text + " but was: " + response, response.contains(text));
    }
}

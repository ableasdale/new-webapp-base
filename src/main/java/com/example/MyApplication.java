package com.example;

import com.example.resources.JspResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;

/**
 * Created by ableasdale on 29/11/15.
 */
public class MyApplication extends ResourceConfig {

    public MyApplication() {
        // Resources.
        register(JspResource.class);

        // MVC.
        register(JspMvcFeature.class);
    }
}

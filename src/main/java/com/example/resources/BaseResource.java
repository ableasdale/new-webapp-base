package com.example.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ableasdale on 19/11/15.
 */
public class BaseResource {

    private static final Logger LOG = LoggerFactory.getLogger(BaseResource.class);

    protected Map<String, Object> createModel(String id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "Your Example title goes here");
        map.put("id", id);
        return map;
    }

}

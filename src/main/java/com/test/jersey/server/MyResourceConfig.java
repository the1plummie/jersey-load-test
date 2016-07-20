package com.test.jersey.server;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class MyResourceConfig extends ResourceConfig {

    private static final Logger logger = LoggerFactory.getLogger(MyResourceConfig.class);

    public MyResourceConfig() {
        packages("com.test.jersey.server");
    }

}

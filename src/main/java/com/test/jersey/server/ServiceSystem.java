package com.test.jersey.server;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by shouguoli on 4/28/16.
 */
@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
public class ServiceSystem {

    private static final Logger logger = LoggerFactory.getLogger(ServiceSystem.class);

    @GET
    @Path("/system/healthcheck")
    public String healthcheck() throws Exception {
        return "OK";
    }

    @GET
    @Path("/v0/targeting_profile")
    public String someTestApi() throws Exception {
        TimeUnit.MILLISECONDS.sleep(100);
        return "Still OK";
    }

}

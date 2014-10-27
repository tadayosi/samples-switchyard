package com.redhat.samples.switchyard.web;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.switchyard.component.bean.Reference;

import com.redhat.samples.switchyard.GreetingService;

@Path("/")
@Named
public class GreetingGatewayResource {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(GreetingGatewayResource.class);
    
    @Inject
    @Reference
    private GreetingService service;
    
    @GET
    @Path("/hello/{name}")
    @Produces({ "application/json" })
    public String hello(@PathParam("name") String name) {
        String message = service.hello(name);
        LOGGER.info(message);
        return String.format("{\"result\" : \"%s\"}", message);
    }
    
    @GET
    @Path("/goodbye/{name}")
    @Produces({ "application/xml" })
    public String goodbye(@PathParam("name") String name) {
        String message = service.goodbye(name);
        LOGGER.info(message);
        return String.format("<goodbye><result>%s</result></goodbye>", message);
    }
    
}

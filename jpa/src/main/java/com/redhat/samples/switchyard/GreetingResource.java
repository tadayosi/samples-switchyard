package com.redhat.samples.switchyard;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/")
public interface GreetingResource {

    @GET
    @Path("/hello/{name}")
    @Produces({ "application/json" })
    Response hello(@PathParam("name") String name);

    @GET
    @Path("/goodbye/{name}")
    @Produces({ "application/xml" })
    Response goodbye(@PathParam("name") String name);

}

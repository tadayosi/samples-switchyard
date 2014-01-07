package com.redhat.samples.switchyard;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.jboss.resteasy.client.ClientResponse;

@Path("/")
public interface GreetingClientResource {

    @GET
    @Path("/hello/{name}")
    @Produces({ "application/json" })
    ClientResponse<String> hello(@PathParam("name") String name);

    @GET
    @Path("/goodbye/{name}")
    @Produces({ "application/xml" })
    ClientResponse<String> goodbye(@PathParam("name") String name);

}

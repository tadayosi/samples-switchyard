package com.redhat.samples.switchyard.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/")
public class Greeting {

    @GET
    @Path("/hello/{name}")
    @Produces({ "application/json" })
    public Response hello(@PathParam("name") String name) {
        System.out.println("[hello] " + name);
        if ("error".equals(name)) { return Response.serverError().build(); }
        if ("not_found".equals(name)) { return Response.status(Status.NOT_FOUND).build(); }
        if ("unauthorized".equals(name)) { return Response.status(Status.UNAUTHORIZED).build(); }

        String message = String.format("Hello, %s!", name);
        String json = String.format("{\"result\" : \"%s\"}", message);
        return Response.ok(json).build();
    }

    @GET
    @Path("/goodbye/{name}")
    @Produces({ "application/xml" })
    public Response goodbye(@PathParam("name") String name) {
        System.out.println("[goodbye] " + name);
        if ("error".equals(name)) { return Response.serverError().build(); }
        if ("not_found".equals(name)) { return Response.status(Status.NOT_FOUND).build(); }
        if ("unauthorized".equals(name)) { return Response.status(Status.UNAUTHORIZED).build(); }

        String message = String.format("Goodbye, %s!", name);
        String xml = String.format("<goodbye><result>%s</result></goodbye>", message);
        return Response.ok(xml).build();
    }

}

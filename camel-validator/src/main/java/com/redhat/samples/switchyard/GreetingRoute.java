package com.redhat.samples.switchyard;

import static org.apache.camel.LoggingLevel.*;

import org.apache.camel.builder.RouteBuilder;

public class GreetingRoute extends RouteBuilder {

    public void configure() {
        // @formatter:off
        from("switchyard://GreetingRoute").routeId(getClass().getName())
            .streamCaching()
            .log("********************************************************************************")
            .log("body =\n${body}")
            .log("********************************************************************************")
            .doTry()
                .to("validator:wsdl/greeting.xsd")
                .to("switchyard://GreetingServiceBean?operationName=hello")
                .convertBodyTo(String.class)
            .doCatch(Exception.class)
                .log(ERROR, "********************************************************************************")
                .log(ERROR, "${exception}")
                .log(ERROR, "********************************************************************************")
                .to("switchyard://InvalidChannel")
                .setBody().simple("ERROR: ${exception.message}")
            .end();
        // @formatter:on
    }

}

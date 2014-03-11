package com.redhat.samples.switchyard;

import org.apache.camel.builder.RouteBuilder;

public class GreetingRoute extends RouteBuilder {

    public void configure() {
        // @formatter:off
        from("switchyard://GreetingService").routeId(getClass().getName())
                .streamCaching()
                .log("********************************************************************************")
                .log("body = ${body}")
                .log("********************************************************************************")
                .transform().simple("Hello, ${body}!", String.class);
        // @formatter:on
    }

}

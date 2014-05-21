package com.redhat.samples.switchyard.versioning.consumer;

import org.apache.camel.builder.RouteBuilder;

public class GreetingConsumerRoute extends RouteBuilder {

    public void configure() {
        //@formatter:off
        from("switchyard://GreetingConsumerRoute").routeId(getClass().getSimpleName())
            .log("********************************************************************************")
            .log("${body}")
            .log("********************************************************************************")
            .choice()
                .when(body().contains("urn:samples-switchyard:versioning:greeting-service:1.0"))
                    .to("switchyard://GreetingServiceV10")
                .when(body().contains("urn:samples-switchyard:versioning:greeting-service:1.1"))
                    .choice()
                        .when(body().contains("hello"))
                            .to("switchyard://GreetingServiceV11?operationName=hello")
                        .when(body().contains("goodbye"))
                            .to("switchyard://GreetingServiceV11?operationName=goodbye")
                    .end()
            .end()
            .convertBodyTo(String.class);
        //@formatter:on
    }

}

package com.redhat.samples.switchyard;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.jboss.resteasy.client.ClientResponseFailure;
import org.switchyard.HandlerException;
import org.switchyard.component.resteasy.composer.RESTEasyContextMapper;

public class GreetingProxyRoute extends RouteBuilder {

    public void configure() {

        //@formatter:off
        from("switchyard://GreetingProxy")
            .routeId(getClass().getName())
            .log("********************************************************************************")
            .log("body = ${body}")
            .log("********************************************************************************")
            .doTry()
                .to("switchyard://GreetingService")
            .doCatch(ClientResponseFailure.class)
                .process(new FailureStatusProcessor())
            .end();
        //@formatter:on

    }

    private class FailureStatusProcessor implements Processor {
        @Override
        public void process(Exchange exchange) throws Exception {
            HandlerException e = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, HandlerException.class);
            ClientResponseFailure failure = (ClientResponseFailure) e.getCause();
            exchange.getIn().setHeader(RESTEasyContextMapper.HTTP_RESPONSE_STATUS, failure.getResponse().getStatus());
        }
    }

}

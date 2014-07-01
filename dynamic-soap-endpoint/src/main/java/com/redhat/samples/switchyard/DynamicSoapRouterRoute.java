package com.redhat.samples.switchyard;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class DynamicSoapRouterRoute extends RouteBuilder {

    public static final String ENDPOINT_LABEL = DynamicSoapRouterRoute.class.getPackage().getName() + ".endpoint-label";

    private static final Map<String, String> ENDPOINTS = new HashMap<String, String>();
    static {
        ENDPOINTS.put("hello", "http://localhost:8080/hello/HelloService");
        ENDPOINTS.put("goodbye", "http://localhost:8080/goodbye/GoodbyeService");
    }

    public void configure() {
        // @formatter:off
        from("switchyard://DynamicSoapRouter").routeId(getClass().getSimpleName())
            .streamCaching()
            .log("********************************************************************************")
            .log("Request:\n${body}")
            .log("********************************************************************************")
            .process(new EndpointResolver())
            .to("switchyard://SoapEndpoint")
            .log("********************************************************************************")
            .log("Response:\n${body}")
            .log("********************************************************************************");
        // @formatter:on
    }

    private class EndpointResolver implements Processor {

        @Override
        public void process(Exchange exchange) throws Exception {
            exchange.getIn().setHeader(Exchange.HTTP_METHOD, "POST");
            String endpoint = ENDPOINTS.get(exchange.getProperty(ENDPOINT_LABEL));
            if (endpoint != null) {
                exchange.getIn().setHeader(Exchange.HTTP_URI, endpoint);
            }
        }

    }

}

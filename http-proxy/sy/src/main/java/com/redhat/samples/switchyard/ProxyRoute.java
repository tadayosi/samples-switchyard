package com.redhat.samples.switchyard;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.http.HttpOperationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.switchyard.HandlerException;
import org.switchyard.component.http.composer.HttpContextMapper;

public class ProxyRoute extends RouteBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProxyRoute.class);

    private static final String REFERENCE_BASE_URL = "http://localhost:18080";

    public void configure() {
        //@formatter:off
        from("switchyard://CamelProxyService").routeId(getClass().getName())
                .log("********************************************************************************")
                .log("method      = ${header.http_request_info.method}")
                .log("pathInfo    = ${header.http_request_info.pathInfo}")
                .log("queryString = ${header.http_request_info.queryString}")
                .log("body        = ${body}")
                .log("********************************************************************************")
                .doTry()
                    .setHeader(Exchange.HTTP_METHOD, simple("${header.http_request_info.method}"))
                    .setHeader(Exchange.HTTP_URI, simple(REFERENCE_BASE_URL + "${header.http_request_info.pathInfo}"))
                    .setHeader(Exchange.HTTP_QUERY, simple("${header.http_request_info.queryString}"))
                    .to("switchyard://TargetReference")
                    .setHeader(HttpContextMapper.HTTP_RESPONSE_STATUS, constant(200))
                .doCatch(Exception.class)
                    .process(new FailureStatusProcessor())
                .end();
        //@formatter:on
    }

    private class FailureStatusProcessor implements Processor {
        public void process(Exchange exchange) throws Exception {
            Exception exception = exchange.getProperty(org.apache.camel.Exchange.EXCEPTION_CAUGHT, Exception.class);
            LOGGER.error(exception.getMessage(), exception);
            int statusCode = 500;
            if (exception instanceof HandlerException) {
                HandlerException handlerEx = (HandlerException) exception;
                HttpOperationFailedException httpOperationFailed = (HttpOperationFailedException) handlerEx.getCause();
                statusCode = httpOperationFailed.getStatusCode();
            }
            exchange.getIn().setHeader(HttpContextMapper.HTTP_RESPONSE_STATUS, statusCode);
        }
    }

}
